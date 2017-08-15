package com.uber.test.yaros.flickrimageviewer.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.uber.test.yaros.flickrimageviewer.settings.BackendSettings;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

	private NetworkService networkService;
	private static NetworkModule networkModule;

	public static NetworkModule getInstance() {
		if (networkModule == null) {
			synchronized (NetworkModule.class) {
				if (networkModule == null) {
					networkModule = new NetworkModule();
				}
			}
		}

		return networkModule;
	}

	private NetworkModule() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.addNetworkInterceptor(new StethoInterceptor())
				.build();

		networkService = new Retrofit.Builder()
				.baseUrl(BackendSettings.BASE_URL)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(NetworkService.class);
	}

	@NonNull
	public NetworkService getNetworkService() {
		return networkService;
	}
}
