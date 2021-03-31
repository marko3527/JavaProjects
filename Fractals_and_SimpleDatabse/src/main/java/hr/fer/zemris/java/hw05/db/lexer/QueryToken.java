package hr.fer.zemris.java.hw05.db.lexer;

/**
* Class that represents one token generated by query lexer.
 * It has one constructor and variables that are storing
 * informations about value of the token and type
 * 
 * @author Marko
 *
 */
public class QueryToken {
	
	private QueryTokenType type;
	private String value;
	
	/**
	 * Constructor.
	 * 
	 * @param type {@code QueryTokenType} type of token generated by lexer
	 * @param value {@code String}
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code QueryTokenType} type of token
	 */
	public QueryTokenType getType() {
		return type;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} value of the token
	 */
	public String getValue() {
		return value;
	}
	
	
	@Override
	public String toString() {
		return "(" + getType() + ", " + getValue() + ")";
	}

}