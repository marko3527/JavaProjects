package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class for exception that happens during parsing the document.
 * 
 * @author Marko
 *
 */
public class SmartScriptParserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartScriptParserException(String text) {
		super(text);
	}
}
