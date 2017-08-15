package com.uber.test.yaros.flickrimageviewer.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class ExecutorUtils {

	private static ExecutorUtils instance;
	private final Executor mainThread;

	public static ExecutorUtils getInstance() {
		if (instance == null) {
			synchronized (ExecutorUtils.class) {
				if (instance == null) {
					instance = new ExecutorUtils();
				}
			}
		}
		return instance;
	}

	private ExecutorUtils() {
		this(new MainThreadExecutor());
	}

	private ExecutorUtils(@NonNull Executor mainThread) {
		this.mainThread = mainThread;
	}

	public Executor mainThread() {
		return mainThread;
	}

	private static class MainThreadExecutor implements Executor {
		private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(@NonNull Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}
