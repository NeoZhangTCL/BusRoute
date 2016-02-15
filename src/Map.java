import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

/* ============================================
  * Author: Tuochaolong Zhang (250787957)
  * Used for Assignment 5 of COMPSCI 2210 a
* This class represents the map of bus lines. As mentioned above, a graph will be used to store the map and to
find a path from the starting point to the destination.
  *  Methods: getGraph; findPath(Node u); findPath(Edge v); findPath()
= ============================================*/

public class Map{

	//CONSTRUCTOR
	private Graph map;
	private int busChangeLimit;
	private Node start;
	private Node end;

	//FIELD
	Map(String inputFile) throws IOException, GraphException, MapException{

		BufferedReader in;
		int width;
		int height;

		//Read files in by buffered reader, and throw exception if map doesn't exist
		try{
			 in = new BufferedReader (new FileReader(inputFile));
		}catch (FileNotFoundException e){
			throw new MapException("The file does't exist");
		}

		//skip first line and print it
		System.out.println("scale factor:" +in.readLine());
		//read, store and print the width
		width = Integer.parseInt(in.readLine());
		System.out.println("width:"+width);
		//read, store and print the height
		height = Integer.parseInt(in.readLine());
		System.out.println("height:"+height);
		//calculate and print the dimension
		int dimen = width*height;
		System.out.println("dimension:"+dimen);
		//new the Graph and store it
		map = new Graph(dimen);
		//read, store and print the busChangeLimit
		this.busChangeLimit = Integer.parseInt(in.readLine());
		System.out.println("busChangeLimit:"+busChangeLimit);

		//store all the map info into a 2d array for later use
		int[][] info= new int[2*height-1][2*width-1];
		for (int i=0; i<=(2*height-2); i++){
			for (int j=0; j<=(2*width-2);j++){
				char temp = (char)in.read();
				//if char be read is not change line symbol, store it
				if(temp !='\n'){
					info[i][j]=temp;
				}
				//else skip and keep reading
				else{
					temp = (char)in.read();
					info[i][j]=temp;
				}
			}
		}

		//count the index number to make sure the node's position
		int index=0;
		//get all the nodes's edge relationship and insert all the edges
		for (int i=0; i<=(2*height-2); i=i+2){
			for (int j=0; j<=(2*width-2);j=j+2){
				//if '0' is read, set as start point
				if(info[i][j]=='0'){
					this.start = map.getNode(index);
				}
				//if '1' is read, set as end point
				if(info[i][j]=='1'){
					this.end =  map.getNode(index);
				}
				//if right node exist, and right edge exist, insert the edge
				if(j+2<=width && info[i][j+1] != ' '){
					String busLine = (char)info[i][j+1] + "";
					map.insertEdge(map.getNode(index), map.getNode(index+1), busLine);
				}
				//if left node exist, and left edge exist, insert the edge
				if(j-2>=0 && info[i][j-1] != ' '){
					String busLine = (char)info[i][j-1] + "";
					map.insertEdge(map.getNode(index), map.getNode(index-1), busLine);
				}
				//if lower node exist, and lower edge exist, insert the edge
				if(i+2<=height && info[i+1][j] != ' '){
					String busLine = (char)info[i+1][j] + "";
					map.insertEdge(map.getNode(index), map.getNode(index+width), busLine);
				}
				//if upper node exist, and upper edge exist, insert the edge
				if(i-2>=0 && info[i-1][j] != ' '){
					String busLine = (char)info[i-1][j]+"";
					map.insertEdge(map.getNode(index), map.getNode(index-width), busLine);
				}
				//add up index once set one node's relation up
				index++;
			}
		}

		//close the input
		in.close();

		//print graph 2d array to test
		//you can commend it for better performing
		this.map.printGraph();
}

	public Graph getGraph() throws MapException{
		if (this.map ==null){
			throw new MapException("Map doesn't exist");
		}
		else{
			return this.map;
		}
	}

	//CONSTRUCTOR only for findPath();
	private int busLineChanged = 0;
	private Stack<Node> nodePath = new Stack<Node>();
	private Stack<Edge> edgePath = new Stack<Edge>();

	/*Private method to change path from current node to target, return true to make parent recursive know
	 * the right path*/
	private boolean findPath(Node u) throws GraphException{

		//setMark and print for test
		u.setMark(true);
		System.out.println(u.getName() + "marked");

		//push the node to nodePath
		nodePath.push(u);

		//if current node reach the end node, return true
		if(u == end){
			return true;
		}

		//get unmarked edges iterator
		Iterator<Edge> unmarkedEdges = map.unmarkedIEdges(u);
		//if the iterator not empty do loop to search for the path, until all the edges has been searched and failed
		//or get the right path
		if(unmarkedEdges.hasNext()){
			do{
				//for each edge of unmarked edges iterator
				Edge edges = unmarkedEdges.next();
				//recursive to know if the edge lead to right path
				if (findPath(edges) == true){
					return true;
				}
				//if not remove the edge for iterator
				else{
					unmarkedEdges.remove();
				}
			}while(unmarkedEdges.hasNext());
		}

		//if not right path founded, pop this node and return false
		nodePath.pop();
		return false;
	}

	/*Private method to change path from current edge to target, return true to make parent recursive know
	 * the right path.
	 * if the bus change over limit, return false*/
	private boolean findPath(Edge v) throws GraphException{
		//when edgePath is empty, push edge in path
		if(edgePath.empty()){
			edgePath.push(v);
		}
		/*else compare current edge's busLine with path's peek edge's busline, if busLine changed, then
		 * add buslineChanged, if over limit, return false, to make parent recursive know it is the wrong one.*/
		else if(!v.getBusLine().equals(edgePath.peek().getBusLine())){
			busLineChanged++;
			if (busLineChanged > busChangeLimit){
				return false;
			}
		}

		//push the edge to path, avoid double insert the starting edge
		if(!v.hasTarget(start)){
			edgePath.push(v);
		}

		//recursive to make sure if the other side of edge it lead to right ath or not
		if(!findPath(v.nextNode(nodePath.peek()))){
			//if not, pop the edge, and return false to make parent recursive know
			edgePath.pop();
			return false;
		}
		//else return true
		else{
			return true;
		}

	}

	/*Returns a Java Iterator containing the nodes along the path from the starting
	 * point to the destination, if such a path exists. If the path does not exist, this method returns the value
	 * null.*/
	public Iterator<Node> findPath() throws GraphException{
		//do the helper method to get the nodePath stack
		findPath(start);

		//if nodePath is null, return null
		if (nodePath == null){
			return null;
		}
		//else return its nodePath's iterator
		else{
			return nodePath.iterator();
		}
	}


}
