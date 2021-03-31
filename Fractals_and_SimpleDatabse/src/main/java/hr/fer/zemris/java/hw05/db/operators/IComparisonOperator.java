package hr.fer.zemris.java.hw05.db.operators;

/**
 * Interface that offers a method to works with string literals and doing
 * different comparisons between them.
 * 
 * @author Marko
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that checks whether the literals satisfy given comparation operator
	 * 
	 * @param value1
	 * @param value2
	 * @return {@code true} if the comparation is satisfied
	 * 		   {@code false} if the comparation is not satisfied
	 */
	public boolean satisfied(String value1, String value2);
}
