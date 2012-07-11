package com.brian.blockswipe.BotStuff;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


import com.brian.blockswipe.BotStuff.Graph.Node;

public class GraphSearch {

	Node startNode;
	Node endNode;
	Node vertex;
	int beans;
	

	public DistanceTable computePath(Graph graph) {
		DistanceTable DT = new DistanceTable(graph);
		Queue<Node> open = new ArrayBlockingQueue<Node>(100);
		
		//Create a distance Table
		
		startNode = graph.nodeList.get(0); 
		endNode = graph.nodeList.get(1);

		DT.distanceCol[DT.getIndex("StartNode")] = 0;// distance from start to start is 0
		open.add(startNode); // and startNode to the list

		while (!open.isEmpty()) { // Keep going while there are nodes to explore
			vertex = open.poll(); // read the queue
			
			// Read the adjacency list inside each node taken from the list
			for(int i = 0; i < vertex.connected.size(); i++) {
				if(DT.distanceCol[DT.getIndex(vertex.connected.get(i))] == -1){ // if the distances is uncomputed
					DT.distanceCol[DT.getIndex(vertex.connected.get(i))] =  // set the distance to current vertex + 1
							DT.distanceCol[DT.getIndex(vertex.nodeName)] + 1;
					
					DT.pathCol[DT.getIndex(vertex.connected.get(i))] = // set the path to the children as vertex.
							vertex.nodeName; 							
					
					DT.dirCol[DT.getIndex(vertex.connected.get(i))] = //set the direction the node was reached from
							vertex.directed.get(i);
					
					open.add(graph.getNode(vertex.connected.get(i))); //add the childern of vertex to the queue
				}
			}
			
		}

		return DT;
	}
}

