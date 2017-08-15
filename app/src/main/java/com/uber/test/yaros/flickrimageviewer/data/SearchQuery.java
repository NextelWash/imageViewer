package com.uber.test.yaros.flickrimageviewer.data;

import android.support.annotation.Nullable;

public class SearchQuery {

	private String searchCriteria;

	public SearchQuery() {
		this.searchCriteria = null;
	}

	public SearchQuery(@Nullable String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Nullable
	public String getSearchCriteria() {
		return searchCriteria;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SearchQuery that = (SearchQuery) o;

		return searchCriteria != null ? searchCriteria.equals(that.searchCriteria) : that.searchCriteria == null;
	}

	@Override
	public int hashCode() {
		return searchCriteria != null ? searchCriteria.hashCode() : 0;
	}
}

