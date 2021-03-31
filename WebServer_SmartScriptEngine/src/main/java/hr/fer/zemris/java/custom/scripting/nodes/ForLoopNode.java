package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing single for-loop construct. It inherits 
 * class Node and defines four more read only properties.
 * 
 * @author Marko
 *
 */
public class ForLoopNode extends Node{
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	
	/**
	 * Contructor.
	 * 
	 * @param variable {@code ElementVariable} variable in for loop
	 * @param startExpression {@code Element} starting expression, for example variable definition
	 * @param endExpression {@code Element} expression that defines when the loop should be stopped
	 * @param step {@code Element} the step expression of for loop
	 * 
	 * @throws NullPointerException if any of the arguments, except step, are null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element step) {
		if(variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("Can't accept 'null' reference");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = step;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code Element} stopping expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code Element} starting expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code Element} step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code ElementVariable} variable at the start of for loop
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String text = "{$ FOR "+ variable.asText() + " " + startExpression.asText() + " " +
				endExpression.asText() + " " + stepExpression.asText() + " $}";
		sb.append(text + super.toString() + "{$ END $}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ForLoopNode)) {
			return false;
		}
		ForLoopNode other = (ForLoopNode) obj;
		if(this.variable.equals(other.variable) && 
		   this.startExpression.equals(startExpression) &&
		   this.endExpression.equals(endExpression) && 
		   this.stepExpression.equals(stepExpression)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return variable.hashCode() + startExpression.hashCode() + 
			   endExpression.hashCode() + stepExpression.hashCode();
	}

}
