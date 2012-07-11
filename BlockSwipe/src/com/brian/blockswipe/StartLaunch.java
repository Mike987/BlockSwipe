package com.brian.blockswipe;




import com.brian.blockswipe.BotStuff.LevelGenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class StartLaunch extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start);
		

		ImageButton start = (ImageButton) findViewById(R.id.bStart);
		
		start.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Thread game = new Thread() {
					public void run() {
						try {
							sleep(50);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							
							Intent openGame = new Intent(
									"com.brian.blockswipe.LEVELS");
							startActivity(openGame);
						}

					}
				};
				game.start();
			}
		});
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StartScreen.isRunning = false;
		finish();
	}
	
}
