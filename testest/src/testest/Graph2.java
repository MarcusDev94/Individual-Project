package testest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class Graph2  {

	/* variables to contain the rootNode, the rootState, rootConfig,
	 * 4 variables which are used to create copies of a state
	 */
	static Node rootNode;
	static State rootState;
	int[] rootConfig;
	private int[] current1;
	private int[] current2;
	private int[] current3;
	private int[] current4;
	
	/* variables which hold the optimal sequence of nodes from
	 * start state to goal state for a given search, a mapping of state
	 * to depth, a Hashset of visited states (used to stop visiting 
	 * of visited nodes), a q used in bfs, a stack used in dfs and iterative
	 * deepening (idfs) and a priority queue for A* search
	 */
	ArrayList<Node> nodeSequence = new ArrayList<Node>();
	Map<State, Integer> map = new HashMap<State, Integer>();
	HashSet<int[]> set = new HashSet<int[]>();
	Queue<Node> q = new LinkedList<Node>();
	Stack<Node> st = new Stack<Node>();
	PriorityQueue<Node> pq = new PriorityQueue<Node>();

	/* used to store current positions of a, b, c and the agent
	 * when needed (used during a* searches heuristic
	 */
	int aPos;
	int bPos;
	int cPos;
	int agentPos;
	int counter;

	/* constructor for the graph. Creates a root Node to be
	 * used by the search functions
	 */
	public Graph2() {
		rootState = new State(rootConfig);
		rootNode = new Node(rootState,null);
		current1 = new int[rootNode.getData().noOfRows*rootNode.getData().noOfCols];
		current2 = new int[rootNode.getData().noOfRows*rootNode.getData().noOfCols];
		current3 = new int[rootNode.getData().noOfRows*rootNode.getData().noOfCols];
		current4 = new int[rootNode.getData().noOfRows*rootNode.getData().noOfCols];
	}

	/* when a node is given as a parameter the blocks a, b and c
	 * have their current position stored in variables of this class.
	 * Used by the A*'s heuristic to find the f cost of a node
	 */
	void getPos(Node n) {
		for(int i = 0; i<n.getData().state.length; i++){
			if(n.getData().state[i] == n.getData().a) {
				aPos = i;
			}
			else if(n.getData().state[i] == n.getData().b) {
				bPos = i;
			}
			else if(n.getData().state[i] == n.getData().c) {
				cPos = i;
			}
			else if(n.getData().state[i] == n.getData().agent) {
				agentPos = i;
			}
			else{}
		}
	}

	/* checks if a state is contained within a hashSet of states.
	 * This method is used to check if a state of a node has already
	 * been visited
	 */
	public boolean equalsArr(HashSet<int[]> s, int[] a) {
		Iterator<int[]> it = s.iterator();
		while(it.hasNext()) {
			if(Arrays.equals((int[])it.next(), a)) {
				return true;
				
			}
		}
		return false;
	}
	
	/*adds a node to a queue, mapping of Nodes state to a depth,
	 *add a state to a set of visited states and also assign a depth
	 *to a node.
	 */
	void addBfs(Node n,int m) {
		if(!equalsArr(set,n.getData().state)){
			map.put(n.getData(),m);
			set.add(n.getData().state);
			q.add(n);
			n.depth = m;
		}
	}

	/*adds a node to a stack, mapping of Nodes state to a depth,
	 *add a state to a set of visited states and also assign a depth
	 *to a node.
	 */
	void addDfs(Node n,int m) {
		if(!equalsArr(set,n.getData().state)){
			map.put(n.getData(),m);
			set.add(n.getData().state);
			st.add(n);
			n.depth = m;
		}
	}
	
	/*adds a node to a queue, mapping of Nodes state to a depth,
	 *add a state to a set of visited states and also assign a depth
	 *to a node. If a state has already been visited but the state of 
	 *this node is at a depth less than that of the visited stated
	 *we remove the old mapping for that state and give it a new depth
	 *mapping and and add it to the stack.
	 */
	void addIDfs(Node n,int m) {
		n.depth = m;
		if(!equalsArr(set,n.getData().state)){
			map.put(n.getData(),m);
			set.add(n.getData().state);
			st.add(n);
			//n.depth = m;
		}
		if(n.depth < map.get(n.getData())) {
			map.remove(n.getData());
			map.put(n.getData(), m);
			st.add(n);
		}
	}
	
	/*adds a node to a priority queue, mapping of Nodes state to a depth,
	 *add a state to a set of visited states and also assign a depth
	 *to a node.
	 */
	void addAStar(Node n,int m) {
		if(!equalsArr(set,n.getData().state)){
			map.put(n.getData(),m);
			set.add(n.getData().state);
			pq.add(n);
			n.depth = m;
		}
	}
	
	//returns the Manhattan cost between a block and the blocks goal state
	int manhattanNum(int posA, int posB) {
		return (int) (Math.abs(((int) Math.ceil((posA/(double)this.rootNode.getData().noOfCols))+0.01) - ((int) Math.ceil((posB/(double)this.rootNode.getData().noOfCols)+0.01))) + Math.abs( (posA % (double)this.rootNode.getData().noOfCols) - (posB % (double)this.rootNode.getData().noOfCols) ));
	}

	/* used to assign an f cost to a node based on the number of moves taken to reach the current
	 * state as well as the manhattan cost of that state. calls for the node to then be added by
	 * addAStar
	 */
	void heuristic(Node s, int hCost) {
		getPos(s);
		int costOverall =hCost + ((manhattanNum(aPos, bPos - s.getData().noOfCols)) + (manhattanNum(cPos, bPos + s.getData().noOfCols))
				+ (manhattanNum(agentPos, s.getData().agentGoal)));
		s.costOverall = costOverall;
		addAStar(s, hCost);
	}

	//Returns a string sequence of optimal moves made to go from start state to goal state
	void sequence(Node n) {
		while(n.parent != null) {
			nodeSequence.add(n);
			n = n.parent;
		}
		
		int s = 1;
		for(int i = nodeSequence.size()-1; i>=0 ; i--) {
			if(s%30 == 0) {
				System.out.println("");
				s++;
			}
			System.out.print(nodeSequence.get(i).getData().move+ " ");
			s++;
		}
		nodeSequence.clear();
	}

	/*The method used to search a graph in breadth first way
	 * This method returns the depth at which the goal state is found,
	 * the number of nodes it had to process before reaching this state,
	 * the state of the board at goal state and the sequence of optimal
	 * moves made to go from start to goal state.
	 * 
	 * Uncommenting the lines commented out in this method will print out
	 * the board state after each performed move of the search. The time in
	 * ms for this program to run will also be displayed.
	 */
	public boolean bfs (Node root) {
		//long startTime = System.currentTimeMillis();
		counter = 0;
		map.clear();
		set.clear();
		q.clear();
		addBfs(root, 0);
		while(!q.isEmpty()) {
			Node n = (Node)q.remove();
			System.arraycopy(n.getData().state, 0, this.current1, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current2, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current3, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current4, 0,n.getData().state.length);
			
			System.out.println("");
			System.out.print("Board state at level " +n.depth);
			n.getData().displayBoard(n.getData().state);
			System.out.println("");
			
			if(n.getData().goal()) {
				System.out.println("");
				System.out.println("goal config found at level: " +n.depth+ " of the tree for BFS");
				System.out.println("nodes processed: " +counter);
				//long elapsed = System.currentTimeMillis() - startTime;
				//System.out.println("time in ms: " +elapsed);
				n.getData().displayBoard(n.getData().state);
				System.out.println("");
				sequence(n);
				System.out.println("");
				return true;
			}
			counter++;
			State s1 = new State(current1, "U");
			State s2 = new State(current2, "L");
			State s3 = new State(current3, "D");
			State s4 = new State(current4, "R");

			if(s1.state != null) {
				Node child1 = new Node(s1,n);
				addBfs(child1,map.get(n.getData())+1);
			}

			if(s2.state != null) {
				Node child2 = new Node(s2,n);
				addBfs(child2,map.get(n.getData())+1);
			}

			if(s3.state != null) {
				Node child3 = new Node(s3,n);
				addBfs(child3,map.get(n.getData())+1);
			}

			if(s4.state != null) {
				Node child4 = new Node(s4,n);
				addBfs(child4,map.get(n.getData())+1);
			}
		}
		System.out.println("No Solution found");
		return false;
	}

	/*The method used to search a graph in Depth First Way
	 * This method returns the depth at which the goal state is found,
	 * the number of nodes it had to process before reaching this state,
	 * the state of the board at goal state and the sequence of optimal
	 * moves made to go from start to goal state.
	 * 
	 * Uncommenting the lines commented out in this method will print out
	 * the board state after each performed move of the search. The time in
	 * ms for this program to run will also be displayed.
	 */
	public boolean dfs (Node root) {
		//long startTime = System.currentTimeMillis();
		counter = 0;
		map.clear();
		set.clear();
		st.clear();
		addDfs(root, 0);
		while(!st.isEmpty()) {
			Node n = (Node)st.pop();
			System.arraycopy(n.getData().state, 0, this.current1, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current2, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current3, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current4, 0,n.getData().state.length);
			/*
			System.out.println("");
			System.out.print("Board state at level " +n.depth);
			n.getData().displayBoard(n.getData().state);
			System.out.println("");
			*/
			if(n.getData().goal()) {
				System.out.println("");
				System.out.println("goal config found at level " +n.depth+ " of the tree for DFS");
				System.out.println("nodes processed: " +counter);
				//long elapsed = System.currentTimeMillis() - startTime;
				//System.out.println("time in ms: " +elapsed);
				n.getData().displayBoard(n.getData().state);
				System.out.println("");
				sequence(n);
				System.out.println("");
				return true;
			}
			counter++;
			State s1 = new State(current1, "U");
			State s2 = new State(current2, "L");
			State s3 = new State(current3, "D");
			State s4 = new State(current4, "R");

			if(s1.state != null) {
				Node child1 = new Node(s1,n);
				addDfs(child1,map.get(n.getData())+1);
			}

			if(s2.state != null) {
				Node child2 = new Node(s2,n);
				addDfs(child2,map.get(n.getData())+1);
			}

			if(s3.state != null) {
				Node child3 = new Node(s3,n);
				addDfs(child3,map.get(n.getData())+1);
			}

			if(s4.state != null) {
				Node child4 = new Node(s4,n);
				addDfs(child4,map.get(n.getData())+1);
			}
		}
		System.out.println("No Solution found");
		return false;
	}

	/*The method used to search a graph in Depth First Way but with
	 * the depth iteratively increasing
	 * This method returns the depth at which the goal state is found,
	 * the number of nodes it had to process before reaching this state,
	 * the state of the board at goal state and the sequence of optimal
	 * moves made to go from start to goal state.
	 * 
	 * Uncommenting the lines commented out in this method will print out
	 * the board state after each performed move of the search. The time in
	 * ms for this program to run will also be displayed.
	 */
	public boolean idfs (Node root, int l) {
		//long startTime = System.currentTimeMillis();
		counter = 0;
		int depth = l;
		map.clear();
		set.clear();
		st.clear();
		addIDfs(root, 0);
		while(!st.isEmpty()) {
			Node n = (Node)st.pop();
			System.arraycopy(n.getData().state, 0, this.current1, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current2, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current3, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current4, 0,n.getData().state.length);
			/*
			System.out.println("");
			System.out.print("Board state at level " +n.depth);
			n.getData().displayBoard(n.getData().state);
			System.out.println("");
			*/
			if(n.getData().goal()) {
				System.out.println("");
				System.out.println("goal config found at level " +n.depth+ " of the tree for iterative deepening");
				System.out.println("nodes processed: " +counter);
				//long elapsed = System.currentTimeMillis() - startTime;
				//System.out.println("time in ms: " +elapsed);
				n.getData().displayBoard(n.getData().state);
				System.out.println("");
				sequence(n);
				System.out.println("");
				return true;
			}
			counter++;
			if(map.get(n.getData()) != depth) {
				State s1 = new State(current1, "U");
				State s2 = new State(current2, "L");
				State s3 = new State(current3, "D");
				State s4 = new State(current4, "R");

				if(s1.state != null) {
					Node child1 = new Node(s1,n);
					addIDfs(child1,map.get(n.getData())+1);
				}

				if(s2.state != null) {
					Node child2 = new Node(s2,n);
					addIDfs(child2,map.get(n.getData())+1);
				}

				if(s3.state != null) {
					Node child3 = new Node(s3,n);
					addIDfs(child3,map.get(n.getData())+1);
				}

				if(s4.state != null) {
					Node child4 = new Node(s4,n);
					addIDfs(child4,map.get(n.getData())+1);
				}
			}
			if(st.isEmpty()) {
				map.clear();
				set.clear();
				st.clear();
				depth++;
				addDfs(root, 0);
			}
		}
		System.out.println("No Solution found");
		return false;
	}

	/*The method used to search a graph in a heuristic way (A* Search)
	 * This method returns the depth at which the goal state is found,
	 * the number of nodes it had to process before reaching this state,
	 * the state of the board at goal state and the sequence of optimal
	 * moves made to go from start to goal state.
	 * 
	 * Uncommenting the lines commented out in this method will print out
	 * the board state after each performed move of the search. The time in
	 * ms for this program to run will also be displayed.
	 */
	public boolean aStar (Node root) {
		//long startTime = System.currentTimeMillis();
		counter = 0;
		map.clear();
		set.clear();
		pq.clear();
		addAStar(root, 0);
		while(!pq.isEmpty()) {
			Node n = (Node)pq.remove();
			System.arraycopy(n.getData().state, 0, this.current1, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current2, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current3, 0,n.getData().state.length);
			System.arraycopy(n.getData().state, 0, this.current4, 0,n.getData().state.length);
			/*
			System.out.println("");
			System.out.print("Board state at level " +n.depth);
			n.getData().displayBoard(n.getData().state);
			System.out.println("");
			*/
			if(n.getData().goal()) {
				System.out.println("");
				System.out.println("goal config found at level " +n.depth+ " of the tree for A*");
				System.out.println("nodes processed: " +counter);
				//long elapsed = System.currentTimeMillis() - startTime;
				//System.out.println("time in ms: " +elapsed);
				n.getData().displayBoard(n.getData().state);
				System.out.println("");
				sequence(n);
				System.out.println("");
				return true;
			}
			counter++;
			State s1 = new State(current1, "U");
			State s2 = new State(current2, "L");
			State s3 = new State(current3, "D");
			State s4 = new State(current4, "R");

			if(s1.state != null) {
				Node child1 = new Node(s1,n);
				heuristic(child1,map.get(n.getData())+1);
			}

			if(s2.state != null) {
				Node child2 = new Node(s2,n);
				heuristic(child2,map.get(n.getData())+1);
			}

			if(s3.state != null) {
				Node child3 = new Node(s3,n);
				heuristic(child3,map.get(n.getData())+1);
			}

			if(s4.state != null) {
				Node child4 = new Node(s4,n);
				heuristic(child4,map.get(n.getData())+1);
			}
		}
		System.out.println("No Solution found");
		return false;
	}

	
	public static void main(String[] args) {
		Graph2 g = new Graph2();
		g.bfs(rootNode);
		g.dfs(rootNode);
		g.idfs(rootNode, 0);
		g.aStar(rootNode);
		
	}

}