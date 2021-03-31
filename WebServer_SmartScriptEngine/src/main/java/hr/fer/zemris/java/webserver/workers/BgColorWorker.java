package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Web worker that changes the background of the page.
 * This worker it mapped to /setbgcolor
 * 
 * @author Marko
 *
 */
public class BgColorWorker implements IWebWorker{

	/**
	 * {@inheritDoc}
	 * Background worker checks if there is value with key "bgcolor" 
	 * stored in parameters of the {@code RequestedContext}, if there is
	 * that means that user has requested the change of the background
	 * color so it stores the value of the new color that user requested
	 * in persistant parameters and call the dispatched method with url of the
	 * original page.
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		if(color != null) {
			if(color.length() == 6) {
				context.setPersistentParameter("bgcolor", color);
			}
		}
		context.getDispatcher().dispatchRequest("/index2.html");
	}
	
	

}
