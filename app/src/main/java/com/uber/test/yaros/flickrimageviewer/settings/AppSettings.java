package com.uber.test.yaros.flickrimageviewer.settings;

import java.util.Locale;

public class AppSettings {

	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	/**
	 * when to load the next page for SR
	 */
	public static final int LOAD_NEXT_PAGE_THRESHOLD = 6;

	/**
	 * How many images to request per page
	 */
	public static final int LOAD_PHOTOS_PER_PAGE = 18; // default is 100

	/**
	 * How many columns to show on SR page
	 */
	public static final int NUMBER_OF_COLUMNS = 3;
}
