package com.brian.blockswipe;


import com.brian.blockswipe.BotStuff.LevelGenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class GameMenu extends Activity {

	private ImageButton select, cont, replay;
	public static LevelGenerator LG = new LevelGenerator();
	public static int[] GLevel;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gamemenu);
		
		cont = (ImageButton) findViewById(R.id.bCont);
		
		cont.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Thread game = new Thread() {
					public void run() {
						try {
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							onPause();
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
							Intent openGame = new Intent("com.brian.blockswipe.LEVELS");
							startActivity(openGame);
							GameLaunch.h.sendEmptyMessage(0);
						}

					}
				};
				game.start();
			}
		});
		
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
							startActivity(intent);

						}

					}
				};
				game.start();
			}
		});

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		}
}
