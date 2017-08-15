package com.uber.test.yaros.flickrimageviewer.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ResponseWrapper<T> {

	@NonNull
	public final Status status;

	@Nullable
	public final String message;

	@Nullable
	public final T data;

	private ResponseWrapper(@NonNull Status status, @Nullable T data, @Nullable String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public static <T> ResponseWrapper<T> success(@Nullable T data) {
		return new ResponseWrapper<>(Status.SUCCESS, data, null);
	}

	public static <T> ResponseWrapper<T> error(String msg, @Nullable T data) {
		return new ResponseWrapper<>(Status.ERROR, data, msg);
	}

	public static <T> ResponseWrapper<T> loading(@Nullable T data) {
		return new ResponseWrapper<>(Status.LOADING, data, null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ResponseWrapper<?> responseWrapper = (ResponseWrapper<?>) o;

		if (status != responseWrapper.status) {
			return false;
		}
		if (message != null ? !message.equals(responseWrapper.message) : responseWrapper.message != null) {
			return false;
		}
		return data != null ? data.equals(responseWrapper.data) : responseWrapper.data == null;
	}

	@Override
	public int hashCode() {
		int result = status.hashCode();
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ResponseWrapper{" +
				"status=" + status +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}
