///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  MeasuringCupsSolver.java
// File:             MeasuringCupsPuzzle.java
// Semester:         (367) Fall 2016
//
// Author:           Rouyi Ding  rding26@wisc.edu
// CS Login:         rouyi
// Lecturer's Name:  Deb Deppeler
// Lab Section:      001
//
////////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.Stack;

/**
 * A class describing the measuring cups puzzle with a startState
 * {@link MeasuringCupsPuzzleState} and a goalState
 * {@link MeasuringCupsPuzzleState}
 */
public class MeasuringCupsPuzzle{

	private MeasuringCupsPuzzleState startState;
	private MeasuringCupsPuzzleState goalState;

	private MeasuringCupsPuzzleADT measuringCupsPuzzleADT;

	private MeasuringCupsPuzzleStateList pathFromStartToGoal;
	private MeasuringCupsPuzzleStateList processedStates;
	private MeasuringCupsPuzzleState foundGoalState;

	/**
	 * Construct a puzzle object by describing the startState and goalState
	 *
	 * @param startState
	 *            a state describing the capacities and initial volumes of
	 *            measuring cups {@link MeasuringCupsPuzzleState}
	 * @param goalState
	 *            a state describing the desired end volumes of measuring cups
	 *            {@link MeasuringCupsPuzzleState}
	 */
	public MeasuringCupsPuzzle(MeasuringCupsPuzzleState startState, 
			MeasuringCupsPuzzleState goalState) {
		this.startState = startState;
		this.goalState = goalState;

		this.pathFromStartToGoal = new MeasuringCupsPuzzleStateList();
		this.processedStates = new MeasuringCupsPuzzleStateList();
		this.foundGoalState = null;
		this.measuringCupsPuzzleADT = null;
	}

	/**
	 * Solve the measuring cups puzzle if it can be solved. Set processedStates
	 * by adding a {@link MeasuringCupsPuzzleState} graph node to the list as
	 * the algorithm visits that node. Set foundGoalState to a
	 * {@link MeasuringCupsPuzzleState} if the graph traversal algorithm labeled
	 * by *algorithm* visits a node with the same values as the desired
	 * goalState
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 * @return true if the puzzle can be solved (and has been solved, see
	 *         {@link retrievePath} to obtain the solution stored in this
	 *         object) and false otherwise
	 */
	public boolean findPathIfExists(String algorithm) {
		// TODO
		resetCupPuzzle();
		chooseADT(algorithm);
		// initialize(unvisited = measuringCupsPuzzleADT)
		
		//add startState to unvisited
		this.measuringCupsPuzzleADT.add(startState);
		MeasuringCupsPuzzleState state = null;

		//while some states are unvisited
		while (!this.measuringCupsPuzzleADT.isEmpty()){
			//set next unvisited state that is not processed as state
			MeasuringCupsPuzzleState tmp;
			
			do {
				if (this.measuringCupsPuzzleADT.isEmpty()){
					return false;
				} else {
					tmp = this.measuringCupsPuzzleADT.remove();
				}
				
			} while (isProcessed(tmp));
			state = tmp;

			if (state.equals(goalState)){
				this.foundGoalState = state;
				return true;
			} else {
				//else generate successors, and add to unvisited
				 MeasuringCupsPuzzleStateList s = getSuccessors(state);
				 
				 Iterator<MeasuringCupsPuzzleState> itrS = s.iterator();
					while (itrS.hasNext()){
						this.measuringCupsPuzzleADT.add(itrS.next());
					}
					
			}
					
			//add state to processedStates
			this.processedStates.add(state);
			state = null;
		}
		
		return false;
	}

	/**
	 * Set member measuringCupsPuzzleADT {@link MeasuringCupsPuzzleADT} with a
	 * data type that will be used to solve the puzzle.
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 */
	private void chooseADT(String algorithm) {
		// TODO
		if (algorithm.equals("BFS")){
			this.measuringCupsPuzzleADT = new MeasuringCupsPuzzleQueue();
		} else if (algorithm.equals("DFS")){
			this.measuringCupsPuzzleADT = new MeasuringCupsPuzzleStack();
		} else throw new MeasuringCupsPuzzleException(Config.INVALID_ALGORITHM);
	}

	/**
	 * Reset the puzzle by erasing all member variables which store some aspect
	 * of the solution (pathFromStartToGoal, processedStates, and
	 * foundGoalState) and setting them to their initial values
	 */
	private void resetCupPuzzle() {
		pathFromStartToGoal.clear();
		processedStates.clear();
		foundGoalState = null;
	}

	/**
	 * Mark the graph node represented by currentState has been visited for the
	 * purpose of the graph traversal algorithm being used to solve the puzzle
	 * (set by {@link chooseADT})
	 *
	 * @param currentState
	 *            {@link MeasuringCupsPuzzleState}
	 * @return true if the currentState has been visited and false otherwise
	 */
	private boolean isProcessed(MeasuringCupsPuzzleState currentState) {
		// TODO
		Iterator<MeasuringCupsPuzzleState> itr = processedStates.iterator();
		
		//Iterate over the processedStstes list to check 
		while (itr.hasNext()){
			if (itr.next().equals(currentState)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Assuming {@link findPathIfExists} returns true, return the solution that
	 * was found. Set pathFromStartToGoal by starting at the foundGoalState and
	 * accessing/setting the current node to the parentState
	 * {@link MeasuringCupsPuzzleState#getParentState} until reaching the
	 * startState
	 *
	 * @return a list of states {@link MeasuringCupsPuzzleStateList}
	 *         representing the changes in volume of cupA and cupB from the
	 *         initial state to the goal state.
	 */
	public MeasuringCupsPuzzleStateList retrievePath() {
		
		// TODO
		MeasuringCupsPuzzleStack tmp = new MeasuringCupsPuzzleStack();
		while (foundGoalState != null){			
			tmp.add(foundGoalState);
			this.foundGoalState = this.foundGoalState.getParentState();
		
		}
		
		while (!tmp.isEmpty()){
			this.pathFromStartToGoal.add(tmp.remove());
		}
        return this.pathFromStartToGoal;
	}

	/**
	 * Enumerate all possible states that can be reached from the currentState
	 *
	 * @param currentState
	 *            the current volumes of cupA and cupB
	 * @return a list of states {@link MeasuringCupsPuzzleStateList} that can be
	 *         reached by emptying cupA or cupB, pouring from cupA to cupB and
	 *         vice versa, and filling cupA or cupB to its max capacity
	 */
	public MeasuringCupsPuzzleStateList getSuccessors(
			MeasuringCupsPuzzleState currentState) {
		MeasuringCupsPuzzleStateList successors = 
				new MeasuringCupsPuzzleStateList();

		if (currentState == null) {
			return successors;
		}
		// TODO
		
		// Fill CupA
		successors.add(fillCupA(currentState));
		
		// Fill CupB
		successors.add(fillCupB(currentState));
		
		// Empty CupA
		successors.add(emptyCupA(currentState));
		
		// Empty CupB
		successors.add(emptyCupB(currentState));
		
		// Pour from CupA to CupB
		successors.add(pourCupAToCupB(currentState));
		
		// Pour from CupB to CupA
		successors.add(pourCupBToCupA(currentState));
		
		// remove successors if same as currentState
        Iterator<MeasuringCupsPuzzleState> itr = successors.iterator(); 
		while (itr.hasNext()){
			MeasuringCupsPuzzleState tmp = itr.next();
			if (tmp == currentState){
				successors.remove(tmp);
			}
		}
		
		return successors;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupA to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupA(MeasuringCupsPuzzleState 
			currentState) {
		// TODO
		int currentB = currentState.getCupB().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		
		// Fill CupA
		Cup fillACupA = new Cup(capacityA, capacityA);
		Cup fillACupB = new Cup(capacityB, currentB);	
		
		MeasuringCupsPuzzleState fillA = new MeasuringCupsPuzzleState
				(fillACupA, fillACupB, currentState);
		
		return fillA;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupB to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupB(MeasuringCupsPuzzleState 
			currentState) {
		// TODO
		int currentA = currentState.getCupA().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		// Fill CupB
		Cup fillBCupA = new Cup(capacityA, currentA);
		Cup fillBCupB = new Cup(capacityB, capacityB);	
				
		MeasuringCupsPuzzleState fillB = new MeasuringCupsPuzzleState
						(fillBCupA, fillBCupB, currentState);
		
		return fillB;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupA
	 */
	public MeasuringCupsPuzzleState emptyCupA(MeasuringCupsPuzzleState
			currentState) {
		// TODO
		int currentB = currentState.getCupB().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		// Empty CupA
		Cup emptyACupA = new Cup(capacityA, 0);
		Cup emptyACupB = new Cup(capacityB, currentB);	
				
		MeasuringCupsPuzzleState emptyA = new MeasuringCupsPuzzleState
						(emptyACupA, emptyACupB, currentState);
		
		return emptyA;
	
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupB
	 */
	public MeasuringCupsPuzzleState emptyCupB(MeasuringCupsPuzzleState 
			currentState) {
		// TODO
		int currentA = currentState.getCupA().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		// Empty CupB
		Cup emptyBCupA = new Cup(capacityA, currentA);
		Cup emptyBCupB = new Cup(capacityB, 0);	
				
		MeasuringCupsPuzzleState emptyB = new MeasuringCupsPuzzleState
						(emptyBCupA, emptyBCupB, currentState);
		
		return emptyB;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupA into cupB until either cupA is empty or cupB is full
	 */
	public MeasuringCupsPuzzleState pourCupAToCupB(MeasuringCupsPuzzleState 
			currentState) {
		// TODO
		int currentA = currentState.getCupA().getCurrentAmount();
		int currentB = currentState.getCupB().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		
		// Pour from CupA to CupB
		int pourMax = capacityB - currentB;
		Cup pourAtoBCupA = null;
		Cup pourAtoBCupB = null;
		// if cupA's current amount is not less than pourMax
		if (currentA >= pourMax){
			pourAtoBCupA = new Cup(capacityA, currentA-pourMax);
			pourAtoBCupB = new Cup(capacityB, capacityB);
		} else {
			// else if cupA's current amount is less than pourMax
			pourAtoBCupA = new Cup(capacityA, 0);
			pourAtoBCupB = new Cup(capacityB, currentB + currentA);
		}
				
		MeasuringCupsPuzzleState pourAtoB = new MeasuringCupsPuzzleState
						(pourAtoBCupA, pourAtoBCupB,currentState);
		
		return pourAtoB;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupB into cupA until either cupB is empty or cupA is full
	 */
	public MeasuringCupsPuzzleState pourCupBToCupA(MeasuringCupsPuzzleState 
			currentState) {
		// TODO
		int currentA = currentState.getCupA().getCurrentAmount();
		int currentB = currentState.getCupB().getCurrentAmount();
		int capacityA = currentState.getCupA().getCapacity();
		int capacityB = currentState.getCupB().getCapacity();
		
		// Pour from CupB to CupA
		int pourMax = capacityA - currentA;
		Cup pourBtoACupA = null;
		Cup pourBtoACupB = null;
		// if cupB's current amount is not less than pourMax
		if (currentB >= pourMax){
			pourBtoACupB = new Cup(capacityB, currentB-pourMax);
			pourBtoACupA = new Cup(capacityA, capacityA);
		} else {
			// else if cupB's current amount is less than pourMax
			pourBtoACupB = new Cup(capacityB, 0);
			pourBtoACupA = new Cup(capacityA, currentB + currentA);
		}
		
		MeasuringCupsPuzzleState pourBtoA = new MeasuringCupsPuzzleState
				(pourBtoACupA, pourBtoACupB,currentState);
		
		return pourBtoA;
	}
	
	
}
