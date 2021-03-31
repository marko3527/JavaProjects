package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Action that takes two arguments. FIrst argument is decimal number
 * and second number is decimal format of the number that should be applied
 * to the decimal number.
 * 
 * @author Marko
 *
 */
public class DecFMTAction implements IFunctionAction{

	@Override
	public void performAction(Stack<Object> tempStack) {
		try {
			DecimalFormat format = new DecimalFormat((String)tempStack.pop());
			Double number = (double) tempStack.pop();
			String result = format.format(number).replaceAll("\"","");
			tempStack.push(Double.parseDouble(result));
		} catch (ClassCastException e) {
			System.out.println("Given format is not compatible with Decimal Format");
		}
	}
	
	

}
