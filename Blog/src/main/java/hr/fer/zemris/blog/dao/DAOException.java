package hr.fer.zemris.blog.dao;


/**
 * Exception that is being thrown when DAO interface has
 * problems in communication with databases.
 * 
 * @author Marko
 *
 */
public class DAOException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	
	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
	
}
