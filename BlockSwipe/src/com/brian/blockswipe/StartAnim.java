package com.brian.blockswipe;

import java.util.Random;

public class StartAnim {

	int x, dx, y, dy, tempX, tempY, location, count, downx, upx, downy, upy,
			count2, direction, move, ranInt;
	private int nextblock;
	Random ran;
	

	
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

	public StartAnim() {
		move = 0;
		ran = new Random();
		ranInt = ran.nextInt(4);
		if (ranInt == 0){
			dx = 1;	
		}
		else if (ranInt == 1){
			dx = -1;	
		}
		else if (ranInt == 2){
			dy = 1;	
		}
		else {
			dy = -1;	
		}
		
		tempY = -50;
		count = 0;
		for (int n = 0; n < 198; n++) {
			tempX = (n % 11) * 50;
			if (tempX == 0)
				tempY = tempY + 50;
			if (startlvl[n] == 2) {
				x = tempX;
				y = tempY;
				location = n;

			}
		}
	}
	
	public void move() {

		if (startlvl[location] == 3) {
			dy = 0;
			dx = 0;

		}

		if (dx == 1) {
			nextblock = startlvl[location + 1];
		}
		if (dx == -1) {
			nextblock = startlvl[location - 1];
		}
		if (dy == 1) {
			nextblock = startlvl[location + 11];
		}
		if (dy == -1) {
			nextblock = startlvl[location - 11];
		}

		if (nextblock == 1) {
			dy = 0;
			dx = 0;
			ranInt = ran.nextInt(4);
			if (ranInt == 0){
				dx = 1;	
			}
			else if (ranInt == 1){
				dx = -1;	
			}
			else if (ranInt == 2){
				dy = 1;	
			}
			else {
				dy = -1;	
			}	

		} else if ((LevelSelectScreen.keyPickedUp == false) && (nextblock == 5)) {
			dy = 0;
			dx = 0;
			ranInt = ran.nextInt(4);
			if (ranInt == 0){
				dx = 1;	
			}
			else if (ranInt == 1){
				dx = -1;	
			}
			else if (ranInt == 2){
				dy = 1;	
			}
			else {
				dy = -1;	
			}	

		} else if ((LevelSelectScreen.buttonPressed == false) && (nextblock == 7)) {
			dy = 0;
			dx = 0;
			ranInt = ran.nextInt(4);
			if (ranInt == 0){
				dx = 1;	
			}
			else if (ranInt == 1){
				dx = -1;	
			}
			else if (ranInt == 2){
				dy = 1;	
			}
			else {
				dy = -1;	
			}	

		} else if ((LevelSelectScreen.buttonPressed == true) && (nextblock == 8)) {
			dy = 0;
			dx = 0;
			ranInt = ran.nextInt(4);
			if (ranInt == 0){
				dx = 1;	
			}
			else if (ranInt == 1){
				dx = -1;	
			}
			else if (ranInt == 2){
				dy = 1;	
			}
			else if (ranInt == 3) {
				dy = -1;	
			}	

		} else if (nextblock == 4) {
			LevelSelectScreen.keyPickedUp = true;
			moving();
		} else if (nextblock == 6) {

			if ((LevelSelectScreen.buttonPressed == true) && (count2 == 9)) {
				LevelSelectScreen.buttonPressed = false;
			} else if ((LevelSelectScreen.buttonPressed == false) && (count2 == 9)) {
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
			x = x + 5;
			count++;
			if (count == 10) {
				location = location + 1;
				count = 0;
				count2 = 0;
			}
		}
		if (dx == -1) {
			x = x - 5;
			count++;
			if (count == 10) {
				location = location - 1;
				count = 0;
				count2 = 0;
			}
		}
		if (dy == 1) {
			y = y + 5;
			count++;
			if (count == 10) {
				location = location + 11;
				count = 0;
				count2 = 0;
			}
		}
		if (dy == -1) {
			y = y - 5;
			count++;
			if (count == 10) {
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
}
