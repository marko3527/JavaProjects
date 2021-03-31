package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class that represents a parser which will parse a document into nodes.
 * 
 * @author Marko
 *
 */
public class SmartScriptParser {
	
	private String documentBody;
	private SmartScriptLexer lexer;
	private ObjectStack stackOfNodes;
	
	
	/**
	 * Constructor.
	 * 
	 * @param documentBody {@code String} input text that should be parsed
	 * @throws NullPointerException if argument documentBody is null 
	 */
	public SmartScriptParser(String documentBody) {
		if(documentBody == null) {
			throw new NullPointerException("Can't accept null reference as argument! ");
		}
		this.documentBody = documentBody;
		this.lexer = new SmartScriptLexer(this.documentBody);
	}
	
	
	/**
	 * Method that returns a document tree represented with nodes
	 * 
	 * @return {@code DocumentNode} node representation of a document
	 * @throws SmartScriptParserException if there is not enough closing tags
	 * 
	 */
	public DocumentNode getDocumentNode(){
		makeDocumentTree();
		if(stackOfNodes.size() == 0 || stackOfNodes.size() > 1) {
			throw new SmartScriptParserException("Not enough closing tags!");
		}
		else {
			return (DocumentNode) stackOfNodes.pop();
		}
	}
	
	
	/**
	 * Method that computes document tree.
	 * @throws SmartScriptParserException if tag name is not valid or if there is too many closing tags
	 */
	private void makeDocumentTree() {
		stackOfNodes = new ObjectStack();

		stackOfNodes.push(new DocumentNode());
		SmartToken token = getNextToken();
		while(lexer.getToken().getType() != SmartTokenType.EOF) {
			token = lexer.getToken();
			
			if(token.getType() == SmartTokenType.WORD || token.getType() == SmartTokenType.SYMBOL ||
			   token.getType() == SmartTokenType.WHITESPACE) {
				Node node = (Node)stackOfNodes.peek();
				node.addChildNode(makeTextNode(token));
			}
			else if(token.getType() == SmartTokenType.TAG_START) {
				token = getNextToken();
				if(token.getType() == SmartTokenType.WORD) {
					if(token.getValue().toString().toUpperCase().equals("FOR")) {
						ForLoopNode forLoop = makeForLoop(getNextToken());
						Node node = (Node)stackOfNodes.peek();
						node.addChildNode(forLoop);
						stackOfNodes.push(forLoop);
					}
					else if(token.getValue().toString().toUpperCase().equals("END")) {
						token = getNextToken();
						if(stackOfNodes.size() == 0) {
							throw new SmartScriptParserException("Too many closing tags!");
						}
						stackOfNodes.pop();
					}
					else {
						throw new SmartScriptParserException("Not valid tag name");
					}
				}
				else if(token.getType() == SmartTokenType.SYMBOL && 
						token.getValue().equals('=')) {
					EchoNode echoNode = makeEchoNode(getNextToken());
					Node node = (Node)stackOfNodes.peek();
					node.addChildNode(echoNode);
				}
				else {
					throw new SmartScriptParserException("Not valid tag name");
				}
			}
			else {
				token = getNextToken();
			}
			
		}
	}
	
	
	/**
	 * Method that computes echo node, the node that should print something.
	 * 
	 * @param token {@code SmartToken} currentToken
	 * @return {@code EchoNode} new EchoNode that consist of elements.
	 * @throws SmartScriptParserException if function is not properly assigned
	 */
	public EchoNode makeEchoNode(SmartToken token) {
		LinkedListIndexedCollection elements = new LinkedListIndexedCollection();
		while(token.getType() != SmartTokenType.EOF && token.getType() != SmartTokenType.TAG_END) {
			if(token.getType() == SmartTokenType.NUMBER) {
				Element number = new Element();
				if(token.getValue().toString().matches("[+-]?[0-9]+\\.0")){
					number = new ElementConstantInteger(Double.valueOf((double)token.getValue()).intValue());
				}
				else {
					number = new ElementConstantDouble((double)token.getValue());
				}
				elements.add(number);
				token = getNextToken();
			}
			else if(token.getType() == SmartTokenType.SYMBOL && 
					token.getValue().toString().equals("@")) {
				SmartToken functionToken = getNextToken();
				
				ElementFunction element = new ElementFunction(functionToken.getValue().toString());
				elements.add(element);
				token = getNextToken();
			}
			else if(token.getType() == SmartTokenType.SYMBOL &&
					token.getValue().toString().matches("[+-/*^ ]")) {
				elements.add(new ElementOperator(token.getValue().toString()));
				token = getNextToken();
			}
			else if(token.getType() == SmartTokenType.STRING_WORD) {
				elements.add(new ElementString(token.getValue().toString()));
				token = getNextToken();
			}
			else if(token.getType() == SmartTokenType.WORD) {
				elements.add(new ElementVariable(token.getValue().toString()));
				token = getNextToken();
			}
			if(token.getType() == SmartTokenType.WHITESPACE) {
				token = getNextToken();
			}
		}
		Element[] elementsArray = new Element[elements.size()];
		for(int i = 0; i < elements.size(); i++) {
			elementsArray[i] = (Element)elements.get(i);
		}
		return new EchoNode(elementsArray);
	}
	
	
	/**
	 * Method that contructs for loop node.
	 * 
	 * @param token {@code SmartToken} current token in lexer
	 * @return {@code ForLoopNode} constructed for loop node
	 * @throws SmartScriptParserException if variable name is not correct!
	 */
	private ForLoopNode makeForLoop(SmartToken token) {
		int i = 0;
		ElementVariable variable = new ElementVariable("");
		Element startExpression = new Element();
		Element endExpression = new Element();
		Element stepExpression = new Element();
		
		if(!checkForEnd(token, i)) {
			String variableName = (String) token.getValue();
			
			variable = new ElementVariable(variableName);
			i++;
		}
		token = getNextToken();
		if(!checkForEnd(token, i)) {
			startExpression = determineExpression(token);
			i++;
		}
		token = getNextToken();
		if(!checkForEnd(token, i)) {
			endExpression = determineExpression(token);
			i++;
		}
		token = getNextToken();
		if(checkForEnd(token, i)) {
			return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		}
		else if(!checkForEnd(token, i)) {
			stepExpression = determineExpression(token);
		}
		else {
			checkForEnd(token, i);
		}
		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
	}
	
	
	/**
	 * Method that determines which token is next and makes an expression out of it.
	 * 
	 * @param token {@code SmartToken} lexer token
	 * @return {@code Element} expression for for loop
	 */
	private Element determineExpression(SmartToken token) {
		Element expression = new Element();
		if(token.getType() == SmartTokenType.SYMBOL && token.getValue() == "@") {
			expression = new ElementFunction(token.getValue().toString());
		}
		else if(token.getType() == SmartTokenType.NUMBER) {
			if(token.getValue().toString().matches("[+-]?[0-9]+\\.0")){
				expression = new ElementConstantInteger(Double.valueOf((double)token.getValue()).intValue());
			}
			else {
				expression = new ElementConstantDouble((double)token.getValue());
			}
		}
		else if(token.getType() == SmartTokenType.STRING_WORD) {
			expression = new ElementString(token.getValue().toString());
		}
		return expression;
	}
	
	
	/**
	 * Check whether the for loop should end.
	 * 
	 * @param token {@code SmartToken} lexer token
	 * @param i {@code int} that helps determine if user has put less or more arguments in for loop
	 * @return {@code true} if for loop should end
	 * 		   {@code false} if for loop shouldn't end
	 * @throws SmartScriptParserException if there is not proper number of arguments for for loop
	 */
	private boolean checkForEnd(SmartToken token, int i) {
		if(token.getType() == SmartTokenType.EOF ||
		   token.getType() == SmartTokenType.TAG_END) {
			if(i != 3 && i != 4) {
				throw new SmartScriptParserException("There is too many or not enough properties in for loop!");
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * Method that makes text node.
	 * 
	 * 
	 * @param token {@code SmartToken} current token from lexer
	 * @return {@code TextNode}
	 */
	private TextNode makeTextNode(SmartToken token) {
		String text = "";
		while(token.getType() != SmartTokenType.EOF && token.getType() != SmartTokenType.TAG_START) {
			if(token.getType() == SmartTokenType.WHITESPACE || token.getType() == SmartTokenType.SYMBOL) {
				text += token.getValue();
				text = text.replace(" " + token.getValue(), "" + token.getValue());
			}
			else {
				text += token.getValue();
			}
			 
			token = getNextToken();
		}
		return new TextNode(text);
	}
	
	private SmartToken getNextToken() {
		SmartToken token = new SmartToken(null, null);
		try {
			token = lexer.nextToken();
		} catch (SmartLexerException e) {
			throw new SmartScriptParserException(e.getMessage());
		}
		return token;
	}
	
	
	
}
