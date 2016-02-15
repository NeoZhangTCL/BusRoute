/* ============================================
  * Author: Tuochaolong Zhang (250787957)
  * Used for Assignment 5 of COMPSCI 2210 a
  * This program is a making the edges for a graph
  *  Methods: firstEndpoint; secondEndpoint nextNode, getBusLine, hasTarget
= ============================================*/

public class Edge {

	//CONSTRUCROR
	private Node firstNode, sceondNode;
	private String busLine;
	
	//FIELD	
	public Edge(Node first, Node second, String busLine ){
		this.firstNode = first;
		this.sceondNode = second;
		this.busLine = busLine;
	}
	
	//First node getter
	public Node firstEndpoint(){
		return this.firstNode;
	}
	
	//Second node getter
	public Node secondEndpoint(){
		return this.sceondNode;
	}
	
	//package level method
	//get the other node in same edge different than the input one
	Node nextNode(Node u){
		if(this.firstNode != u){
			return this.firstNode;
		}
		else{
			return this.sceondNode;
		}
	}
	
	//busLine getter
	public String getBusLine(){
		return this.busLine;
	}
	
	//package level method
	//check if the edge has target node or not
	boolean hasTarget(Node u){
		if (this.firstNode != u && this.sceondNode != u){
			return false;
		}
		else
			return true;
	}
}
