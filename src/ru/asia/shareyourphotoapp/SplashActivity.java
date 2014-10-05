package ru.asia.shareyourphotoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	
	private static final int SPLASH_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Thread splashThread = new Thread() {
			
			@Override
			public void run() {
				try {
					sleep(SPLASH_TIME);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}				 
			}
		};
		splashThread.start();
	}
}
