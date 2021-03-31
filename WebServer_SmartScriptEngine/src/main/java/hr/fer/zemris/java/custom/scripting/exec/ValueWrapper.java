package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that implements the wrapper that stores one value 
 * and can perform simple artihmetic operations given second
 * value.
 *
 * 
 * @author Marko
 *
 */
public class ValueWrapper {
	
	private Object value;
	
	
	/**
	 * Constructor. Sets the inital value of the wrapper.
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	
	/**
	 * Getter. Returns the current value of the wrapper.
	 * 
	 * @return {@code Object}
	 */
	public Object getValue() {
		return value;
	}
	
	
	/**
	 * Setter. Sets the current value of the wrapper to the given
	 * value in argument.
	 * 
	 * @param value {@code Object}
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	/**
	 * Method that adds given value in argument to
	 * stored value in wrapper. 
	 * 
	 * @param incValue {@code Object} value to add
	 */
	public void add(Object incValue) {
		ActionPerformer performer = new ActionPerformer(value, incValue);
		performer.performAction(this, "+");
		
	}
	
	
	/**
	 * Method that subtracts given value in argument to
	 * stored value in wrapper. 
	 * 
	 * @param decValue {@code Object} value to subtract
	 */
	public void subtract(Object decValue) {
		ActionPerformer performer = new ActionPerformer(value, decValue);
		performer.performAction(this, "-");
		
	}
	
	
	/**
	 * Method that multiplies given value in argument to
	 * stored value in wrapper. 
	 * 
	 * @param mulValue {@code Object} value to multiply
	 */
	public void multiply(Object mulValue) {
		ActionPerformer performer = new ActionPerformer(value, mulValue);
		performer.performAction(this, "*");
		
	}
	
	
	/**
	 * Method that divides given value in argument to
	 * stored value in wrapper. 
	 * 
	 * @param divValue {@code Object} value to divide
	 */
	public void divide(Object divValue) {
		ActionPerformer performer = new ActionPerformer(value, divValue);
		performer.performAction(this, "/");
		
	}
	
	
	/**
	 * Method that compares current value stored in wrapper
	 * with the value given in argument. 
	 * 
	 * @param withValue {@code Object} value to compare with
	 * @return {@code 0} if the values are same, {@code 1} if the current
	 * value is larger that value given in argument, {@code -1} if the current
	 * value is smaller than value given in argument
	 */
	public int numCompare(Object withValue) {
		ActionPerformer performer = new ActionPerformer(value, withValue);
		performer.performAction(this, "compare");
		return performer.getCompareValue();
	}
	
	

}
