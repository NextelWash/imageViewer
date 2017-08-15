package com.uber.test.yaros.flickrimageviewer.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.uber.test.yaros.flickrimageviewer.R;
import com.uber.test.yaros.flickrimageviewer.navigation.NavigationController;
import com.uber.test.yaros.flickrimageviewer.navigation.NavigationControllerInterface;

public class BaseLifeCycleFragment extends LifecycleFragment {

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(getScreenTitle());
		}
	}

	protected String getScreenTitle() {
		return getContext().getString(R.string.app_name);
	}

	protected NavigationController getNavigationController() {
		NavigationControllerInterface navigationControllerInterface = ((NavigationControllerInterface) getActivity());
		return navigationControllerInterface.getNavigationController();
	}

}
