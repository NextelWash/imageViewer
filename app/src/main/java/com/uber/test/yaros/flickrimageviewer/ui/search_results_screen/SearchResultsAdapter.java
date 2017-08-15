package com.uber.test.yaros.flickrimageviewer.ui.search_results_screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uber.test.yaros.flickrimageviewer.BuildConfig;
import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.data.Photo;
import com.uber.test.yaros.flickrimageviewer.ui.view.PhotoView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int PHOTO_TYPE = 1;

	@NonNull
	private List<Photo> data = new LinkedList<>();

	@NonNull
	private Context context;

	SearchResultsAdapter(@NonNull Context context) {
		this.context = context;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder = null;

		switch (viewType) {
			case PHOTO_TYPE:
				viewHolder = createItemViewHolder(parent);
				break;
			default:
				if (BuildConfig.DEBUG) {
					throw new RuntimeException(context.getString(R.string.error_not_supported_default));
				}
				break;
		}

		return viewHolder;
	}

	private ItemViewHolder createItemViewHolder(ViewGroup parent) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.search_result_item, parent, false);

		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		switch (getItemViewType(position)) {
			case PHOTO_TYPE:
				ItemViewHolder item = (ItemViewHolder) holder;
				item.setContent(position, getItem(position));
				break;
			default:
				if (BuildConfig.DEBUG) {
					throw new RuntimeException(context.getString(R.string.error_not_supported_default));
				}
				break;
		}
	}

	private Photo getItem(int position) {
		return data.get(position);
	}

	public void setData(@Nullable List<Photo> items) {
		if (items == null) {
			data = new ArrayList<>();
		} else {
			data = new ArrayList<>(items);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public int getItemViewType(int position) {
		return PHOTO_TYPE;
	}

	private static class ItemViewHolder extends RecyclerView.ViewHolder {

		@NonNull
		private PhotoView photoView;

		ItemViewHolder(@NonNull View itemView) {
			super(itemView);
			photoView = itemView.findViewById(R.id.photo_view);
		}

		void setContent(int position, @NonNull Photo photo) {
			photoView.initPhotoView(position, photo);
		}
	}
}