package com.brian.blockswipe;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class GameLaunch extends Activity {
	/** Called when the activity is first created. */
	int x;
	Block b = new Block();
	public static Handler h;
	public static boolean homecheck = true;
	GameView game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		homecheck = true;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		game = new GameView(this);
		setContentView(game);

		h = new Handler() {

			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {

				case 0:
					finish();
					break;

				}
			}

		};

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
					if (b.gameOver == false) {
						homecheck = false;
						Intent openGame = new Intent(
								"com.brian.blockswipe.GAMEMENUP");
						startActivity(openGame);
					}
				}

			}
		};
		game.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (homecheck == true) {
			finish();
		}
	}
	
	// Create solve button for running the bot
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bot_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.botMenu:
			
			game.runBot = true;
			game.setRun();
			
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}