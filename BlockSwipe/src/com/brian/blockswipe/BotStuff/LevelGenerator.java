package com.brian.blockswipe.BotStuff;

import java.util.Random;

public class LevelGenerator {
	// 1324 x 3523

	boolean done = false;
	boolean different = false;
	int[] finalLevel;
	int sizeMin;
	int sizeMax;
	int difMin;
	int difMax;
	int wallDensity; // Modifier for wall number, levels with fewer walls and more
					// moves tend to be better.
	int count = 0;
	Bot bot = new Bot();
	Random ran = new Random();

	/*
	 * Level Generator uses the BFS bot to find acceptable levels Set Easy,
	 * Normal, Hard to define levels created.
	 */
	public int[] generate(int difficulty) {

		if (difficulty == 0) {
			sizeMin = 6;
			sizeMax = 11;
			difMin = 4;
			difMax = 8;
			wallDensity = 100;
		}
		if (difficulty == 1) {
			sizeMin = 4;
			sizeMax = 12;
			difMin = 8;
			difMax = 14;
			wallDensity = 150;
		}
		if (difficulty == 2) {
			sizeMin = 1;
			sizeMax = 17;
			difMin = 17;
			difMax = 250;
			wallDensity = 150;
		}

		while (!done) {
			
			int[][] level = new int[11][18];
			int[] genlevel = new int[199];
			int randIntX;
			int randIntY;

			boolean check;
			
			int randInt;

			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < sizeMin; j++) {
				level[i][j] = 1;
				level[i][17 - j] = 1;
				
				}
			}
			for (int i = 0; i < 18; i++) {
				level[0][i] = 1;
				level[10][i] = 1;
			}
			randIntX = (int) (1 + (Math.random() * (10 - 1)));
			randIntY = (int) (sizeMin + (Math.random() * (sizeMax - sizeMin)));

			level[randIntX][randIntY] = 2;

			
				randIntX = (int) (1 + (Math.random() * (10 - 1)));
				randIntY = (int) (sizeMin + (Math.random() * (sizeMax - sizeMin)));

				
					
					level[0][0] = 3;
					different = true;
				
			
			different = false;
			for (int i = 1; i < 10; i++) {
				for (int j = sizeMin; j < sizeMax; j++) {
					randInt = ran.nextInt(1000);
					if (level[i][j] == 0) {
						if (randInt < wallDensity) {
							level[i][j] = 1;
						}
						if (randInt >= wallDensity) {
							level[i][j] = 0;
						}
					}
				}
			}

			//
			// System.out.print("{");
			// for (int j = 0; j < 18; j++){
			// System.out.println("");
			// for (int i = 0; i < 11; i++)
			// System.out.print(level[i][j] + ",");
			// }
			// System.out.print("};");

			// System.out.println();
			check = bot.checkSolveable(level, difMin, difMax); // set the
			if(check == false){
				count++;// numbers to
			}
			if(check == true){
				
				level[0][0] = 1;
				level[bot.finalX][bot.finalY] = 3;
			}
			// what moves you want
			// it to be between
			if (check == true) {

				System.out.println("Final Level:");
				System.out.print("{");
				for (int j = 0; j < 18; j++) {
					System.out.println("");
					for (int i = 0; i < 11; i++)
						System.out.print(level[i][j] + ",");
				}
				System.out.print("};");

				System.out.println();

				for (int i = 0; i < 11; i++) {
					for (int j = 0; j < 18; j++) {
						genlevel[(j * 11) + i] = level[i][j]; // convert to 1d
					}
				}
				genlevel[198] = bot.minimumMoves;
				finalLevel = genlevel;
				break;
			}

		}
		System.out.println("Tries before Success: " + count);
		return finalLevel;

	}

}
