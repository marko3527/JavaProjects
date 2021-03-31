package hr.fer.zemris.java.webserver;


/**
 * Interface for web workers
 * 
 * @author Marko
 *
 */
public interface IWebWorker {
	
	
	/**
	 * This method does the job of drawing, creating graphics, creating pages
	 * and so on...
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
