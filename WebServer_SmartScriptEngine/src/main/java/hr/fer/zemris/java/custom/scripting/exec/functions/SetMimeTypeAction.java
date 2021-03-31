package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Action that takes top value from temporary stack and
 * calls the requestContext.setMimeType() with that value. It 
 * does not produce any result.
 * 
 * @author Marko
 *
 */
public class SetMimeTypeAction implements IFunctionAction {
	
	private RequestContext requestContext;
	
	
	/**
	 * Constructor.
	 * 
	 * @param requestContext
	 */
	public SetMimeTypeAction(RequestContext requestContext) {
		if(requestContext != null) {
			this.requestContext = requestContext;
		}
	}

	
	@Override
	public void performAction(Stack<Object> tempStack) {
		String mime = (String)tempStack.pop();
		if(mime != null) {
			requestContext.setMimeType(mime);
		}
	}

}
