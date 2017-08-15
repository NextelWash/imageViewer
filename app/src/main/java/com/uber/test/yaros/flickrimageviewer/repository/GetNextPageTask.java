package com.uber.test.yaros.flickrimageviewer.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.uber.test.yaros.flickrimageviewer.data.Photo;
import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;
import com.uber.test.yaros.flickrimageviewer.data.SearchResults;
import com.uber.test.yaros.flickrimageviewer.network.NetworkService;
import com.uber.test.yaros.flickrimageviewer.settings.AppSettings;
import com.uber.test.yaros.flickrimageviewer.settings.BackendSettings;
import com.uber.test.yaros.flickrimageviewer.utils.ResponseWrapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Task that gets next page of photos. Manages the state, will nto fetch if there are no more pages
 */
public class GetNextPageTask implements Runnable {
	private final MutableLiveData<ResponseWrapper<Boolean>> liveData = new MutableLiveData<>();

	private final SearchQuery searchQuery;
	private final NetworkService networkService;

	private int currentPage;
	private final boolean isAllDataLoaded;

	/**
	 * TODO:: definitely this is bad, has to be improved... but will work for a demo
	 */
	private static SearchResultResult results;

	GetNextPageTask(@NonNull SearchQuery searchQuery, @NonNull NetworkService networkService) {
		this.searchQuery = searchQuery;
		this.networkService = networkService;

		if (results != null && searchQuery.equals(results.getSearchQuery())) {
			currentPage = results.getPageToLoad();
			isAllDataLoaded = results.isAllPagesLoaded();
		} else {
			currentPage = 1;
			isAllDataLoaded = false;
		}
	}

	@Override
	public void run() {
		if (isAllDataLoaded) {
			liveData.postValue(ResponseWrapper.success(false));
			return;
		}

		Map<String, String> searchOption = new HashMap<>();
		searchOption.put("format", "json");
		searchOption.put("nojsoncallback", "1");
		searchOption.put("api_key", BackendSettings.FLICK_KEY);
		searchOption.put("per_page", "" + AppSettings.LOAD_PHOTOS_PER_PAGE);
		searchOption.put("page", "" + currentPage);
		searchOption.put("text", searchQuery.getSearchCriteria());

		networkService.getSearchResults(searchOption).enqueue(new Callback<SearchResults>() {
			@Override
			public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
				if (response.isSuccessful()) {
					SearchResults searchResults = response.body();

					if (!searchResults.isValid() && currentPage == 1) {
						PhotosLiveData.get().setData(ResponseWrapper.error(response.body().getMessage(), null));
						liveData.postValue(ResponseWrapper.error(response.body().getMessage(), false));
					} else if (searchResults.getData() != null) {
						List<Photo> photos = new LinkedList<>();

						List<Photo> currentPhotos = PhotosLiveData.get().getValue().data;
						if (currentPhotos != null) {
							photos.addAll(currentPhotos);
						}
						photos.addAll(searchResults.getData().getPhotos());
						PhotosLiveData.get().setData(ResponseWrapper.success(photos));

						int totalPagesCount = searchResults.getData().getTotalNumOfPages();

						results = new SearchResultResult(searchQuery, ++currentPage, totalPagesCount);
						liveData.postValue(ResponseWrapper.success(!results.isAllPagesLoaded()));
					} else {
						liveData.postValue(ResponseWrapper.error(response.body().getMessage(), true));
					}
				} else {
					liveData.postValue(ResponseWrapper.error(response.body().getMessage(), true));
				}
			}

			@Override
			public void onFailure(Call<SearchResults> call, Throwable t) {
				liveData.postValue(ResponseWrapper.error(t.getMessage(), true));
			}
		});
	}

	LiveData<ResponseWrapper<Boolean>> getLiveData() {
		return liveData;
	}
}
