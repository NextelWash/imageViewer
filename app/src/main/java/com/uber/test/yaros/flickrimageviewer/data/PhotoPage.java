package com.uber.test.yaros.flickrimageviewer.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoPage {

		/*
		{
			"page": 1,
			"pages": 2220,
			"perpage": 100,
			"total": "221908",
			"photo": [
				{
					PHOTO_OBJs...
				},
			...
			]
		}
	 */

	@SerializedName("page")
	private int currentPage;

	@SerializedName("pages")
	private int totalNumOfPages;

	@SerializedName("perpage")
	private int photosPerPage;

	@SerializedName("total")
	private int totalAmountOfPhotos;

	@SerializedName("photo")
	private List<Photo> photos;

	public int getCurrentPage() {
		return currentPage;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public int getTotalNumOfPages() {
		return totalNumOfPages;
	}
}
