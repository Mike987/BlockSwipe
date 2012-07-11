package com.brian.blockswipe.BotStuff;

public class DistanceTable {

	int size;
	String[] nodeCol;
	int[] distanceCol;
	String[] pathCol;
	int[] dirCol;

	//Just a distance table, with arrays for columns to keep track of graphsearch data.
	public DistanceTable(Graph graph) {
		size = graph.nodeList.size();
		nodeCol = new String[size]; // list of all nodes
		distanceCol = new int[size]; // Holds all distances to startNode
		pathCol = new String[size]; // Holds the path to get to each
		dirCol = new int[size];										// node by one hop

		for (int i = 0; i < size; i++) {
			distanceCol[i] = -1; // all distances are "not computed"
			pathCol[i] = "Null";
			nodeCol[i] = graph.nodeList.get(i).nodeName;
			dirCol[i] = -1;
		}

	}

	//Get the node index from the distance table by querying its name
	public int getIndex(String name) {
		for (int i = 0; i < size; i++) {
			if (nodeCol[i] == name) {
				return i;
			}
		}
		return 0;
	}
	

}
