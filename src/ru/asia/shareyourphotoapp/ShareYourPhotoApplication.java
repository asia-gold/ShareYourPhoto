package ru.asia.shareyourphotoapp;

import android.app.Application;
import android.util.Log;

public class ShareYourPhotoApplication extends Application {

	private static DraftDataSource dataSource;

	@Override
	public void onCreate() {
		super.onCreate();
		dataSource = new DraftDataSource(this);
		dataSource.open();
		Log.e("MyApp", "DataSource initialize");
	}

	public static DraftDataSource getDataSource() {
		return dataSource;
	}

}
