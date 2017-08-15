package com.uber.test.yaros.flickrimageviewer.ui.search_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;
import com.uber.test.yaros.flickrimageviewer.repository.PhotosLiveData;
import com.uber.test.yaros.flickrimageviewer.repository.SearchQueryLiveData;

public class SearchViewModel extends AndroidViewModel {

	@NonNull
	private final SearchQueryLiveData searchQueryLiveData;

	public SearchViewModel(@NonNull Application application) {
		super(application);
		searchQueryLiveData = SearchQueryLiveData.get();
	}

	LiveData<SearchQuery> getSearchQueryData() {
		return searchQueryLiveData;
	}

	void onSearchUpdated(@NonNull SearchQuery searchQuery) {
		if (!searchQuery.equals(searchQueryLiveData.getValue())) {
			PhotosLiveData.get().reset();
		}
		searchQueryLiveData.setSearchQuery(searchQuery);
	}
}
