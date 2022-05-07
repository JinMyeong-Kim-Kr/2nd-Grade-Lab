// Lab 006	:  Bellman Ford
// Name :
// Student ID :

import java.util.*;


class Graph {
	int numofnodes;  // the number of nodes in the graph
	private int[][] CostAdj; // Adjacency matrix
	private int[] dist; // dist array
	private int[] p; // p array

	final int LargeCost = 999999;

	Graph() { 
		// Graph constructor. 
		numofnodes = 0;
	}


	void Init(int n) { 
		numofnodes = n;
		// now create 2 dimensional array of numofnodes * numofnodes
		CostAdj = new int[numofnodes][numofnodes];
		dist = new int[numofnodes];
		p = new int[numofnodes];

		for(int i = 0; i < numofnodes; i++) {
			// initialize all entries to 0
			for(int j = 0; j < numofnodes; j++)
				CostAdj[i][j] = LargeCost;  // initialize the adjacency matrix 0으로 수정
			CostAdj[i][i] = 0;
		}
	}
	public String toString() { 
		String str;
		int i = 0;
		str = "Dist : ";
		for(i = 0; i < numofnodes; i++)
			str +=  dist[i] + " ";
		str += "\n";
	
		str += "P    : ";
		for( i = 0; i < numofnodes; i++)
			str += p[i] + " ";
		str += "\n";
	
		// show the paths to all the destinations
		for( i = 0; i < numofnodes; i++) {
			str += "Path to " + i + " : ";
			str += OutPath(i);	
			str += "\n";
		}
		return str;
	}



	void Edge(int v1, int v2, int cost) { 
		// NEED TO IMPLEMENT
		CostAdj[v1][v2] = cost;
	}


	String OutPath(int i) { 
		String str = "";

		if(p[i] == -1){
			return i + " ";
			}
		else{
			str = OutPath(p[i]) + i;
			return str + " ";
		}
		
	}

	void BellmanFord(int v) {
		p[v] = -1; //v가 시작점 이므로
		
		for(int k = 0; k < dist.length ;k++){
			dist[k] = LargeCost;
		}
		dist[0] = 0;
		//dist = [0,-,-,-,-]
		for(int t = 0; t < numofnodes; t++) { //Negative Cycle check
		for(int k = 0; k < numofnodes; k++){
			for(int i = 1; i < numofnodes; i++){
				if(dist[i] > dist[k] + CostAdj[k][i]){
					dist[i] = dist[k] + CostAdj[k][i];
					p[i] = k;
				}
				
				

			}
		}

		}
		
			
			

		}
	}





