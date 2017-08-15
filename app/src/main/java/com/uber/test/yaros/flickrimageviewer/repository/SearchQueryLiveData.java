package com.uber.test.yaros.flickrimageviewer.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.uber.test.yaros.flickrimageviewer.FlickrImageViewerApp;
import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;

public class SearchQueryLiveData extends LiveData<SearchQuery> {

	private static SearchQueryLiveData sInstance;

	private static final String SEARCH_QUERY_FILE = "searchQueryFile";
	private static final String SEARCH_QUERY_DATA_KEY = "searchDataKey";

	@NonNull
	public static SearchQueryLiveData get() {
		if (sInstance == null) {
			sInstance = new SearchQueryLiveData();
		}
		return sInstance;
	}

	private SearchQueryLiveData() {
		SearchQuery searchQuery = readSearchQuery();
		if (searchQuery == null) {
			searchQuery = new SearchQuery();
		}
		setSearchQuery(searchQuery);
	}

	public void setSearchQuery(@NonNull SearchQuery searchQuery) {
		writeSearchQuery(searchQuery);
		setValue(searchQuery);
	}

	@NonNull
	private SharedPreferences getSharedPref() {
		return FlickrImageViewerApp.getContext().getSharedPreferences(SEARCH_QUERY_FILE, Context.MODE_PRIVATE);
	}

	private void writeSearchQuery(@NonNull SearchQuery searchQuery) {
		SharedPreferences sharedPref = getSharedPref();
		SharedPreferences.Editor editor = sharedPref.edit();
		String json = new Gson().toJson(searchQuery);
		editor.putString(SEARCH_QUERY_DATA_KEY, json);
		editor.apply();
	}

	private SearchQuery readSearchQuery() {
		SharedPreferences sharedPref = getSharedPref();
		String json = sharedPref.getString(SEARCH_QUERY_DATA_KEY, "");
		return new Gson().fromJson(json, SearchQuery.class);
	}

	@SuppressWarnings("ConstantConditions")
	@NonNull
	@Override
	public SearchQuery getValue() {
		return super.getValue();
	}
}