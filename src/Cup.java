///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  MeasuringCupsSolver.java
// File:             Cup.java
// Semester:         (367) Fall 2016
//
// Author:           Rouyi Ding  rding26@wisc.edu
// CS Login:         rouyi
// Lecturer's Name:  Deb Deppeler
// Lab Section:      001
//
////////////////////////////////////////////////////////////////////////////////
import java.lang.IllegalArgumentException;
/**
 * A representation of a measuring cup.
 */
public class Cup {

	private int capacity;
	private int currentAmount;

	/**
	 * Construct a measuring cup
	 * 
	 * @param capacity
	 *            the maximum volume of the measuring cup
	 * @param currentAmount
	 *            the current volume of fluid in the measuring cup
	 * @throws IllegalArgumentException
	 *             when any of these conditions are true: capacity < 0,
	 *             currentAmount < 0, currentAmount > capacity
	 * 
	 */
	public Cup(int capacity, int currentAmount) {
		// TODO
		if (capacity <0 || currentAmount < 0 || currentAmount > capacity){
			throw new IllegalArgumentException();
		} 
		
		this.capacity = capacity;
		this.currentAmount = currentAmount;
	}

	
	/**
	 * @return capacity
	 */
	public int getCapacity() {
		// TODO
		return this.capacity;
	}

	
	/**
	 * @return currentAmount
	 */
	public int getCurrentAmount() {
		// TODO
		return this.currentAmount;
	}

	
	/**
	 * Compare this cup against another cup
	 * 
	 * @param cup
	 *            an other cup to compare against this cup
	 * @return true if the other cup has the same capacity and currentAmount as
	 *         this cup and false otherwise
	 */
	public boolean equals(Cup cup) {
		// TODO
		if (this.capacity == cup.getCapacity() && 
				this.currentAmount == cup.getCurrentAmount()){
			return true;
		} else {
			return false;
		}
		
	}

	
	/**
	 * @return a string containing the currentAmount
	 */
	public String toString() {
		return String.valueOf(this.currentAmount);
	}
}
