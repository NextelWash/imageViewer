package com.uber.test.yaros.flickrimageviewer.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.data.Photo;
import com.uber.test.yaros.flickrimageviewer.network.ImageModule;
import com.uber.test.yaros.flickrimageviewer.settings.AppSettings;

public class PhotoView extends LinearLayout {

	@SuppressWarnings("NullableProblems")
	@NonNull
	private ImageView photoImageView;

	@SuppressWarnings("NullableProblems")
	@NonNull
	private TextView photoDescription;

	@SuppressWarnings("NullableProblems")
	@NonNull
	private String imageUrlTemplate;

	public PhotoView(@NonNull Context context) {
		super(context);
		init();
	}

	public PhotoView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PhotoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public PhotoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		imageUrlTemplate = getResources().getString(R.string.photo_url);

		View view = inflate(getContext(), R.layout.photo_view, this);

		photoImageView = view.findViewById(R.id.photo);
		photoDescription = view.findViewById(R.id.photo_description);
	}

	public void initPhotoView(int position, @Nullable Photo photo) {
		if (photo == null) {
			return;
		}

		if (!TextUtils.isEmpty(photo.getTitle())) {
			photoDescription.setText(photo.getTitle());
			photoDescription.setVisibility(VISIBLE);
		} else {
			photoDescription.setVisibility(GONE);
		}

		String imageUrl = getImageString(photo);
		ImageModule.getInstance().loadImage(getContext(), imageUrl, photoImageView);

		setOnClickListener(view -> {
			String text = String.format(getResources().getString(R.string.photo_clicked_text), position);
			Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		});
	}

	private String getImageString(@NonNull Photo photo) {
		return String.format(AppSettings.DEFAULT_LOCALE,
				imageUrlTemplate,
				photo.getFarm(),
				photo.getServer(),
				photo.getId(),
				photo.getSecret());
	}
}
