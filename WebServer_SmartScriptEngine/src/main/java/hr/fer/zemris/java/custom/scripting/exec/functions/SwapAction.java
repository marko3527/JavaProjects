package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;


/**
 * Action that swaps the first two top values from stack.
 * So if at the top of the stack is x,y after this operation
 * the stack will look like this y,x
 * 
 * @author Marko
 *
 */
public class SwapAction implements IFunctionAction {

	@Override
	public void performAction(Stack<Object> tempStack) {
		Object topValue = tempStack.pop();
		Object secondTopValue = tempStack.pop();
		tempStack.push(topValue);
		tempStack.push(secondTopValue);
	}

}
