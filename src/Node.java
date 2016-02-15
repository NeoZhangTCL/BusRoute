/* ============================================
  * Author: Tuochaolong Zhang (250787957)
  * Used for Assignment 5 of COMPSCI 2210 a
  * This program is a making the Node if a graph with mark
  *  Methods: setMark; getMark largest, getName
= ============================================*/

public class Node {
	
	//CONSTRUCTOR
	private int name;
	private boolean mark;
	
	//FEILD
	public Node(int name){
		this.name = name;
		this.mark = false;
	}
	
	//Mark setter
	public void setMark(boolean mark){
		this.mark = mark;
	}
	
	//Mark getter
	 public boolean getMark(){
		 return this.mark;
	 }
	 
	 //Name getter
	 public int getName(){
		 return this.name;
	 }
	
}
