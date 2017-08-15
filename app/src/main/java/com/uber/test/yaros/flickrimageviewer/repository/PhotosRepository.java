package com.uber.test.yaros.flickrimageviewer.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;
import com.uber.test.yaros.flickrimageviewer.network.NetworkModule;
import com.uber.test.yaros.flickrimageviewer.network.NetworkService;
import com.uber.test.yaros.flickrimageviewer.utils.ExecutorUtils;
import com.uber.test.yaros.flickrimageviewer.utils.ResponseWrapper;

public class PhotosRepository {

	private static PhotosRepository instance;
	private final NetworkService networkService;

	public static PhotosRepository getInstance() {
		if (instance == null) {
			synchronized (PhotosRepository.class) {
				if (instance == null) {
					instance = new PhotosRepository();
				}
			}
		}
		return instance;
	}

	private PhotosRepository() {
		this.networkService = NetworkModule.getInstance().getNetworkService();
	}

	public LiveData<ResponseWrapper<Boolean>> loadNextPage(@NonNull SearchQuery searchQuery) {
		GetNextPageTask getNextPageTask = new GetNextPageTask(searchQuery, networkService);
		ExecutorUtils.getInstance().mainThread().execute(getNextPageTask);

		return getNextPageTask.getLiveData();
	}
}
