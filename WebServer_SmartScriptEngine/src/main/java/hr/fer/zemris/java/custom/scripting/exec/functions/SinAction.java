package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;


/**
 * Action that calculates the sin of the value that 
 * is stored at the top of the temporary stack and
 * pushes the result back to stack.
 * 
 * @author Marko
 *
 */
public class SinAction implements IFunctionAction{

	@Override
	public void performAction(Stack<Object> tempStack) {
		try {
			Double x = Double.parseDouble("" + tempStack.pop());
			double result = Math.sin(Math.toRadians(x));
			tempStack.push(result);
		} catch (ClassCastException e) {
			System.out.println("Can't calculate sin of not number!");
		}
	}
	
	

}
