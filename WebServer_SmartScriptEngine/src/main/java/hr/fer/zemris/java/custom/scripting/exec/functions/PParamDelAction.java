package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that deletes the values under the key 'name' 
 * from persistant parameters.
 * 
 * @author Marko
 *
 */
public class PParamDelAction implements IFunctionAction{
	
	private RequestContext requestContext;
	
	/**
	 * Constructor. Takes the reference to request context
	 * so it can access the persistant parameters.
	 * 
	 * @param requestContext
	 */
	public PParamDelAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	@Override
	public void performAction(Stack<Object> tempStack) {
		String name = (String)tempStack.pop();
		requestContext.removePersistentParameter(name);
	}

}
