package hr.fer.zemris.java.webserver;


/**
 * Interface used for processing users request.
 * 
 * @author Marko
 *
 */
public interface IDispatcher {
	
	
	/**
	 * Method calls second method from this interface.
	 * 
	 * @param urlPath
	 * @throws Exception
	 */
	public void dispatchRequest(String urlPath) throws Exception;
	
	
	/**
	 * Method will process the wanted urlPath user has inputed in browser or 
	 * the path that server wanted to execute.
	 * Internal method that will be called by previous method argument directCall
	 * value true if the call came directly from user. If the argument directCall
	 * is set to true this method will check if the path contains '/private'
	 * and if it does it will send message to user that the wanted page 
	 * can't be accessed.
	 * 
	 * @param urlPath {@code String urlPath to process} 
	 * @param directCall
	 */
	public void internalDispatchRequest(String urlPath, boolean directCall);

}
