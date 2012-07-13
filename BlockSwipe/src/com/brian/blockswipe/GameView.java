package com.brian.blockswipe;

import com.brian.blockswipe.BotStuff.Bot;
import com.brian.blockswipe.BotStuff.LevelGenerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	Bot bot = new Bot();
	LevelGenerator LG = new LevelGenerator();
	private SurfaceHolder holder;
	private Bitmap block, wall, finish, key, keyhole, button, buttonBlock,
			buttonBlockOpen, buttonPressed;
	int x, width, height;
	int y = -50;
	Block b = new Block();
	PB pb = new PB(getContext());
	LevelSelectScreen level = new LevelSelectScreen();
	private GameLoopThread gameLoopThread;
	Typeface font;
	Paint paint;
	
	//Bot stuff
	int[] botMoves = new int[100];
	boolean solvable;
	int count = 25;
	boolean blockStopped = true;
	int MOVE_UP = 1;
	int MOVE_RIGHT = 2;
	int MOVE_DOWN = 3;
	int MOVE_LEFT = 4;
	boolean runBot = false;
	boolean once = true;
	boolean movingy = false;
	boolean movingx = false;
	

	public GameView(Context context) {

		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		font = Typeface.createFromAsset(context.getAssets(), "Square.ttf");
		paint = new Paint();

		holder.addCallback(new SurfaceHolder.Callback() {
			public void surfaceDestroyed(SurfaceHolder holder) {
				gameLoopThread.running = false;
				try {
					gameLoopThread.join();
				} catch (InterruptedException e) {
				}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				gameLoopThread.setRunning(true);
				gameLoopThread.start();

			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

		});

		block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
		wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
		finish = BitmapFactory
				.decodeResource(getResources(), R.drawable.finish);
		key = BitmapFactory.decodeResource(getResources(), R.drawable.key);
		keyhole = BitmapFactory.decodeResource(getResources(),
				R.drawable.keyhole);
		button = BitmapFactory
				.decodeResource(getResources(), R.drawable.button);
		buttonBlock = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonblock);
		buttonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonpressed);
		buttonBlockOpen = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonblockopen);

	}

	public void GameOver() {
		pb.save(LevelSelectScreen.x, b.movecounter);
		GameLaunch.homecheck = false;
		gameLoopThread.setRunning(false);
		Intent openGame = new Intent("com.brian.blockswipe.LEVELCOMPLETE");
		((Activity) getContext()).startActivity(openGame);
	}

	protected void onDraw(Canvas canvas) {
		int lx = LevelSelectScreen.x;

		width = (((getWidth() - 450) / 2) - 50);
		height = (((getHeight() - 800) / 2) - 50);
		canvas.drawColor(Color.WHITE);

		for (int i = 0; i < 198; i++) {

			x = (i % 11) * 50;

			if (x == 0)
				y = y + 50;

			if (level.levels[lx][i] == 1) {
				canvas.drawBitmap(wall, x + width, y + height, null);
			}
			if (level.levels[lx][i] == 3) {
				canvas.drawBitmap(finish, x + width, y + height, null);
			}
			if (LevelSelectScreen.keyPickedUp == false) {
				if (level.levels[lx][i] == 4) {
					canvas.drawBitmap(key, x + width, y + height, null);
				}
				if (level.levels[lx][i] == 5) {
					canvas.drawBitmap(keyhole, x + width, y + height, null);
				}
			}

			if (LevelSelectScreen.buttonPressed == false) {
				if (level.levels[lx][i] == 6) {
					canvas.drawBitmap(button, x + width, y + height, null);
				}
				if (level.levels[lx][i] == 7) {
					canvas.drawBitmap(buttonBlock, x + width, y + height, null);
				}
				if (level.levels[lx][i] == 8) {
					canvas.drawBitmap(buttonBlockOpen, x + width, y + height,
							null);
				}
			}

			if (LevelSelectScreen.buttonPressed == true) {
				if (level.levels[lx][i] == 6) {
					canvas.drawBitmap(buttonPressed, x + width, y + height,
							null);
				}
				if (level.levels[lx][i] == 7) {
					canvas.drawBitmap(buttonBlockOpen, x + width, y + height,
							null);
				}
				if (level.levels[lx][i] == 8) {
					canvas.drawBitmap(buttonBlock, x + width, y + height, null);
				}
			}

		}

		paint.setColor(Color.BLACK);
		paint.setTypeface(font);
		paint.setTextSize(20);
		paint.setFakeBoldText(true);

		if (b.gameOver == true) {
			GameOver();
		}
		x = 0;
		y = -50;
		b.move();
		canvas.drawBitmap(block, b.getX() + width, b.getY() + height, null);
		canvas.drawText("Total Moves: " + b.movecounter, 10, 25, paint);
		canvas.drawText("Minimum Moves: " + level.levels[lx][198], 200, 25,
				paint);
		
	}
	
	
	public void setRun() {
		runBot = true;
	}
	
	// Mostly just reads off directions and moves etc.
	public void runBot() {

		if(runBot = true){
			
			if(once == true){
		
		
		botMoves = bot.solve(level.levels[LevelSelectScreen.x], b.location); //Solve puzzle
		once = false;
			}
			if(count == 0){
				count = 0;
				runBot = false;
				
			}
		
		if (botMoves[count] == MOVE_UP && blockStopped == true) {
			b.moveUp();
			blockStopped = false;	
			movingy = true;
		}
		if (botMoves[count] == MOVE_DOWN && blockStopped == true) {
			b.moveDown();
			blockStopped = false;
			movingy = true;
		
		}
		if (botMoves[count] == MOVE_RIGHT && blockStopped == true) {
			b.moveRight();
			blockStopped = false;	
			movingx = true;
		}
		if (botMoves[count] == MOVE_LEFT && blockStopped == true) {
			b.moveLeft();
			blockStopped = false;
			movingx = true;

		}
		if((botMoves[count] > 4 ||botMoves[count] <= 0) && blockStopped == true ){
			count--;
		}
		if(b.getdy() == 0 && movingy) {
			count--;
			blockStopped = true;
			movingy = false;
		}
		if(b.getdx() == 0 && movingx) {
			count--;
			blockStopped = true;
			movingx = false;
		}
		}
		
	}
//
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!runBot) {
			b.OnTouch(event);
		}
		return true;

	}

}