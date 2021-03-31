package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that stores a value into request context's persistant
 * parameters.
 * 
 * @author Marko
 *
 */
public class PParamSetAction implements IFunctionAction{
	
	private RequestContext requestContext;
	
	/**
	 * Constructor.
	 * 
	 * @param requestContext
	 */
	public PParamSetAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	@Override
	public void performAction(Stack<Object> tempStack) {
		String name = ((String)tempStack.pop()).replace("\"", "");
		String value = "" + tempStack.pop();
		requestContext.setPersistentParameter(name, value);
	}

}
