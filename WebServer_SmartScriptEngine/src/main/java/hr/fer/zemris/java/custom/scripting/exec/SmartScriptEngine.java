package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.functions.DecFMTAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.DupAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.IFunctionAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamDelAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamGetAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamSetAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.ParamGetAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.SetMimeTypeAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.SinAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.SwapAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamDelAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamGetAction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamSetAction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Engine for Smart Script language. It is based on the visitor 
 * strategy. Documents that can be run by this engine 
 * must be parsable with SmartScriptParser.
 * 
 * @author Marko
 *
 */
public class SmartScriptEngine {
	
	
	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();

	
	private Map<String, IFunctionAction> mapOfAction = new HashMap<>();
	
	
	/**
	 * Visitor that supports four method because in the 
	 * smart script language we support 4 nodes, text node, 
	 * for loop node, echo node and document node. Document
	 * node root node and it represents the collection of all other
	 * nodes found by SmartScriptParser in smart script document
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Writing of the text node did not succed!");
			}
		}
		
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper startingValue = new ValueWrapper(node.getStartExpression().asText());
			String iterationVar = node.getVariable().asText();
			Integer endValue = Integer.parseInt(node.getEndExpression().asText());
			Integer incValue = Integer.parseInt(node.getStepExpression().asText());
			
			multistack.push(iterationVar, startingValue);
			
			while(multistack.peek(iterationVar).numCompare(endValue) <= 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					Node child = node.getChild(i);
					child.accept(visitor);
				}
				multistack.peek(iterationVar).add(incValue);
			}
			
		}
		
		
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();
			Element[] elements = node.getElements();
			
			for(int i = 0; i < elements.length; i++) {
				Element element = elements[i];
				
				if(element instanceof ElementString || element instanceof ElementConstantDouble
				  || element instanceof ElementConstantInteger) {
					tempStack.push(element.asText());
				}
				else if(element instanceof ElementVariable) {
					ValueWrapper currentVarValue = multistack.peek(element.asText());
					tempStack.push(currentVarValue.getValue());
					
				}
				else if(element instanceof ElementOperator) {
					Object secondArg = ("" + tempStack.pop()).replace("\"", "");
					ValueWrapper firstValue = new ValueWrapper(tempStack.pop());
					
					
					switch (element.asText()) {
						case "+": {
							firstValue.add(secondArg);
							tempStack.push(firstValue.getValue());
							break;
						}
						case "-": {
							firstValue.subtract(secondArg);
							tempStack.push(firstValue.getValue());
							break;
						}
						case "*": {
							firstValue.multiply(secondArg);
							tempStack.push(firstValue.getValue());
							break;
						}
						case "/": {
							firstValue.divide(secondArg);
							tempStack.push(firstValue.getValue());
						}
					}
				}
				
				else if(element instanceof ElementFunction) {
					String functionName = element.asText().replace("@", "");
					mapOfAction.get(functionName).performAction(tempStack);
				}
			}
			
			for(int i = 0; i < tempStack.size(); i++) {
				try {
					requestContext.write(("" + tempStack.get(i)).replace("\"",""));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};
	
	
	/**
	 * Constructor. It takes reference to parsed nodes of the smart
	 * script document and takes reference to the request context to be able
	 * to use parameters and to write to output stream. It also initializes
	 * the action that are support to use in echo node.
	 * 
	 * @param documentNode
	 * @param requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		
		mapOfAction.put("sin", new SinAction());
		mapOfAction.put("decfmt", new DecFMTAction());
		mapOfAction.put("dup", new DupAction());
		mapOfAction.put("swap", new SwapAction());
		mapOfAction.put("setMimeType", new SetMimeTypeAction(requestContext));
		mapOfAction.put("paramGet", new ParamGetAction(requestContext));
		mapOfAction.put("pparamGet", new PParamGetAction(requestContext));
		mapOfAction.put("pparamSet", new PParamSetAction(requestContext));
		mapOfAction.put("pparamDel", new PParamDelAction(requestContext));
		mapOfAction.put("tparamGet", new TParamGetAction(requestContext));
		mapOfAction.put("tparamSet", new TParamSetAction(requestContext));
		mapOfAction.put("tparamDel", new TParamDelAction(requestContext));
	}
	
	
	/**
	 * Execution of the program.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
}
