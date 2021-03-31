package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Workers that calculates the sum of two numbers.
 * It is mapped to /calc
 * 
 * @author Marko
 *
 */
public class SumWorker implements IWebWorker {
	
	private int a = 1;
	private int b = 2;
	

	
	/**
	 * {@inheritDoc}
	 * SumWorker checks if the user has inputted any parameters
	 * with key a and key b. It it has it tries to convert them to
	 * Integer and if it fails it uses the default value for that variable.
	 * After if has calculated the sum of the a and b it sets the 
	 * value to temporary params under key 'zbroj'
	 * After all that it calls the contexts dispatcher with private
	 * script calc.smscr that returns the response in format of the html
	 * page. 
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		try {
			this.a = Integer.parseInt(context.getParameter("a"));
		} catch (Exception e) {
			this.a = 1;
		}
		
		try {
			this.b = Integer.parseInt(context.getParameter("b"));
		} catch (Exception e) {
			this.b = 2;
		}
		
		int sum = a + b;
		context.setTemporaryParameter("zbroj", "" + sum);
		context.setTemporaryParameter("varA", "" + a);
		context.setTemporaryParameter("varB", "" + b);
		
		if(sum % 2 == 0) {
			context.setTemporaryParameter("imgName", "earth.gif");
		}
		else {
			context.setTemporaryParameter("imgName", "smoke.png");
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		
	}
	
	

}
