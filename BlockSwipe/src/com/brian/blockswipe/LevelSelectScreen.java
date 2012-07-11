package com.brian.blockswipe;

import com.brian.blockswipe.BotStuff.LevelGenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class LevelSelectScreen extends Activity {
	ImageButton easy, med, hard;
	Intent openGame = new Intent("com.brian.blockswipe.GAMELAUNCH");
	public static int x;
	public static int[] levelpicked;
	public static boolean keyPickedUp = false;
	public static boolean buttonPressed = false;
	int levelsComp;
	public static LevelGenerator LG = new LevelGenerator();
	public static int[] GLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		levelsComp = getApplicationContext().getSharedPreferences(PB.pB,
				MODE_PRIVATE).getInt("levelComp", -1);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.leveldiff);

		easy = (ImageButton) findViewById(R.id.bEasy);
		med = (ImageButton) findViewById(R.id.bMed);
		hard = (ImageButton) findViewById(R.id.bHard);



		// for (int i = 0; i <= levelsComp + 1; i++) {
		// if (i == 18) {
		// break;
		// }
		// levelbuttons[i].setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.levbutton));
		// }

	}

	public void ButtonOnClick(View v) {

		switch (v.getId()) {
		case R.id.bEasy:
			x = 0;
			game.start();
			break;
		case R.id.bMed:
			x = 1;
			game.start();
			break;
		case R.id.bHard:
			x = 2;
			game.start();
			break;
		}
	}

	// 0 for blank square
	// 1 for wall
	// 2 for start
	// 3 for finish
	// 4 for key
	// 5 for key block
	// 6 for button
	// 7 button block up
	// 8 button block down

	int[] level1 = GLevel;
	int[] level2 = GLevel;
	int[] level3 = GLevel;

	int[][] levels = { level1, level2, level3 };
	

	Thread game = new Thread() {
		public void run() {
			try {

				if (x == 0) {
					GLevel = LG.generate(0);
				}
				if (x == 1) {
					GLevel = LG.generate(1);
				}
				if (x == 2) {
					GLevel = LG.generate(2);
				}
			} finally {
				startActivity(openGame);
			}

		}
	};

	@Override
	public void onBackPressed() {
		Thread game = new Thread() {
			public void run() {
				try {
					sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

					Intent openGame = new Intent(
							"com.brian.blockswipe.STARTLAUNCH");
					startActivity(openGame);

				}

			}
		};
		game.start();
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
