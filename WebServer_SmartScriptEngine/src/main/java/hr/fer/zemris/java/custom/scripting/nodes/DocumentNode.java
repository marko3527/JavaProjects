package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node representing an entire document.It is starting node in every document.
 * It inherits Node.
 * 
 * @author Marko
 *
 */
public class DocumentNode extends Node{
	
	/**
	 * {@inheritDoc}
	 * Method will dump out the parsed document in its original state.
	 * 
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	
	/**
	 * {@inheritDoc}
	 * Method will evaluate if the two strings are the same without all the whitespaces.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DocumentNode)) {
			return false;
		}
		DocumentNode node = (DocumentNode) obj;
		if(this.toString().equals(node.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return toString().replaceAll("\\s+", "").hashCode();
	}
	
	
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
}
