package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node that represents a piece of textual data.
 * It inherits class Node and defines one more only read property.
 * 
 * @author Marko
 *
 */
public class TextNode extends Node{
	
	private String text;
	
	
	/**
	 * Constructor.
	 * 
	 * @param text {@code String} a piece of textual data
	 * @throws NullPointerException if argument text is null
	 */
	public TextNode(String text) {
		if(text == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.text = text;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} a piece of textual data.
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(text.substring(2));
		return sb + super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode)) {
			return false;
		}
		TextNode other = (TextNode)obj;
		if(text.equals(other.text)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return text.hashCode();
	}
	
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
