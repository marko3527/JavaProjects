package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that stores a value into request context's temporary
 * parameters.
 * 
 * @author Marko
 *
 */
public class TParamSetAction implements IFunctionAction {
	
	
	private RequestContext requestContext;
	
	
	/**
	 * Constrcutor.
	 * 
	 * @param requestContext
	 */
	public TParamSetAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	
	@Override
	public void performAction(Stack<Object> tempStack) {
		String name = ((String)tempStack.pop()).replace("\"", "");
		String value = "" + tempStack.pop();
		requestContext.setTemporaryParameter(name, value);
	}

}
