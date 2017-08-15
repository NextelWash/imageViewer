package com.uber.test.yaros.flickrimageviewer.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.ui.search_results_screen.SearchResultsFragment;
import com.uber.test.yaros.flickrimageviewer.ui.search_screen.SearchFragment;

public class NavigationController {

	private final int containerId;
	private final FragmentManager fragmentManager;

	public NavigationController(AppCompatActivity mainActivity) {
		this.containerId = R.id.container;
		this.fragmentManager = mainActivity.getSupportFragmentManager();
	}

	public void navigateToSearch() {
		SearchFragment fragment = new SearchFragment();
		fragmentManager.beginTransaction()
				.replace(containerId, fragment)
				.commitAllowingStateLoss();
	}

	public void navigateToSearchResults() {
		SearchResultsFragment fragment = new SearchResultsFragment();
		fragmentManager.beginTransaction()
				.replace(containerId, fragment)
				.addToBackStack(null)
				.commitAllowingStateLoss();
	}

}