package com.uber.test.yaros.flickrimageviewer.data;

import com.google.gson.annotations.SerializedName;

public class Photo {

	/*
	 "id": "36569551935",
	 "owner": "148036032@N07",
	 "secret": "83c1ba4b55",
	 "server": "4420",
	 "farm": 5,
	 "title": "more of my best friend evie - The Caturday",
	 "ispublic": 1,
	 "isfriend": 0,
	 "isfamily": 0
	 */

	@SerializedName("id")
	private long id;

	@SerializedName("owner")
	private String owner;

	@SerializedName("secret")
	private String secret;

	@SerializedName("server")
	private int server;

	@SerializedName("farm")
	private int farm;

	@SerializedName("title")
	private String title;

	@SerializedName("ispublic")
	private int ispublic;

	@SerializedName("isfriend")
	private int isfriend;

	@SerializedName("isfamily")
	private int isfamily;

	public int getFarm() {
		return farm;
	}

	public int getServer() {
		return server;
	}

	public String getSecret() {
		return secret;
	}

	public String getTitle() {
		return title;
	}

	public long getId() {
		return id;
	}
}
