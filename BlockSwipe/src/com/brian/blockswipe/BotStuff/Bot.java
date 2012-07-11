/* Notes on optimizing:
 * Early node bailout: If total number of nodes is less than difMin then
 * scrap and start over. ie graph.nodelist.size() < difMin; break;
 * 
 * 
 * calculate all nodes in the map and set the endNode to the farthest node
 * from the startNode
 * 
 * 
 */





package com.brian.blockswipe.BotStuff;

import com.brian.blockswipe.BotStuff.Graph.Node;



public class Bot {

	static int[][] level2d= new int[11][18];
	static int[][] validLocations= new int[11][18];
	
	int[] solution = new int[100]; //Needs to be more flexible
	boolean solvable;
	int MOVE_UP = 1;
	int MOVE_RIGHT = 2;
	int MOVE_DOWN = 3;
	int MOVE_LEFT = 4;

	int startx;
	int starty;
	int endx;
	int endy;

	int currentX;
	int currentY;
	boolean firstNode;
	int count = 0;
	public int minimumMoves;
	
	Graph graph;
	GraphSearch GS;
	DistanceTable DT;
	
	boolean levelUnacceptable = false;
	
	int finalX;
	int finalY;


	public Bot(){
		
		level2d = new int[11][18];
		
		startx = -1;
		starty = -1;
		endx= -1;
		endy = -1;
		solution = new int[100];
		MOVE_UP = 1;
		MOVE_RIGHT = 2;
		MOVE_DOWN = 3;
		MOVE_LEFT = 4;

		
	}
	

	public int[] solve(int[] level, int location ) {
		
		graph = new Graph();
		GS = new GraphSearch();
		
		for(int i = 0; i < level.length; i++){
			if(level[i] == 2){
				level[i] = 0;
			}
		}
		level[location] = 2;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 18; j++) {
				level2d[i][j] = level[(j * 11) + i];
				if (level2d[i][j] == 2) {
					startx = i;
					starty = j;
				}
				if (level2d[i][j] == 3) {
					endx = i;
					endy = j;
				}
				validLocations[i][j] = level2d[i][j];
				
			}
		}
		
		validLocations[startx][starty] = 2;
		currentX = startx;
		currentY = starty;
		graph.addNode("StartNode", startx, starty);
		if(endx != -1 && endy != -1){
		graph.addNode("EndNode", endx, endy);
		}
		count++;

		iterateLocations();
//		for (int i = 0; i < 18; i++) {
//			for (int j = 0; j < 11; j++) {
//				System.out.print(validLocations[j][i] + " ");
//			}
//			System.out.println("");
//		}

		connectNodes();

		DT = GS.computePath(graph);
		
		

		solution = readDirections(DT);

		// Finish up and reset variables etc
		
		count = 0;
		startx = -1;
		starty = -1;
		endx = -1;
		endy = -1;
		GS = null;
		graph = null;
		DT = null;
		validLocations = new int[11][18];
		level2d = new int[11][18];
		
		
		return solution;

	}
	//Boolean version of solve, just returns if a puzzle can be solved or not
public boolean checkSolveable(int[][] level, int puzzleSizeMin, int puzzleSizeMax) {
		
		graph = new Graph();
		GS = new GraphSearch();
		level2d = level;

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 18; j++) {
				
				if (level2d[i][j] == 2) {
					startx = i;
					starty = j;
				}
				if (level2d[i][j] == 3) {
					endx = i;
					endy = j;
				}
				validLocations[i][j] = level2d[i][j];
				
			}
		}
		
		validLocations[startx][starty] = 2;
		currentX = startx;
		currentY = starty;
		graph.addNode("StartNode", startx, starty);
		graph.addNode("EndNode", endx, endy);
		count++;

		iterateSolvable(puzzleSizeMin);
		
			if(!levelUnacceptable){
				return false;
			}

		connectNodes();

		DT = GS.computePath(graph);

		solvable = checkDirections(DT,puzzleSizeMin, puzzleSizeMax);

		// Finish up and reset variables etc
		
		count = 0;
		startx = -1;
		starty = -1;
		endx = -1;
		endy = -1;
		GS = null;
		graph = null;
		DT = null;
		validLocations = new int[11][18];
		level2d = new int[11][18];
		
		return solvable;
		
		

	}

	private int[] readDirections(DistanceTable DT) { // Create an array of directions for runBot() to read off and move
		int index;
		int size = DT.nodeCol.length;
		int[] direction = new int[100];
		String node;
		index = DT.getIndex("EndNode");
		
		for(int i = 0; i < size; i++ ){
			direction[i] = DT.dirCol[index];
			node = DT.pathCol[index];
			index = DT.getIndex(node);
		}
		
		return direction;
	}
	
	private boolean checkDirections(DistanceTable DT, int puzzleSizeMin, int puzzleSizeMax) { // Create an array of directions for runBot() to read off and move
		
		int countCheck = -1;
		int direction = -1;
		int max = -1;
		int index = -1;
		int size = DT.nodeCol.length;
		String node;
		String prevNode;
		Node EndNode;
		Node PreviousNode;
		int currentX;
		int currentY;
		
		int[][] testLocations = new int[11][18];
		

		
		
		for(int i = 0; i < size; i++){
			if(max < DT.distanceCol[i]){
			max = DT.distanceCol[i];
			direction = DT.dirCol[i];
			index = i;
			}
		}
		
		
		if(max < puzzleSizeMin){
		return false;
		}
		if(max >= puzzleSizeMin){

			node = DT.nodeCol[index];
			prevNode = DT.pathCol[index];
			
			EndNode = graph.getNode(node);
			PreviousNode = graph.getNode(prevNode);
			
			currentX = EndNode.x;
			currentY = EndNode.y;
			//testLocations = validLocations;
			validLocations[EndNode.x][EndNode.y] = 7;
			validLocations[PreviousNode.x][PreviousNode.y] = 6;
//			for (int i = 0; i < 18; i++) {
//			for (int j = 0; j < 11; j++) {
//				System.out.print(validLocations[j][i] + " ");
//			}
//			System.out.println("");
//			}
			System.out.println("X: " + currentX + " " + PreviousNode.x + " Y: " + currentY + " " + PreviousNode.y);
			System.out.println("LocCount " + countLocation(currentX, currentY));
			countCheck = countLocation(currentX, currentY);
			if(countCheck == 0){
				finalX = currentX;
				finalY = currentY;
				System.out.println("COORDS: " + finalX + ", " + finalY);
				minimumMoves = max;
				return true;
			}
			
			if(DT.dirCol[index] == MOVE_UP){
				while(currentY != PreviousNode.y){
					countCheck = countLocation(currentX, currentY);
					System.out.println("LocCount " + countLocation(currentX, currentY));
				
					if(countCheck > 0){
						currentY = currentY + 1;
						testLocations[currentX][currentY] = -1;
					}
					if(countCheck == 0){
						
						finalX = currentX;
						finalY = currentY;
						System.out.println("COORDS: " + finalX + ", " + finalY);
						minimumMoves = max;
						for (int i = 0; i < 18; i++) {
							for (int j = 0; j < 11; j++) {
								System.out.print(validLocations[j][i] + " ");
							}
							System.out.println("");
							}
							System.out.println("X: " + currentX + " " + PreviousNode.x + " Y: " + currentY + " " + PreviousNode.y);
						return true;
					}
				}
			}
			if(DT.dirCol[index] == MOVE_RIGHT){
				while(currentX != PreviousNode.x){
					countCheck = countLocation(currentX, currentY);
					System.out.println("LocCount " + countLocation(currentX, currentY));
				
					if(countCheck > 0){
						currentX = currentX - 1;
						testLocations[currentX][currentY] = -1;
					}
					if(countCheck == 0){
						finalX = currentX;
						finalY = currentY;
						System.out.println("COORDS: " + finalX + ", " + finalY);
						minimumMoves = max;
						for (int i = 0; i < 18; i++) {
							for (int j = 0; j < 11; j++) {
								System.out.print(validLocations[j][i] + " ");
							}
							System.out.println("");
							}
							System.out.println("X: " + currentX + " " + PreviousNode.x + " Y: " + currentY + " " + PreviousNode.y);
						return true;
					}
				}
			}
			if(DT.dirCol[index] == MOVE_DOWN){
				while(currentY != PreviousNode.y){
					countCheck = countLocation(currentX, currentY);
					System.out.println("LocCount " + countLocation(currentX, currentY));
				
					if(countCheck > 0){
						currentY = currentY - 1;
						testLocations[currentX][currentY] = -1;
					}
					if(countCheck == 0){
						finalX = currentX;
						finalY = currentY;
						System.out.println("COORDS: " + finalX + ", " + finalY);
						minimumMoves = max;
						for (int i = 0; i < 18; i++) {
							for (int j = 0; j < 11; j++) {
								System.out.print(validLocations[j][i] + " ");
							}
							System.out.println("");
							}
							System.out.println("X: " + currentX + " " + PreviousNode.x + " Y: " + currentY + " " + PreviousNode.y);
						return true;
					}
				}
			}
			if(DT.dirCol[index] == MOVE_LEFT){
				while(currentX != PreviousNode.x){
					countCheck = countLocation(currentX, currentY);
					System.out.println("LocCount " + countLocation(currentX, currentY));
				
					if(countCheck > 0){
						currentX = currentX + 1;
						testLocations[currentX][currentY] = -1;
					}
					if(countCheck == 0){
						finalX = currentX;
						finalY = currentY;
						System.out.println("COORDS: " + finalX + ", " + finalY);
						minimumMoves = max;
						for (int i = 0; i < 18; i++) {
							for (int j = 0; j < 11; j++) {
								System.out.print(validLocations[j][i] + " ");
							}
							System.out.println("");
							}
							System.out.println("X: " + currentX + " " + PreviousNode.x + " Y: " + currentY + " " + PreviousNode.y);
						return true;
					}
				}
			}
				
			 
			
//			System.out.println("Node: " + EndNode.nodeName);
//		
//			System.out.println("MAX PATH: " + max);
//			System.out.println("X: " + finalX + " Y: " + finalY);
			
		}
		return false;
		
		
	}
	
	private void iterateSolvable(int puzzleSizeMin) {
		for (int c = 0; c < 200; c++) { // constants like this 50 need changing for something a bit more flexible, will do later

			firstNode = true;
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 18; j++) {
					if (validLocations[i][j] == 8 && firstNode) { //This sets a possible block Location as "explored" and sets a new valid Location to check from

						currentX = i;
						currentY = j;
						validLocations[i][j] = 9;
						
						firstNode = false;
						graph.addNode("N" + count, i, j); // Add a graph node for each possible location
						count++;


					}

				}

			}

			checkLocation();

		}
		if(graph.nodeList.size() < puzzleSizeMin + 5 ){
			levelUnacceptable = true;
		}

	}
	private void iterateLocations() {
		for (int c = 0; c < 200; c++) { // constants like this 50 need changing for something a bit more flexible, will do later

			firstNode = true;
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 18; j++) {
					if (validLocations[i][j] == 8 && firstNode) { //This sets a possible block Location as "explored" and sets a new valid Location to check from

						currentX = i;
						currentY = j;
						validLocations[i][j] = 9;
						
						firstNode = false;
						graph.addNode("N" + count, i, j); // Add a graph node for each possible location
						count++;


					}

				}

			}

			checkLocation();

		}

	}

	public void checkLocation() { // Just checks in all directions around the current position
		// check up
		for (int y = currentY; y >= 0; y--) {
			if (level2d[currentX][y] == 1) {
				if (validLocations[currentX][y + 1] == 0) {
					validLocations[currentX][y + 1] = 8;

				}
				break;
			}

		}
		// check right
		for (int x = currentX; x < 11; x++) {
			if (level2d[x][currentY] == 1) {
				if (validLocations[x - 1][currentY] == 0) {
					validLocations[x - 1][currentY] = 8;

				}
				break;
			}
		}
		// check down
		for (int y = currentY; y < 18; y++) {
			if (level2d[currentX][y] == 1) {
				if (validLocations[currentX][y - 1] == 0) {
					validLocations[currentX][y - 1] = 8;

				}
				break;
			}
		}
		// check left
		for (int x = currentX; x >= 0; x--) {
			if (level2d[x][currentY] == 1) {
				if (validLocations[x + 1][currentY] == 0) {
					validLocations[x + 1][currentY] = 8;

				}
				break;
			}

		}

	}
	public int countLocation(int currentX, int currentY) {
		int LocCount = 0;// Just checks in all directions around the current position
		// check up
		for (int y = currentY; y >= 0; y--) {
			if (validLocations[currentX][y] == 1 || validLocations[currentX][y] == 6) {
				break;
			}
			if (validLocations[currentX][y] == 9) {
					LocCount++;
				break;
			}

		}
		// check right
		for (int x = currentX; x < 11; x++) {
			if (validLocations[x][currentY] == 1 || validLocations[x][currentY] == 6) {
				break;
			}
			if (validLocations[x][currentY] == 9 ) {
				LocCount++;
				break;
			}
		}
		// check down
		for (int y = currentY; y < 18; y++) {
			if (validLocations[currentX][y] == 1 || validLocations[currentX][y] == 6) {
				break;
			}
			if (validLocations[currentX][y] == 9) {
				LocCount++;
				break;
			}
		}
		// check left
		for (int x = currentX; x >= 0; x--) {
			if (validLocations[x][currentY] == 1 || validLocations[x][currentY] == 6) {
				break;
			}
			if (validLocations[x][currentY] == 9) {
				LocCount++;
				break;
			}

		}
		
		return LocCount;

	}

	// good god almighty please tidy this up.
	
	//This connects up nodes to create the directed graph. 
	public void connectNodes() {
		int count = 0;
		for (int j = 0; j < graph.nodeList.size(); j++) {
			// check up
			for (int y = graph.nodeList.get(count).y; y >= 0; y--) {
				if (validLocations[graph.nodeList.get(count).x][y] == 9|| validLocations[graph.nodeList.get(count).x][y] == 2) {
					if (validLocations[graph.nodeList.get(count).x][y - 1] == 1) {

						for (int i = 0; i < graph.nodeList.size(); i++) {
							if (graph.nodeList.get(i).y == y
									&& graph.nodeList.get(i).x == graph.nodeList
											.get(count).x) {
								graph.connectNode(
										graph.nodeList.get(count).nodeName,
										graph.nodeList.get(i).nodeName, MOVE_UP);
								
							}
						}
						break;
					}
				}
				if (validLocations[graph.nodeList.get(count).x][y] == 3) {
					for (int i = 0; i < graph.nodeList.size(); i++) {
						if (graph.nodeList.get(i).y == y
								&& graph.nodeList.get(i).x == graph.nodeList
										.get(count).x) {
							graph.connectNode(
									graph.nodeList.get(count).nodeName,
									graph.nodeList.get(i).nodeName, MOVE_UP);
							
						}

					}
					break;
				}

			}

			// check right
			for (int x = graph.nodeList.get(count).x; x < 11; x++) {
				if (validLocations[x][graph.nodeList.get(count).y] == 9|| validLocations[x][graph.nodeList.get(count).y] == 2) {
					if (validLocations[x + 1][graph.nodeList.get(count).y] == 1) {

						for (int i = 0; i < graph.nodeList.size(); i++) {
							if (graph.nodeList.get(i).y == graph.nodeList
									.get(count).y
									&& graph.nodeList.get(i).x == x) {
								graph.connectNode(
										graph.nodeList.get(count).nodeName,
										graph.nodeList.get(i).nodeName, MOVE_RIGHT);
								
							}
						}
						break;
					}
				}

				if (validLocations[x][graph.nodeList.get(count).y] == 3) {
					for (int i = 0; i < graph.nodeList.size(); i++) {
						if (graph.nodeList.get(i).y == graph.nodeList
								.get(count).y && graph.nodeList.get(i).x == x) {
							graph.connectNode(
									graph.nodeList.get(count).nodeName,
									graph.nodeList.get(i).nodeName, MOVE_RIGHT);
							
						}
					}
					break;
				}

			}

			// check down
			for (int y = graph.nodeList.get(count).y; y < 18; y++) {
				if (validLocations[graph.nodeList.get(count).x][y] == 9 || validLocations[graph.nodeList.get(count).x][y] == 2) {
					if (validLocations[graph.nodeList.get(count).x][y + 1] == 1) {

						for (int i = 0; i < graph.nodeList.size(); i++) {
							if (graph.nodeList.get(i).y == y
									&& graph.nodeList.get(i).x == graph.nodeList
											.get(count).x) {
								graph.connectNode(
										graph.nodeList.get(count).nodeName,
										graph.nodeList.get(i).nodeName, MOVE_DOWN);
								
							}
						}
						break;
					}

				}
				if (validLocations[graph.nodeList.get(count).x][y] == 3) {
					for (int i = 0; i < graph.nodeList.size(); i++) {
						if (graph.nodeList.get(i).y == y
								&& graph.nodeList.get(i).x == graph.nodeList
										.get(count).x) {
							graph.connectNode(
									graph.nodeList.get(count).nodeName,
									graph.nodeList.get(i).nodeName, MOVE_DOWN);
							
						}
					}
					break;
				}

			}

			// check left
			for (int x = graph.nodeList.get(count).x; x >= 0; x--) {
				if (validLocations[x][graph.nodeList.get(count).y] == 9 || validLocations[x][graph.nodeList.get(count).y] == 2) {
					if (validLocations[x - 1][graph.nodeList.get(count).y] == 1) {

						for (int i = 0; i < graph.nodeList.size(); i++) {
							if (graph.nodeList.get(i).y == graph.nodeList
									.get(count).y
									&& graph.nodeList.get(i).x == x) {
								graph.connectNode(
										graph.nodeList.get(count).nodeName,
										graph.nodeList.get(i).nodeName, MOVE_LEFT);
								
							}
						}
						break;
					}
				}

				if (validLocations[x][graph.nodeList.get(count).y] == 3) {
					for (int i = 0; i < graph.nodeList.size(); i++) {
						if (graph.nodeList.get(i).y == graph.nodeList
								.get(count).y && graph.nodeList.get(i).x == x) {
							graph.connectNode(
									graph.nodeList.get(count).nodeName,
									graph.nodeList.get(i).nodeName, MOVE_LEFT);
							
						}
					}
					break;
				}

			}

			count++;
		}
	}

}



