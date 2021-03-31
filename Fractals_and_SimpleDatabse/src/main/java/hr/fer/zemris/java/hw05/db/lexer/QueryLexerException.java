package hr.fer.zemris.java.hw05.db.lexer;


/**
 * Exception that is being created by a query lexer when it
 * finds some errors in querys
 * 
 * @author Marko
 *
 */
public class QueryLexerException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor.
	 * 
	 * @param text message that should be outputed to user.
	 */
	public QueryLexerException(String text) {
		super(text);
	}

}
