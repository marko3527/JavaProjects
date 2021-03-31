package hr.fer.zemris.java.hw03.prob1;


/**
 * Exception that extends RuntimeException that will be used in 
 * the lexer.
 * 
 * @author Marko
 *
 */
public class LexerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor.
	 * 
	 * @param string {@code String} message in exception
	 */
	public LexerException(String string) {
		super(string);
	}

}
