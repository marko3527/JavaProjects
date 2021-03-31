package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Web worker that prints the parameters that user inputted as
 * part of the GET request
 * This worker is not mapped!
 * 
 * @author Marko
 *
 */
public class EchoParams implements IWebWorker{

	
	/**
	 * {@inheritDoc}
	 * EchoParams worker takes the pair of key values that is it
	 * as part of the request context and just makes a response with html
	 * page containing all those key value pairs formatted as table.
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table>"
				+ "		<tr>" 
				+ "			<th> Key </th>"
				+ "			<th> Value </th>"
				+ "		</tr>");
		for(String key : context.getParameterNames()) {
			sb.append("<tr>"
					+ "		<th>" + key
					+ "     <th>" + context.getParameter(key)
					+ "<tr>");
		}
		sb.append("</table>");
		
		context.write(sb.toString());
		
	}
	
	
	

}
