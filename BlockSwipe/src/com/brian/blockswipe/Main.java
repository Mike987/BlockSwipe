package com.brian.blockswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Main extends Activity {
	ImageButton start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent openGame = new Intent("com.brian.blockswipe.STARTLAUNCH");
		startActivity(openGame);

	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
}
