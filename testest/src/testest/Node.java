package testest;

public class Node implements Comparable<Node> {
	
	/* variables to store the Board state of the node,
	 * the depth of this node in the graph,
	 * the parent node of this node and the 'f cost'
	 * of this node (Used in the A* heuristic search)
	 */
	State data;
	int depth;
	Node parent;
	int costOverall;
	
	/* the constructor which assigns a state to this node
	 * as well as a parent node
	 */
	public Node(State s, Node p) {
		this.data = s;
		this.parent = p;
	}
	
	/* when called returns the state of
	 * this node
	 */
	public State getData(){		
		return this.data;	
		}
	
	//when called returns the 'f cost' of this node
	public int getOverallCost() {
		return costOverall;
	}

	/* This class implements comparable to allow for it to be compared
	 * in a priority queue (used for a* search). As it does this a compareTo
	 * method needed to be created to make sure this object
	 * is being compared in the way I wanted it to be. Compares this
	 * object against an object of the same class on it 'f cost'
	 */
	@Override
	public int compareTo(Node one) {
		int costOverall = ((Node) one).getOverallCost();
		return this.costOverall - costOverall;
	}


}

