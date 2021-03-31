package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Web worker that calls the dispatcher with private script to generate 
 * repsone in format of html document.
 * It is mapped to /index2.html
 * 
 * @author Marko
 *
 */
public class HomeWorker implements IWebWorker {

	
	/**
	 * {@inheritDoc}
	 * HomeWorker checks if the context has persistent parameter
	 * with key 'bgcolor'. If it does then it sets the color to that
	 * value under that key and else it sets the color of the background
	 * to the defualt value.
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String bgcolor = context.getPersistentParameter("bgcolor");
		context.setTemporaryParameter("background",
									  bgcolor == null ? "7F7F7F" : bgcolor);
		if(bgcolor == null) {
			context.setPersistentParameter("bgcolor", "7F7F7F");
		}
		

		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		
	}
	
	

}
