package com.uber.test.yaros.flickrimageviewer.network;

import com.uber.test.yaros.flickrimageviewer.data.SearchResults;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NetworkService {

	@GET("services/rest/?method=flickr.photos.search")
	Call<SearchResults> getSearchResults(@QueryMap Map<String, String> searchOptions);
}
