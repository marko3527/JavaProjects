package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class provides a stack to work with turtle states in order to 
 * properly draw on the screen.
 * 
 * @author Marko
 *
 */
public class Context {
	
	private ObjectStack<TurtleState> stackOfStates;
	
	
	/**
	 * Default constructor.
	 */
	public Context() {
		this.stackOfStates = new ObjectStack<TurtleState>();
	}
	
	/**
	 * Returns the current state of the turtle without removing it
	 * from the stack.
	 * 
	 * @return {@code TurtleState} current state of the turtle
	 */
	public TurtleState getCurrentState() {
		return stackOfStates.peek();
	}
	
	
	/**
	 * Method that pushes the state given in argument to a stack.
	 * 
	 * @param state {@code TurtleState} state to be pushed on the stack
	 */
	public void pushState(TurtleState state) {
		stackOfStates.push(state);
	}
	
	
	/**
	 * Method that removes the last state from the stack.
	 */
	public void popState() {
		stackOfStates.pop();
	}

}
