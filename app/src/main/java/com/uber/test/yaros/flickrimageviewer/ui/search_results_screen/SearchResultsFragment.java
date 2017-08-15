package com.uber.test.yaros.flickrimageviewer.ui.search_results_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.settings.AppSettings;
import com.uber.test.yaros.flickrimageviewer.ui.BaseLifeCycleFragment;
import com.uber.test.yaros.flickrimageviewer.ui.view.StatusUpdateView;
import com.uber.test.yaros.flickrimageviewer.utils.Status;

public class SearchResultsFragment extends BaseLifeCycleFragment {

	private SearchResultsViewModel searchResultsViewModel;

	private SearchResultsAdapter srAdapter;

	/**
	 * Progress bar to display when new page is loaded
	 */
	private ProgressBar loadMoreBar;

	@Override
	protected String getScreenTitle() {
		return getString(R.string.search_results_screen_name);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		searchResultsViewModel = ViewModelProviders.of(this).get(SearchResultsViewModel.class);
		searchResultsViewModel.getPhotosData().observe(this, listOfPhotosData -> {
			if (listOfPhotosData != null) {
				if (listOfPhotosData.status == Status.SUCCESS) {
					srAdapter.setData(listOfPhotosData.data);
				}
			}
		});

		searchResultsViewModel.getLoadMoreStatus().observe(this, loadingMore -> {
			if (loadingMore == null) {
				loadMoreBar.setVisibility(View.INVISIBLE);
			} else {
				loadMoreBar.setVisibility(loadingMore.isLoading() ? View.VISIBLE : View.INVISIBLE);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_results_fragment_layout, container, false);

		loadMoreBar = view.findViewById(R.id.load_more_bar);

		StatusUpdateView statusUpdateView = view.findViewById(R.id.status_update_view);
		statusUpdateView.initView(getLifecycle());

		RecyclerView srRecyclerView = view.findViewById(R.id.search_results_recycler_view);
		initRecyclerView(srRecyclerView);

		return view;
	}

	private void initRecyclerView(RecyclerView recyclerView) {
		srAdapter = new SearchResultsAdapter(getContext());

		recyclerView.setAdapter(srAdapter);

		final LinearLayoutManager srLayoutManager = new GridLayoutManager(getContext(), AppSettings.NUMBER_OF_COLUMNS, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(srLayoutManager);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				if (dy > 0) {
					int totalItemsCount = srLayoutManager.getItemCount();
					int lastVisibleItem = srLayoutManager.findLastVisibleItemPosition();

					if (lastVisibleItem >= totalItemsCount - AppSettings.LOAD_NEXT_PAGE_THRESHOLD) {
						searchResultsViewModel.loadNextPage();
					}
				}
			}
		});
	}
}
