package hr.fer.zemris.java.voting.dao;


/**
 * Exception that is being thrown when DAO interface has
 * problems in communication with databases.
 * 
 * @author Marko
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor.
	 * 
	 */
	public DAOException() {
		
	}
	
	
	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppresion
	 * @param writeableStackTrace
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppresion, boolean writeableStackTrace) {
		super(message, cause, enableSuppresion, writeableStackTrace);
	}
	
	
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
	
	
	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
	
	

}
