package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor interface. It defines four different methods
 * for four different nodes of the some programming language
 * Every instance of this visitor interface will have different implementation
 * for function to make when visiting nodes.
 * 
 * @author Marko
 *
 */
public interface INodeVisitor {
	
	
	/**
	 * Method that visits the text node. The visiting
	 * function that it handles is to write the content of 
	 * the text node to the outputStream of the requested
	 * context
	 * 
	 * @param node
	 */
	public void visitTextNode(TextNode node);
	
	
	/**
	 * Method that visits the for loop node. Visiting the for loop 
	 * it remembers the variable over which loop is iterating and is increasing that
	 * value if it smaller or equal to the end expression of the loop.
	 * If the stored satisfies condition then method goes through all
	 * children of the for loop and calls the appropriate visitor method.
	 * 
	 * @param node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	
	/**
	 * Method that visits the echo node. Echo node can have multiple elements.
	 * If the element stored in echo mode is constant then it pushes the value
	 * to the temporary stack. If the element is variable it finds the value of that
	 * variable on the stack and pushes it to temporary stack. If the element is operator
	 * it pops two elements from temporary stack and calculates the binary operation
	 * on those elements and then stores the result back on the temporary stack.
	 * If the element is function it pops the number of arguments needed to perform
	 * the action. After passing through all elements if the temporary stack is
	 * not empty then it prints the content of it reversed. 
	 * 
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);
	
	
	/**
	 * Method that visits the document node. It goes through all the children
	 * and calls the appropriate visitor method over that child.
	 * 
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);

}
