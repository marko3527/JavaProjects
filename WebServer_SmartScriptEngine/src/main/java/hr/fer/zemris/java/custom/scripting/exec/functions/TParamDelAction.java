package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that deletes the values under the key 'name' 
 * from temporary parameters.
 * 
 * @author Marko
 *
 */
public class TParamDelAction implements IFunctionAction{
	
	private RequestContext requestContext;
	
	
	/**
	 * Constructor.
	 * 
	 * @param requestContext
	 */
	public TParamDelAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	
	@Override
	public void performAction(Stack<Object> tempStack) {
		String name = (String)tempStack.pop();
		requestContext.removeTemporaryParameter(name);
	}

}
