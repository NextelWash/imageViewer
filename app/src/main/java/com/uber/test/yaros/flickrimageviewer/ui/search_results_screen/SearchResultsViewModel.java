package com.uber.test.yaros.flickrimageviewer.ui.search_results_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uber.test.yaros.flickrimageviewer.data.Photo;
import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;
import com.uber.test.yaros.flickrimageviewer.repository.PhotosLiveData;
import com.uber.test.yaros.flickrimageviewer.repository.PhotosRepository;
import com.uber.test.yaros.flickrimageviewer.repository.SearchQueryLiveData;
import com.uber.test.yaros.flickrimageviewer.utils.ResponseWrapper;

import java.util.List;
import java.util.Objects;

public class SearchResultsViewModel extends AndroidViewModel {

	private PhotosLiveData photosLiveData;

	private final NextPageHandler nextPageHandler;

	public SearchResultsViewModel(@NonNull Application application) {
		super(application);
		photosLiveData = PhotosLiveData.get();
		nextPageHandler = new NextPageHandler(PhotosRepository.getInstance());

		loadNextPage();
	}

	LiveData<ResponseWrapper<List<Photo>>> getPhotosData() {
		return photosLiveData;
	}

	LiveData<LoadMoreDataState> getLoadMoreStatus() {
		return nextPageHandler.getLoadMoreState();
	}

	void loadNextPage() {
		SearchQuery searchQuery = SearchQueryLiveData.get().getValue();
		nextPageHandler.queryNextPage(searchQuery);
	}

	static class LoadMoreDataState {
		private final boolean loading;
		private final String errorMessage;
		private boolean handledError = false;

		LoadMoreDataState(boolean loading, String errorMessage) {
			this.loading = loading;
			this.errorMessage = errorMessage;
		}

		boolean isLoading() {
			return loading;
		}

		String getErrorMessage() {
			return errorMessage;
		}

		String getErrorMessageIfNotHandled() {
			if (handledError) {
				return null;
			}
			handledError = true;
			return errorMessage;
		}
	}

	static class NextPageHandler implements Observer<ResponseWrapper<Boolean>> {
		@Nullable
		private LiveData<ResponseWrapper<Boolean>> nextPageLiveData;
		private final MutableLiveData<LoadMoreDataState> loadMoreState = new MutableLiveData<>();
		private SearchQuery query;
		private final PhotosRepository repository;
		boolean hasMore;

		NextPageHandler(@NonNull PhotosRepository repository) {
			this.repository = repository;
			reset();
		}

		void queryNextPage(@NonNull SearchQuery query) {
			if (Objects.equals(this.query, query)) {
				return;
			}
			unregister();
			this.query = query;
			nextPageLiveData = repository.loadNextPage(query);
			loadMoreState.setValue(new LoadMoreDataState(true, null));
			//noinspection ConstantConditions
			nextPageLiveData.observeForever(this);
		}

		@Override
		public void onChanged(@Nullable ResponseWrapper<Boolean> result) {
			if (result == null) {
				reset();
			} else {
				switch (result.status) {
					case SUCCESS:
						hasMore = Boolean.TRUE.equals(result.data);
						unregister();
						loadMoreState.setValue(new LoadMoreDataState(false, null));
						break;
					case ERROR:
						hasMore = true;
						unregister();
						loadMoreState.setValue(new LoadMoreDataState(false,
								result.message));
						break;
				}
			}
		}

		private void unregister() {
			if (nextPageLiveData != null) {
				nextPageLiveData.removeObserver(this);
				nextPageLiveData = null;
				if (hasMore) {
					query = null;
				}
			}
		}

		private void reset() {
			unregister();
			hasMore = true;
			loadMoreState.setValue(new LoadMoreDataState(false, null));
		}

		MutableLiveData<LoadMoreDataState> getLoadMoreState() {
			return loadMoreState;
		}
	}
}
