package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

/**
 * Class that models one conditional expression.
 * 
 * @author Marko
 *
 */
public class ConditionalExpression {
	
	
	private IComparisonOperator comparisonOperator;
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	
	
	/**
	 * Constructor.
	 * 
	 * @param comparisonOperator a reference to {@code IComparisonOperator} strategy
	 * @param fieldGetter a reference to {@code IFieldValueGetter} strategy
	 * @param stringLiteral a string literal
	 * @throws NullPointerException if strategies are provided as null references
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter,
								 String stringLiteral,
								 IComparisonOperator comparisonOperator) {
		
		if(comparisonOperator == null || fieldGetter == null) {
			throw new NullPointerException("References to strategies can't be null!");
		}
		
		this.comparisonOperator = comparisonOperator;
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
	}
	
	/**
	 * Getter.
	 * 
	 * @return reference to {@code IComparisonOperator} strategy
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Getter.
	 * 
	 * @return reference to {@code IFieldValueGetter} strategy
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Getter.
	 * 
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

}
