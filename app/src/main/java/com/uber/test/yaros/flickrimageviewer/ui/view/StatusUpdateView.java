package com.uber.test.yaros.flickrimageviewer.ui.view;

import android.annotation.TargetApi;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.repository.PhotosLiveData;

public class StatusUpdateView extends LinearLayout implements LifecycleOwner {

	@SuppressWarnings("NullableProblems")
	@NonNull
	private ProgressBar progressBar;

	@SuppressWarnings("NullableProblems")
	@NonNull
	private TextView progressDescription;
	private Lifecycle lifecycleOwner;

	public StatusUpdateView(@NonNull Context context) {
		super(context);
		init();
	}

	public StatusUpdateView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StatusUpdateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@SuppressWarnings("unused")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public StatusUpdateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);

		View view = inflate(getContext(), R.layout.status_update_view_layout, this);

		progressBar = view.findViewById(R.id.progress_bar);
		progressDescription = view.findViewById(R.id.progress_description);

		setVisibility(GONE);
	}

	public void initView(@NonNull Lifecycle owner) {
		lifecycleOwner = owner;

		setVisibility(VISIBLE);

		PhotosLiveData.get().observe(this, listOfPhotosData -> {
			if (listOfPhotosData != null) {
				switch (listOfPhotosData.status) {
					case SUCCESS:
						//noinspection ConstantConditions
						if (listOfPhotosData.data.isEmpty()) {
							progressBar.setVisibility(GONE);
							progressDescription.setText(getResources().getString(R.string.status_search_no_photos));
						} else {
							setVisibility(GONE);
						}
						break;
					case ERROR:
						progressBar.setVisibility(GONE);
						progressDescription.setText(getResources().getString(R.string.status_search_failed));
						break;
					case LOADING:
						progressDescription.setText(getResources().getString(R.string.status_loading));
						progressBar.setVisibility(VISIBLE);
						setVisibility(VISIBLE);
						break;
				}
			}
		});
	}

	@Override
	public Lifecycle getLifecycle() {
		return lifecycleOwner;
	}
}