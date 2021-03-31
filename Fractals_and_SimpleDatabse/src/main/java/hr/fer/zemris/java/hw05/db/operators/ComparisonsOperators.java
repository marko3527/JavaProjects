package hr.fer.zemris.java.hw05.db.operators;

/**
 * Class that implements comparisons for each operator.
 * 
 * @author Marko
 *
 */
public class ComparisonsOperators {
	
	/**
	 * Checks if first string in (value1 LESS value2) is lexicographically smaller than 
	 * second string.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> {
		if(value1.compareTo(value2) < 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Checks if first string in (value1 LESS OR EQUALS value2) is lexicographically 
	 * smaller or equals to second string.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {	
		if(value1.compareTo(value2) <= 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Checks if first string in (value1 GREATER value2) is lexicographically
	 * greater than second string.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		if(value1.compareTo(value2) > 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Checks if first string in (value1 EQUALS value2) is lexicographically 
	 * equals to second string.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		if(value1.compareTo(value2) == 0) {
			return true;
		}
		return false;
	};
	
	
	/**
	 * Checks if first string in (value1 NOT EQUALS value2) is lexicographically
	 * different than second string.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		if(value1.compareTo(value2) != 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Checks if first string in (value1 LIKE value2) looks like value2.
	 * It changes the second string so it could call method matches with appropriate
	 * symbols for any number of symbols. In regex, any symbol is represented as '.'
	 * and here in the query user can put '*' for any number of symbols, so this method 
	 * replaces '*' with '.*' that means, occurance of any symbol any 0 or more number 
	 * of times.
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		value2 = value2.replace("*",".*");
		if(value1.matches(value2)) {
			return true;
		}
		return false;
	};

}
