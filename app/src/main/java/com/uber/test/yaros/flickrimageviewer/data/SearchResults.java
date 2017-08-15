package com.uber.test.yaros.flickrimageviewer.data;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class SearchResults {
	/*
	{
		"photos": {
			PHOTOS OBJ
		},
		"stat": "ok"
		}
	 */

	@SerializedName("photos")
	private PhotoPage data;

	@SerializedName("stat")
	private String status;

	@SerializedName("message")
	private String message;

	@Nullable
	public PhotoPage getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public boolean isValid() {
		return TextUtils.equals(status, "ok");
	}
}
