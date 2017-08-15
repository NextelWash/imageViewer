package com.uber.test.yaros.flickrimageviewer.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.uber.test.yaros.flickrimageviewer.data.Photo;
import com.uber.test.yaros.flickrimageviewer.utils.ResponseWrapper;

import java.util.LinkedList;
import java.util.List;


public class PhotosLiveData extends LiveData<ResponseWrapper<List<Photo>>> {
	private static PhotosLiveData sInstance;

	public static PhotosLiveData get() {
		if (sInstance == null) {
			sInstance = new PhotosLiveData();
		}
		return sInstance;
	}

	private PhotosLiveData() {
		reset();
	}

	public void reset() {
		setValue(ResponseWrapper.loading(new LinkedList<>()));
	}

	public void setData(ResponseWrapper<List<Photo>> photos) {
		setValue(photos);
	}

	@SuppressWarnings("ConstantConditions")
	@NonNull
	@Override
	public ResponseWrapper<List<Photo>> getValue() {
		return super.getValue();
	}
}
