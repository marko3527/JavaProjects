package hr.fer.zemris.java.gui.layouts;


/**
 * Exception that happens during adding component to {@code CalcLayout}
 * on wrong indexes.
 * 
 * @author Marko
 *
 */
public class CalcLayoutException extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param text {@code String} message on why the exception happened.
	 */
	public CalcLayoutException(String text) {
		super(text);
	}

}
