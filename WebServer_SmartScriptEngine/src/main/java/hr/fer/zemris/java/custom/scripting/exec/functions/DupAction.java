package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;


/**
 * Action that takes the top value from temporary stack and
 * duplicates it and then puts both values at the top of the stack.
 * 
 * @author Marko
 *
 */
public class DupAction implements IFunctionAction {

	@Override
	public void performAction(Stack<Object> tempStack) {
		Object topValue = tempStack.pop();
		tempStack.push(topValue);
		tempStack.push(topValue);
	}

}
