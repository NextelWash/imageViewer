package com.uber.test.yaros.flickrimageviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.uber.test.yaros.flickrimageviewer.navigation.NavigationController;
import com.uber.test.yaros.flickrimageviewer.navigation.NavigationControllerInterface;

public class MainActivity extends AppCompatActivity implements NavigationControllerInterface {

	private NavigationController navigationController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			navigateToInitialScreen();
		}
	}

	@Override
	public NavigationController getNavigationController() {
		if (navigationController == null) {
			navigationController = new NavigationController(this);
		}
		return navigationController;
	}

	private void navigateToInitialScreen() {
		getNavigationController().navigateToSearch();
	}
}
