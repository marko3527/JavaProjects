package hr.fer.zemris.java.hw05.db.parser;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.getters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryToken;
import hr.fer.zemris.java.hw05.db.lexer.QueryTokenType;
import hr.fer.zemris.java.hw05.db.operators.ComparisonsOperators;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

/**
 * Class that implements a parser for parsing simple query for 
 * database of student records.
 * 
 * @author Marko
 *
 */
public class QueryParser {
	
	private QueryLexer lexer;
	private List<QueryToken> tokens;
	
	public QueryParser(String text) {
		lexer = new QueryLexer(text);
		tokens = new LinkedList<QueryToken>();
		
		while(lexer.nextToken().getType() != QueryTokenType.EOF) {
			tokens.add(lexer.getToken());
		}
	}
	
	
	/**
	 * Method that checks whether the written query is direct. Direct query 
	 * looks like this : "jmbag='xxx'".
	 * 
	 * @return {@code true} if given query is direct
	 * 		   {@code false} if given query is not direct
	 */
	public boolean isDirectQuery() {
		if(tokens.get(0).getValue().toLowerCase().equals("jmbag") &&
		   tokens.size() == 3) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Method that checks whether the given query is direct, if it is
	 * it returns the queried JMBAG.
	 * 
	 * @return {@code String} queried JMBAG
	 * @throws IllegalStateException if given query is not direct
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("The given query is not direct query so "
					+ "there is no jmbags to be returned!");
		}
		else {
			return tokens.get(2).getValue();
		}
	}
	
	/**
	 * Method that goes through a list of tokens and computes conditional expressions.
	 * Conditional expressions are attached with key word 'AND', so if there is a value 
	 * of a token with 'AND' in any form it gets over that token.
	 * 
	 * @return {@code List} list of conditional expressions to be executed on a database
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> expressions = new LinkedList<>();
		
		for(int i = 0; i < tokens.size(); i += 3) {
			if(tokens.get(i).getType().equals(QueryTokenType.KEY_WORD) &&
			   tokens.get(i).getValue().toLowerCase().equals("and")) {
				i++;
			}
			IFieldValueGetter fieldGetter = computeFieldGetter(tokens.get(i).getValue());
			IComparisonOperator operator = computeOperator(tokens.get(i + 1).getValue());
			String literal = tokens.get(i + 2).getValue();
			expressions.add(new ConditionalExpression(fieldGetter, literal, operator));
		}
		
		return expressions;
	}
	
	
	/**
	 * Based on given value of the token it computes one ComparisonOperator
	 * 
	 * @param value {@code String} value of the current token
	 * @return {@code ComparisonOperators} type of strategy
	 * @throws {@code IllegalStateException} if the value is not expected type of comparison
	 */
	private IComparisonOperator computeOperator(String value) {
		if(value.toLowerCase().equals("like")) {
			return ComparisonsOperators.LIKE;
		}
		
		else if(value.equals(">")) {
			return ComparisonsOperators.GREATER;
		}
		
		else if(value.equals("!=")) {
			return ComparisonsOperators.NOT_EQUALS;
		}
		
		else if(value.equals("=")) {
			return ComparisonsOperators.EQUALS;
		}
		
		else if(value.equals("<")) {
			return ComparisonsOperators.LESS;
		}
		
		else if(value.equals("<=")) {
			return ComparisonsOperators.LESS_OR_EQUALS;
		}
		
		else {
			throw new IllegalStateException("Unexpected comparsion symbol!");
		}
	}


	/**
	 * Based on given value of the token it computes one FieldValueGetter
	 * 
	 * @param value {@code String} value of the current token
	 * @return {@code IFieldValueGetter} type of strategy
	 * @throws {@code IllegalSateException} if value doesn't match any of the getters
	 */
	private IFieldValueGetter computeFieldGetter(String value) {
		if(value.toLowerCase().equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}
		
		else if(value.toLowerCase().equals("lastname")) {
			return FieldValueGetters.LAST_NAME;
		}
		
		else if(value.toLowerCase().equals("firstname")) {
			return FieldValueGetters.FIRST_NAME;
		}
		
		else {
			throw new IllegalStateException("Unexpected word in a query!");
		}
	}
	

}
