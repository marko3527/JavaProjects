package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

/**
 * Interface that models the action that can be performed in echo node
 * of the smart script document.
 * 
 * @author Marko
 *
 */
public interface IFunctionAction {
	
	/**
	 * Performs action with given temporary stack
	 * @param tempStack
	 */
	public void performAction(Stack<Object> tempStack);


}
