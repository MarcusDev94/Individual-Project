package testest;

import java.util.Arrays;

public class State {

	/* below are variables for the blocks a, b, c as well
	 * as for the number of rows and columns. Changing the 
	 * in number of rows and columns changes the board size
	 */
	int[] state;
	int[] goal;
	int agent = 1;
	int a = -1;
	int b = -2;
	int c = -3;
	int noOfRows = 4;
	int noOfCols = 4;
	String move;
	
	// variables to store the goal state of blocks a, b, and c
	int aGoal;
	int bGoal;
	int cGoal;
	int agentGoal;
	
	/* override of the hashcode and equals methods of this
	 * code to make it comparable in the way I wanted so an
	 * int[] array could be used as a key for a mapping in a map
	 * in the Graph2 class which contains a mapping from states
	 * to their depth within the graph
	 */
	@Override
	public int hashCode() {
		//final int prime = 31;
		//int result = 1;
		//result = prime * result + Arrays.hashCode(state);
		//return result;
		return Arrays.hashCode(state);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}

	/*constructor for a state Object which assigns the move taken
	 * to reach the new state as well as creating this new state
	 * based on the move given in the parameters of this object
	 */
	
	public State (int[] p, String move) {
		//parent = new int[noOfRows*noOfCols];
		//System.arraycopy(p, 0, parent, 0, p.length);
		
		if(move == "U") {
			this.up(p);
			this.move = move;
		}
		else if(move == "D") {
			this.down(p);
			this.move = move;
		}
		else if(move == "L") {
			this.left(p);
			this.move = move;
		}
		else {
			this.right(p);
			this.move = move;
		}	

		this.goal = new int[noOfRows*noOfCols];
		this.goal[(noOfRows * (noOfCols -3))+1] = a;
		aGoal = (noOfRows * (noOfCols -3))+1;
		this.goal[(noOfRows * (noOfCols -2))+1] = b;
		bGoal = (noOfRows * (noOfCols -2))+1;
		this.goal[(noOfRows * (noOfCols -1))+1] = c;
		cGoal = (noOfRows * (noOfCols -1))+1;
		this.goal[noOfRows*noOfCols -1] = agent;
		agentGoal = noOfRows*noOfCols -1;
	}

	//the constructor used for the root node. No move is stated as their is no move made
	public State(int[] p) {
		p = new int[noOfRows*noOfCols];
		state = new int[noOfRows*noOfCols];
		p[noOfRows * (noOfCols -1)] = a;
		p[(noOfRows * (noOfCols -1))+1] = b;
		p[(noOfRows * (noOfCols -1))+2] = c;
		p[noOfRows*noOfCols -1] = agent;
		System.arraycopy(p, 0, state, 0, p.length);

		this.goal = new int[noOfRows*noOfCols];
		this.goal[(noOfRows * (noOfCols -3))+1] = a;
		aGoal = (noOfRows * (noOfCols -3))+1;
		this.goal[(noOfRows * (noOfCols -2))+1] = b;
		bGoal = (noOfRows * (noOfCols -2))+1;
		this.goal[(noOfRows * (noOfCols -1))+1] = c;
		cGoal = (noOfRows * (noOfCols -1))+1;
		this.goal[noOfRows*noOfCols -1] = agent;
		agentGoal = (noOfRows * (noOfCols -1))+1;
	}

	// when called displays the current Board state in a desired manner
	 
	public void displayBoard(int[] b) {
		for(int i =0; i<b.length; i++) {
			if(i%noOfCols == 0) {
				System.out.println("");	
			}
			System.out.print(b[i]+ " ");
		}
	}

	/*gets the position of the agent in a board state given as
	 * its parameter.
	 */
	public int getPosition(int[] n) {
		for(int i = 0; i<n.length; i++) {
			if(n[i] == agent) {
				return i;
			}
		}
		return 0;
	}
	
	/* Similar to the get Position method except this method
	 * gets the position of any block (a, b or c) given as its
	 * parameter
	 */
	public int getPositionBlock(int n) {
		for(int i = 0; i<state.length; i++) {
			if(state[i] == n) {
				return i;
			}
		}
		return 0;
	}

	/*Used to take a state as a parameter and change it as it would
	 * by moving the agent of that state 'up' a block
	 */
	public void up(int[] b) {
		int agentPos = this.getPosition(b);
		if(agentPos - noOfCols >= 0) {
			state = new int[noOfRows*noOfCols];
			int moving = b[agentPos - noOfCols];
			b[agentPos] = moving;
			b[agentPos - noOfCols] = agent;
			System.arraycopy(b, 0, state, 0, b.length);
		}
	}

	/*Used to take a state as a parameter and change it as it would
	 * by moving the agent of that state 'down' a block
	 */
	public void down(int[] b) {
		int agentPos = this.getPosition(b);
		if(agentPos + noOfCols < noOfRows*noOfCols) {
			state = new int[noOfRows*noOfCols];
			int moving = b[agentPos + noOfCols];
			b[agentPos] = moving;
			b[agentPos + noOfCols] = agent;
			System.arraycopy(b, 0, state, 0, b.length);
		}
	}
	
	/*Used to take a state as a parameter and change it as it would
	 * by moving the agent of that state 'left' a block
	 */
	public void left(int[] b) {
		int agentPos = this.getPosition(b);
		if(agentPos%noOfCols != 0 && agentPos != 0) {
			state = new int[noOfRows*noOfCols];
			int moving = b[agentPos - 1];
			b[agentPos] = moving;
			b[agentPos - 1] = agent;
			System.arraycopy(b, 0, state, 0, b.length);
		}
	}

	/*Used to take a state as a parameter and change it as it would
	 * by moving the agent of that state 'right' a block
	 */
	public void right(int[] b) {
		int agentPos = this.getPosition(b);
		if((agentPos + 1)%noOfCols !=0 && agentPos !=(noOfRows*noOfCols) - 1) {
			state = new int[noOfRows*noOfCols];
			int moving = b[agentPos + 1];
			b[agentPos] = moving;
			b[agentPos + 1] = agent;
			System.arraycopy(b, 0, state, 0, b.length);
		}
	}
 
	/* when this method is called it checks if the state of this state Object
	 * is at a goal state (goal state being a tower present anywhere on the board
	 * and the agent in the bottom right hand corner of the board
	 */
	public boolean goal() {
		//return Arrays.equals(state, goal);
		int a = getPositionBlock(this.a);
		int b = getPositionBlock(this.b);
		int c = getPositionBlock(this.c);
		int agent = getPositionBlock(this.agent);
		
		if(a+noOfCols == b && b + noOfCols == c && agent == (noOfRows*noOfCols -1)) {
			return true;
		}
		return false;
		
	}

}

