package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that takes two arguments. First argument is the key under
 * which the value is and second is default value if the key does not
 * exist. It takes the value with key name from request context parameters
 * and puts it at the top of the stack.
 * 
 * @author Marko
 *
 */
public class ParamGetAction implements IFunctionAction{
	
	private RequestContext requestContext;
	
	
	/**
	 * Constructor. It takes reference to the request context so it
	 * can access the parameters.
	 * 
	 * @param requestContext
	 */
	public ParamGetAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	@Override
	public void performAction(Stack<Object> tempStack) {
		String defaultValue = (String)tempStack.pop();
		String name = ((String) tempStack.pop()).replace("\"", "");
		
		String parameterValue = requestContext.getParameter(name);
		tempStack.push(parameterValue == null ? defaultValue : parameterValue);
	}

}
