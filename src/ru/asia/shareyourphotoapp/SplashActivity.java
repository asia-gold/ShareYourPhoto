package ru.asia.shareyourphotoapp;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Show splash screen for 3 seconds, then start MainActivity.
 * 
 * @author Asia
 *
 */
public class SplashActivity extends Activity {
	
	private static final int SPLASH_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();				
			}
		};
		timer.schedule(timerTask, SPLASH_TIME);
	}
}
