package com.uber.test.yaros.flickrimageviewer;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class FlickrImageViewerApp extends Application {

	private static FlickrImageViewerApp instance;

	public FlickrImageViewerApp() {
		instance = this;

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Stetho.initializeWithDefaults(this);
		}
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}

}