package com.brian.blockswipe;

import android.view.MotionEvent;

public class Block {

	int x, dx, y, dy, tempX, tempY, location, count, downx, upx, downy, upy,
			count2, direction, movecounter;
	LevelSelectScreen level = new LevelSelectScreen();
	int lx = LevelSelectScreen.x;
	boolean gameOver;
	private int nextblock;

	public Block() {
		movecounter = 0;
		tempY = -50;
		count = 0;
		for (int n = 0; n < 198; n++) {
			tempX = (n % 11) * 50;
			if (tempX == 0)
				tempY = tempY + 50;
			if (level.levels[lx][n] == 2) {
				x = tempX;
				y = tempY;
				location = n;

			}
		}
	}

	public void move() {

		if (level.levels[lx][location] == 3) {
			dy = 0;
			dx = 0;
			gameOver = true;

		}

		if (dx == 1) {
			nextblock = level.levels[lx][location + 1];
		}
		if (dx == -1) {
			nextblock = level.levels[lx][location - 1];
		}
		if (dy == 1) {
			nextblock = level.levels[lx][location + 11];
		}
		if (dy == -1) {
			nextblock = level.levels[lx][location - 11];
		}

		if (nextblock == 1) {
			dx = 0;
			dy = 0;

		} else if ((LevelSelectScreen.keyPickedUp == false) && (nextblock == 5)) {
			dx = 0;
			dy = 0;

		} else if ((LevelSelectScreen.buttonPressed == false)
				&& (nextblock == 7)) {
			dx = 0;
			dy = 0;

		} else if ((LevelSelectScreen.buttonPressed == true)
				&& (nextblock == 8)) {
			dx = 0;
			dy = 0;

		} else if (nextblock == 4) {
			LevelSelectScreen.keyPickedUp = true;
			moving();
		} else if (nextblock == 6) {

			if ((LevelSelectScreen.buttonPressed == true) && (count2 == 4)) {
				LevelSelectScreen.buttonPressed = false;
			} else if ((LevelSelectScreen.buttonPressed == false)
					&& (count2 == 4)) {
				LevelSelectScreen.buttonPressed = true;
			}
			count2++;
			moving();

		} else {
			moving();

		}

	}

	private void moving() {
		if (dx == 1) {
			x = x + 10;
			count++;
			if (count == 5) {
				location = location + 1;
				count = 0;
				count2 = 0;
			}
		}
		if (dx == -1) {
			x = x - 10;
			count++;
			if (count == 5) {
				location = location - 1;
				count = 0;
				count2 = 0;
			}
		}
		if (dy == 1) {
			y = y + 10;
			count++;
			if (count == 5) {
				location = location + 11;
				count = 0;
				count2 = 0;
			}
		}
		if (dy == -1) {
			y = y - 10;
			count++;
			if (count == 5) {
				location = location - 11;
				count = 0;
				count2 = 0;
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getdx() {
		return dx;
	}

	public int getdy() {
		return dy;
	}

	public void moveUp() {
		this.dy = -1;
		movecounter++;
	}

	public void moveDown() {
		this.dy = 1;
		movecounter++;
	}

	public void moveRight() {
		this.dx = 1;
		movecounter++;
	}

	public void moveLeft() {
		this.dx = -1;
		movecounter++;
	}

	public void OnTouch(MotionEvent event) {
		int eventAction = event.getAction();
		if (dy == 0 && dx == 0) {
			if (eventAction == MotionEvent.ACTION_DOWN) {
				downx = (int) event.getX();
				downy = (int) event.getY();
			}
		}
		if (dy == 0 && dx == 0) {
			if (eventAction == MotionEvent.ACTION_UP) {
				upx = (int) event.getX();
				upy = (int) event.getY();

				if (((Math.abs(upx - downx) > Math.abs(upy - downy)))
						&& upx - downx > 0) {
					dx = 1;
					movecounter++;
				}
				if (((Math.abs(upx - downx) > Math.abs(upy - downy)))
						&& upx - downx < 0) {
					dx = -1;
					movecounter++;
				}

				if (((Math.abs(upx - downx) < Math.abs(upy - downy)))
						&& upy - downy > 0) {
					dy = 1;
					movecounter++;
				}

				if (((Math.abs(upx - downx) < Math.abs(upy - downy)))
						&& upy - downy < 0) {
					dy = -1;
					movecounter++;
				}

			}
		}

	}

}
