package com.uber.test.yaros.flickrimageviewer.ui.search_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.data.SearchQuery;
import com.uber.test.yaros.flickrimageviewer.ui.BaseLifeCycleFragment;

public class SearchFragment extends BaseLifeCycleFragment {

	private SearchViewModel searchViewModel;
	private EditText searchInput;

	@Override
	protected String getScreenTitle() {
		return getString(R.string.app_name);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
		searchViewModel.getSearchQueryData().observe(this, data -> {
			if (data != null) {
				String searchCriteria = data.getSearchCriteria();
				if (!TextUtils.isEmpty(searchCriteria) && TextUtils.isEmpty(searchInput.getText())) {
					searchInput.setText(searchCriteria);
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_fragment_layout, container, false);

		searchInput = view.findViewById(R.id.search_input);
		searchInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
			if (actionId == EditorInfo.IME_ACTION_GO) {
				onSrButtonClicked();
				return true;
			}

			return false;
		});

		Button srButton = view.findViewById(R.id.search_button);
		srButton.setOnClickListener(view1 -> onSrButtonClicked());

		return view;
	}

	private void onSrButtonClicked() {
		SearchQuery searchQuery = new SearchQuery(searchInput.getText().toString());
		searchViewModel.onSearchUpdated(searchQuery);

		getNavigationController().navigateToSearchResults();
	}
}
