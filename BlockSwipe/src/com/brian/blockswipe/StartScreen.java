package com.brian.blockswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class StartScreen extends SurfaceView implements Runnable {

	SurfaceHolder holder;
	Thread thread = null;
	public static boolean isRunning = true;
	private Bitmap block, wall, finish, key, keyhole, button, buttonBlock, buttonBlockOpen, buttonPressed;
	int midBlock;
	StartAnim b = new StartAnim();
	private int x, width, height;
	int y = -50;

	int[] startlvl = {
			1,1,1,1,1,1,1,1,1,1,1,
			1,0,0,0,0,0,0,1,0,0,1,
			1,0,1,6,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,1,
			1,0,8,0,0,0,0,7,0,0,1,
			1,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,1,3,0,0,0,1,
			1,0,0,0,7,0,0,0,0,0,1,
			1,0,1,0,0,2,0,8,0,0,1,
			1,0,0,0,0,0,0,0,1,0,1,
			1,0,0,0,0,0,1,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,1,
			1,1,0,0,0,0,0,0,0,0,1,
			1,0,0,7,0,0,0,0,0,1,1,
			1,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,1,0,0,0,0,0,1,
			1,1,0,0,0,0,0,6,8,0,1,
			1,1,1,1,1,1,1,1,1,1,1,};

	public StartScreen(Context context, AttributeSet attributeSet) {
		super(context);
		holder = getHolder();
		thread = new Thread(this);
		isRunning = true;
		thread.start();

		block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
		wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
		finish = BitmapFactory
				.decodeResource(getResources(), R.drawable.finish);
		key = BitmapFactory.decodeResource(getResources(), R.drawable.key);
		keyhole = BitmapFactory.decodeResource(getResources(),
				R.drawable.keyhole);
		button = BitmapFactory.decodeResource(getResources(),
				R.drawable.button);
		buttonBlock = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonblock);
		buttonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonpressed);
		buttonBlockOpen = BitmapFactory.decodeResource(getResources(),
				R.drawable.buttonblockopen);


	}

	public void run() {
		while (isRunning) {
			if (!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			onDraw(c);
			// start = false;
			holder.unlockCanvasAndPost(c);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {

		width = (((getWidth() - 450) / 2) - 50);
		height = (((getHeight() - 800) / 2) - 50);
		canvas.drawColor(Color.WHITE);

		for (int i = 0; i < 198; i++) {

			x = (i % 11) * 50;

			if (x == 0)
				y = y + 50;

			if (startlvl[i] == 1) {
				canvas.drawBitmap(wall, x + width, y + height, null);
			}
			if (startlvl[i] == 3) {
				canvas.drawBitmap(finish, x + width, y + height, null);
			}
			if (LevelSelectScreen.keyPickedUp == false) {
				if (startlvl[i] == 4) {
					canvas.drawBitmap(key, x + width, y + height, null);
				}
				if (startlvl[i] == 5) {
					canvas.drawBitmap(keyhole, x + width, y + height, null);
				}
			}

			if (LevelSelectScreen.buttonPressed == false) {
				if (startlvl[i] == 6) {
					canvas.drawBitmap(button, x + width, y + height, null);
				}
				if (startlvl[i] == 7) {
					canvas.drawBitmap(buttonBlock, x + width, y + height, null);
				}
				if (startlvl[i] == 8) {
					canvas.drawBitmap(buttonBlockOpen, x + width, y + height,
							null);
				}
			}

			if (LevelSelectScreen.buttonPressed == true) {
				if (startlvl[i] == 6) {
					canvas.drawBitmap(buttonPressed, x + width, y + height,
							null);
				}
				if (startlvl[i] == 7) {
					canvas.drawBitmap(buttonBlockOpen, x + width, y + height,
							null);
				}
				if (startlvl[i] == 8) {
					canvas.drawBitmap(buttonBlock, x + width, y + height, null);
				}
			}
		}
		
		canvas.drawBitmap(block, b.getX()+ width, b.getY()+height, null);
		b.move();

		x = 0;
		y = -50;
	}

}
