package hr.fer.zemris.java.webserver.workers;

import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Web worker that prints the time when the user made request and
 * and analyzes the parameters user has inputted as part of GET request
 * It is mapped to /hello
 * 
 * @author Marko
 *
 */
public class HelloWorker implements IWebWorker{
	
	
	/**
	 * {@inheritDoc}
	 * HelloWorker returns a reponse in a format of html page. On that
	 * page it prints the time when the user has made a request to the worker
	 * and it also checks if the user has provided some parameters. It user has
	 * provided parameter, with key 'name', it prints the length of that
	 * parameter, else it prints that no parameters were given.
	 * 
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		context.setMimeType("text/html");
		String name = context.getParameter("name");
		
		try {
			context.write("<html><body>");
			context.write("<h1>HELLLLO!</h1>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");
			
			if(name == null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			}
			else {
				context.write("<p>Your name has " + name.trim().length() + " letters.</p>");
			}
			context.write("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
