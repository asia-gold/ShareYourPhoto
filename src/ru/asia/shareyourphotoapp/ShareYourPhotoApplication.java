package ru.asia.shareyourphotoapp;

import android.app.Application;

public class ShareYourPhotoApplication extends Application {

	private static DraftDataSource dataSource;

	@Override
	public void onCreate() {
		super.onCreate();
		dataSource = new DraftDataSource(this);
		dataSource.open();
	}

	public static DraftDataSource getDataSource() {
		return dataSource;
	}

}
