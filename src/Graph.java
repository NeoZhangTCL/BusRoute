import java.util.ArrayList;
import java.util.Iterator;

/* ============================================
 * Author: Tuochaolong Zhang (250787957)
 * Used for Assignment 5 of COMPSCI 2210 a
 * This program is a making a graph by nodes and edges with  Adjacency Matrix Method
 *  Methods: insertEdge; getNode(int name), incidentEdges(Node u), getEdge(Node u, Node v),
 *  ... areAdjacent(Node u, Node v), unmarkedIEdges, printGraph
= ============================================*/

public class Graph implements GraphADT{
	
	//CONSTRUCTOR
	private int size;
	private Node[] nodes;
	private Edge[][] edges;
	
	//FIELD
	public Graph(int n){
		//Initialize size, node array, and edge 2d array
		this.size = n;
		this.nodes = new Node[this.size];
		this.edges = new Edge[this.size][this.size];
		
		//make n new nodes, and give name from 0 to n-1, 
		for(int j=0; j<this.size; j++){
			this.nodes[j] = new Node(j);
		}
		
		//make a 2d array to store edges, initialize them to null
		for(int i = 0; i<this.size; i++){
			for( int j = 0; j<this.size; j++){
				this.edges[i][j] = null;
			}
		}
		
	}
		
	/* Adds to the graph an edge connecting the given vertices. The type of the edge is as indicated. Throws a GraphException 
	 * if either node does not exist or if the edge is already in the graph.*/
	public void insertEdge(Node nodeu, Node nodev, String busLine) throws GraphException{
		//check if node u don't exist, throw exception
		if (nodeu.getName() >= this.size || nodeu.getName() < 0){
			throw new GraphException("Node "+  nodeu +"deoesn't exist");
		}
		//check if node v don't exist, throw exception
		else if (nodev.getName() >= this.size || nodev.getName() < 0){
			throw new GraphException( "Node " + nodev +"deoesn't exist");
		}
		//if both nodes exist, insert edge into two symmetric position
		 else{
			  Edge newEdge = new Edge(nodeu,nodev,busLine);
			  this.edges[nodeu.getName()][nodev.getName()]=newEdge;
			  this.edges[nodev.getName()][nodeu.getName()]=newEdge;
		  } 
	}

	/* Returns the node with the specified name. Throws a GraphException if the node does not exist. */
	public Node getNode(int name) throws GraphException{
		//if node's name out of range, throw an exception
		if (name >= this.size || name < 0){
			throw new GraphException("This node doesn't ");
		}
		//else return the target node
		else{
			return this.nodes[name];
		}
	}

	/* Returns a Java Iterator storing all the edges incident on the specified node. It returns null if the node does
	not have any edges incident on it. Throws a GraphException if the node does not exist. */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException{
		//if node doesn't exist, throw an exception
		if (u.getName() >= this.size || u.getName() < 0){
			throw new GraphException( "Node "+ u +"deoesn't exist");
		}
		//if node exist, try to get all incident edges
		else{
			ArrayList<Edge> l = new ArrayList<Edge>();
			//from all the line or row of same node in 2d edge array, get all the incident edges
			for (int n=0; n<this.size; n++){
				Edge inciEdges = this.edges[u.getName()][n];
				//if info in 2d array is not empty, put it in linkedList
				if (inciEdges != null){
					l.add(inciEdges);
				}
			}
			//return iterator
			return l.iterator();
		}
	}

	/* Returns the edge connecting the given vertices. Throws a GraphException if there is no edge conencting the given vertices or if u or v do not exist. */
	public Edge getEdge(Node u, Node v) throws GraphException{
		//check if node u or v don't exist, throw exception
		if (u.getName() >= this.size || u.getName() < 0 || v.getName() >= this.size || v.getName() < 0){
			throw new GraphException("This edge doesn't exist.");
		}
		//else return the edge
		else{	
			Edge find = this.edges[u.getName()][v.getName()];
			return find;
		}
	}

	/* Returns true is u and v are adjacent, and false otherwise. It throws a GraphException if either vertex does not exist. */
	public boolean areAdjacent(Node u, Node v) throws GraphException{
		//check if node u don't exist, throw exception
		if (u.getName() >= this.size || u.getName() < 0){
			throw new GraphException("Node "+  u +"deoesn't exist");
		}
		//check if node v don't exist, throw exception
		else if (v.getName() >= this.size || v.getName() < 0){
			throw new GraphException( "Node " + v +"deoesn't exist");
		}
		//else find the edge
		else{
			Edge find = (Edge)this.edges[u.getName()][v.getName()];
			if (find == null){
				//if cannot find return false
				return false;
			}
			//else return true
			else{
				return true;
			}
		}
	}

	//Package Level
	/*Returns a Java Iterator storing all the edges incident on the specified node and the next node is unmarked. 
	 * It returns null if the node does not have any edges incident on it. Throws a GraphException if the node does not exist. */
	Iterator<Edge> unmarkedIEdges(Node u) throws GraphException{
		//check if u exist
		if (u.getName() >= this.size || u.getName() < 0){
			throw new GraphException( "Node "+ u +"deoesn't exist");
		}
		else{
			//from all the line or row of same node in 2d edge array, get all the incident edges
			ArrayList<Edge> l = new ArrayList<Edge>();
			for (int n=0; n<this.size; n++){
				Edge inciEdges = this.edges[u.getName()][n];
				//if the edges are not null or the next node is not marked, add it to list
				if (inciEdges != null && getNode(n).getMark() == false){
					l.add(inciEdges);
				}
			}
			return l.iterator();
		}
	}

	//Package level
	//Print the Edge 2d arrage to make sure the map has been read correctly
	//Only for test
	void printGraph(){
		System.out.println("inside");
		for(int i = 0; i<this.size; i++){
			for( int j = 0; j<this.size; j++){
				if(this.edges[i][j]!=null){
					System.out.print(/*this.edges[i][j].firstEndpoint().getName() + ""+ this.edges[i][j].secondEndpoint().getName()+*/this.edges[i][j].getBusLine());
				}else{
					System.out.print(" ");
				}		
			}
			System.out.println("");
		}
	}
}
