package hr.fer.zemris.java.hw05.db.filters;

import java.util.List;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class that implements IFilter and gives implementation of accepting 
 * one student record based on more filters.
 * 
 * @author Marko
 *
 */
public class QueryFilter implements IFilter{
	
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructor.
	 * 
	 * @param expressions {@code List} list of expression to test one student record on
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		if(expressions == null) {
			throw new NullPointerException("List of expressions can't be null!");
		}
		
		this.expressions = expressions;
	}
	
	
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression expression : expressions) {
			if(!expression.getComparisonOperator().satisfied(
			   expression.getFieldGetter().get(record), expression.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}

}
