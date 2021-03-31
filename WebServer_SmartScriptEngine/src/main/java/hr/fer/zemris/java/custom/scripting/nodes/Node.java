package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all nodes in parsing a document. This class holds nodes of the document 
 * as children so we can represent a original document based on a document tree.
 * 
 * @author Marko
 *
 */
public class Node {
	
	private List<Node> children;
	
	
	/**
	 * Method that adds nodes to the graph representation.If this method is called 
	 * first time it instantiates collection of children.
	 * 
	 * @param child {@code Node} new node that should be added.
	 */
	public void addChildNode(Node child) {
		if(children == null) {
			children = new ArrayList<Node>();
		}
		children.add(child);
	}
	
	
	/**
	 * Method that counts the number of children in collection.
	 * 
	 * @return {@code int} number of children
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	
	/**
	 * Method that returns a Node at given index.
	 * 
	 * @param index {@code int} of the object that should be returned
	 * @return {@code Node} an object that is at given index in collection
	 * @throws IndexOutOfBoundsException if the index is not within bounds
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException{
		return (Node) children.get(index);
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(children != null && numberOfChildren() > 0) {
			children.forEach(value -> {
				sb.append(value.toString());
			});
		}
		return sb.toString();
	}
	
	
	/**
	 * Method that calls the appropriate visitor method with this context
	 * 
	 * @param visitor
	 */
	public void accept(INodeVisitor visitor) {
		
	}

}
