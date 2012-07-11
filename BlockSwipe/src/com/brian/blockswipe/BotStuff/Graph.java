package com.brian.blockswipe.BotStuff;

import java.util.ArrayList;



public class Graph {

	

	ArrayList<Node> nodeList = new ArrayList<Node>(); // Holds all nodes in the graph

	// Everything is set up so that just nodes names can be used.  Less efficient probably but easier.
	static class Node {

		String nodeName;
		int x, y;
		ArrayList<String> connected = new ArrayList<String>();
		ArrayList<Integer> directed = new ArrayList<Integer>();

	}

	//Create a new node/vertex at given x,y
	public void addNode(String name, int x, int y) {
		Node node = new Node();
		node.x = x;
		node.y = y;
		node.nodeName = name;
		nodeList.add(node);
		
	}
	// Connect two nodes in a directed fashion from name1 -----> name2
	public void connectNode(String name1, String name2, int direction) {
		if(name1 != name2){
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).nodeName == name1) {
				nodeList.get(i).connected.add(name2);
				nodeList.get(i).directed.add(direction);
				
			}
		}
		}
	}
	// Get the nodes location in the graph by querying its name
	public Node getNode(String name){
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).nodeName == name) {
				return nodeList.get(i);
			}
		}
		return null;
	}


}
