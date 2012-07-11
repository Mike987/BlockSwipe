package com.brian.blockswipe;

import com.brian.blockswipe.BotStuff.LevelGenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class LevelComplete extends Activity {

	private ImageButton select, replay;
	private ImageButton nextlvl;
	public static LevelGenerator LG = new LevelGenerator();
	LevelSelectScreen level = new LevelSelectScreen();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.levelcomp);

		replay = (ImageButton) findViewById(R.id.bReplay);

		replay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread game = new Thread() {
					public void run() {
						try {
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LevelSelectScreen.keyPickedUp = false;
							LevelSelectScreen.buttonPressed = false;
							Intent intent = new Intent(
									"com.brian.blockswipe.GAMELAUNCH");
							GameLaunch.h.sendEmptyMessage(0);
							finish();
							startActivity(intent);

						}

					}
				};
				game.start();
			}
		});

		select = (ImageButton) findViewById(R.id.bSelect);

		select.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Thread game = new Thread() {
					public void run() {
						try {
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LevelSelectScreen.keyPickedUp = false;
							LevelSelectScreen.buttonPressed = false;
							Intent openGame = new Intent(
									"com.brian.blockswipe.LEVELS");
							startActivity(openGame);
							finish();
							GameLaunch.h.sendEmptyMessage(0);
						}

					}
				};
				game.start();
			}
		});

		nextlvl = (ImageButton) findViewById(R.id.bNext);

		nextlvl.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Thread game = new Thread() {
					public void run() {
						try {
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LevelSelectScreen.keyPickedUp = false;
							LevelSelectScreen.buttonPressed = false;

							LevelSelectScreen.GLevel = LG.generate(LevelSelectScreen.x);

							Intent openGame = new Intent(
							"com.brian.blockswipe.GAMELAUNCH");
							startActivity(openGame);

							finish();
							GameLaunch.h.sendEmptyMessage(0);
						}


					}
				};
				game.start();
			}
		});

	}

	@Override
	public void onBackPressed() {
		Thread game = new Thread() {
			public void run() {
				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					LevelSelectScreen.keyPickedUp = false;
					LevelSelectScreen.buttonPressed = false;
					Intent openGame = new Intent("com.brian.blockswipe.LEVELS");
					startActivity(openGame);
					finish();
					GameLaunch.h.sendEmptyMessage(0);
				}

			}
		};
		game.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (GameLaunch.homecheck == true) {
			finish();
		}
	}

}