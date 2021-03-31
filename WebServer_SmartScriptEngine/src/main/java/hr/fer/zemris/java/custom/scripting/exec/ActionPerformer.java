package hr.fer.zemris.java.custom.scripting.exec;


/**
 * Class that handles performing actions on values. Before 
 * performing an action this class promotes variables from Object types
 * to one of {String, Integer, Double} types.
 * 
 * @author Marko
 *
 */
public class ActionPerformer {
	
	
	private Object currentValue;
	private Object argument;
	private int compareValue;
	
	
	/**
	 * Constructor.
	 * 
	 * @param currentValue {@code Object} current value of the wrapper
	 * @param argument {@code Object} argument to perform action with
	 */
	public ActionPerformer(Object currentValue, Object argument) {
		this.currentValue = currentValue;
		this.argument = argument;
		
		try {
			promoteVariables();
		} catch (NumberFormatException | ClassCastException e) {
			throw new RuntimeException("Can't parse the input string as number!");
		}
		
	}
	
	
	/**
	 * Method that performs an action. If the action is one of {+, -, *, /}
	 * it sets current value to the value wrapper and if the action that 
	 * should be performed is compare then it sets the result of comparison
	 * as local variable that the user will then grab.
	 * 
	 * @param valueWrapper
	 * @param action {@code String} can be {'+', '-', '*', '/', 'compare'}
	 */
	public void performAction(ValueWrapper valueWrapper, String action) {
		if(currentValue == null || argument == null) {
			valueWrapper.setValue(0);
			return;
		}
		
		if((currentValue instanceof String) && ((argument instanceof Double) || 
												(argument instanceof Integer))) {
			throw new RuntimeException("You wanted to perform action with String on a number!");
		}
		
		else {
			switch(action) {
				case("+"): 
					valueWrapper.setValue(addObjects());
					return;
				case("-"):
					valueWrapper.setValue(subObjects());
					return;
				case("*"):
					valueWrapper.setValue(mulObjects());
					return;
				case("/"):
					valueWrapper.setValue(divObjects());
				case("compare"):
					compareValue = compare();
			}
			
		}
	}
	
	
	/**
	 * Getter for value comparison;
	 * 
	 * @return {@code 0} if the values are same, {@code 1} if the current
	 * value is larger that value given in argument, {@code -1} if the current
	 * value is smaller than value given in argument
	 */
	public int getCompareValue() {
		return compareValue;
	}
	
	
	
	/**
	 * Method that goes through rules of determining which type of object
	 * are given objects and then promoting so the action can be performed on them.
	 * Rule 1: If either current value or argument is null, you should treat that value 
	 * 		   as being equal to Integer with value 0.
	 * Rule 2: If current value and argument are not null, they can be instances of Integer,
	 * 		   Double or String. For each value that is String, you should check if String 
	 * 		   literal is decimal value (i.e. does it have somewhere a symbol '.' or 'E').
	 * 		   If it is a decimal value, treat it as such; otherwise, treat it as an Integer
	 * 		   (if conversion fails, you are free to throw RuntimeException since the result
	 * 		   of operation is undefined anyway).
	 * Rule 3: Now, if either current value or argument is Double, operation should be
	 * 		   performed on Doubles, and the result should be stored as an instance of
	 * 		   Double. If not, both arguments must be Integers so the operation should
	 * 		   be performed on Integers and the result stored as an Integer.
	 * 
	 */
	private void promoteVariables() {
		if(currentValue instanceof String) {
			String currentString = ((String) currentValue).replace("\"", "");
			if(currentString.contains(".") || currentString.contains("E")) {
				currentValue = Double.parseDouble(currentString);
			}
			else {
				currentValue = Integer.parseInt(currentString);
			}
		}
		else if(currentValue instanceof Double) {
			currentValue = (Double) currentValue;
		}
		else if(currentValue instanceof Integer) {
			currentValue = (Integer) currentValue;
		}
		
		if(argument instanceof String) {
			String argString = (String) argument;
			if(argString.contains(".") || argString.contains("E")) {
				argument = Double.parseDouble(argString);
			}
			else {
				argument = Integer.parseInt(argString);
			}
		}
		else if(argument instanceof Double) {
			argument = (Double) argument;
		}
		else if(argument instanceof Integer) {
			argument = (Integer) argument;
		}
	}
	
	
	/**
	 * Method that adds the objects.
	 * 
	 * @return result of adding values
	 */
	private Object addObjects() {
		if((currentValue instanceof Double) && (argument instanceof Double)) {
			return (Double)currentValue + (Double)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Integer)) {
			return (Integer)currentValue + (Integer)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Double)) {
			return (double) ((Integer) currentValue).intValue() + (Double) argument;
		}
		else{
			return (Double) currentValue + (double)((Integer)argument).intValue();
		}
	}
	
	
	/**
	 * Method that subtracts the objects.
	 * 
	 * @return result of subtracting values
	 */
	private Object subObjects() {
		if((currentValue instanceof Double) && (argument instanceof Double)) {
			return (Double)currentValue - (Double)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Integer)) {
			return (Integer)currentValue - (Integer)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Double)) {
			return (double) ((Integer) currentValue).intValue() + (Double) argument;
		}
		else{
			return (Double) currentValue - (double)((Integer)argument).intValue();
		}
	}
	
	
	/**
	 * Method that multiplyis the objects.
	 * 
	 * @return result of multiplying values
	 */
	private Object mulObjects() {
		if((currentValue instanceof Double) && (argument instanceof Double)) {
			return (Double)currentValue * (Double)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Integer)) {
			return (Integer)currentValue * (Integer)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Double)) {
			return (double) ((Integer) currentValue).intValue() + (Double) argument;
		}
		else{
			return (Double) currentValue * (double)((Integer)argument).intValue();
		}
	}
	
	
	/**
	 * Method that divides the objects.
	 * 
	 * @return result of dividing values
	 */
	private Object divObjects() {
		if((currentValue instanceof Double) && (argument instanceof Double)) {
			return (Double)currentValue / (Double)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Integer)) {
			return (Integer)currentValue / (Integer)argument;
		}
		else if((currentValue instanceof Integer) && (argument instanceof Double)) {
			return (double) ((Integer) currentValue).intValue() + (Double) argument;
		}
		else{
			return (Double) currentValue / (double)((Integer)argument).intValue();
		}
	}
	
	
	/**
	 * Method that compares two objects depending of their instance
	 * 
	 * {@code 0} if the values are same, {@code 1} if the current
	 * value is larger that value given in argument, {@code -1} if the current
	 * value is smaller than value given in argument
	 */
	private int compare() {
		if(currentValue == null && argument == null) {
			return 0;
		}
		
		Integer currValInt;
		Integer argValInt;
		if(currentValue == null && argument != null) {
			currValInt = 0;
		}
		else if(currentValue != null && argument != null) {
			argValInt = 0;
		}
		
		if((currentValue instanceof Double) && (argument instanceof Double)) {
			Double currVal = (Double)currentValue;
			Double argVal = (Double)argument;
			if(currVal == argVal) {
				return 0;
			}
			else return currVal > argVal ? 1 : -1;
		}
		
		else if((currentValue instanceof Integer) && (argument instanceof Integer)) {
			currValInt = (Integer)currentValue;
			argValInt = (Integer)argument;
			if(currValInt == argValInt) {
				return 0;
			}
			else return currValInt > argValInt ? 1 : -1;
		}
		
		else if((currentValue instanceof Integer) && (argument instanceof Double)) {
			Double currVal = (double)((Integer)currentValue).intValue();
			Double argVal = (Double)argument;
			if(currVal == argVal) {
				return 0;
			}
			else return currVal > argVal ? 1 : -1;
		}
		
		else{
			Double currVal = (Double)currentValue;
			Double argVal = (double)((Integer)argument).intValue();
			if(currVal == argVal) {
				return 0;
			}
			else return currVal > argVal ? 1 : -1;
		}
		
	}

}
