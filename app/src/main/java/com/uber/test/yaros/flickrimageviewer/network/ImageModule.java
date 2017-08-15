package com.uber.test.yaros.flickrimageviewer.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageModule {

	private static ImageModule instance;

	public static ImageModule getInstance() {
		if (instance == null) {
			synchronized (ImageModule.class) {
				if (instance == null) {
					instance = new ImageModule();
				}
			}
		}
		return instance;
	}

	public void loadImage(@NonNull Context context, @NonNull final String imageUrl, @NonNull ImageView holder) {
		Glide.with(context)
				.load(imageUrl)
				.into(holder);
	}
}
