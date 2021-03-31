package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node that represents a command which generates some textual
 * output dynamically. It inherits class Node and define one more only read
 * property.
 * 
 * @author Marko
 *
 */
public class EchoNode extends Node{
	
	private Element[] elements;
	
	
	/**
	 * Constructor.
	 * 
	 * @param elements {@code Element[]} 
	 * @throws NullPointerException if argument elements is null
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.elements = elements;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code Element[]}
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" {$ =");
		for(Element element : elements) {
			sb.append(" " + element.asText());
		}
		sb.append(" $}");
		return sb.toString() + super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EchoNode)) {
			return false;
		}
		EchoNode other = (EchoNode) obj;
		for(int i = 0; i < elements.length; i++) {
			if(!other.elements[i].asText().equals(elements[i].asText())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		for(Element element : elements) {
			hash += element.hashCode();
		}
		return hash;
	}
	
	
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

}
