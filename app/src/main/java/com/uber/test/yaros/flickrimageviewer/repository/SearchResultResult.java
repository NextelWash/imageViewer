package com.uber.test.yaros.flickrimageviewer.repository;

import android.support.annotation.NonNull;

import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;

/**
 * unfortunate naming =`(
 * <p>
 * results of the request for search results
 */

class SearchResultResult {
	private SearchQuery searchQuery;
	private int totalNumOfPages;
	private int pageToLoad;

	SearchResultResult(@NonNull SearchQuery searchQuery, int pageToLoad, int totalNumOfPages) {
		this.searchQuery = searchQuery;
		this.pageToLoad = pageToLoad;
		this.totalNumOfPages = totalNumOfPages;
	}

	boolean isAllPagesLoaded() {
		return pageToLoad >= totalNumOfPages;
	}

	SearchQuery getSearchQuery() {
		return searchQuery;
	}

	int getPageToLoad() {
		return pageToLoad;
	}
}
