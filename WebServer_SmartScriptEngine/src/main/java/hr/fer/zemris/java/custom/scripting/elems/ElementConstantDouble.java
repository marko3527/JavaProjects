package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents double number element during parsing a document. It has one
 * only read property.
 * 
 * @author Marko
 *
 */
public class ElementConstantDouble extends Element{
	
	
	private double value;
	
	
	/**
	 * Constructor.
	 * 
	 * @param value {@code double} value of number
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} value of number
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.valueOf(value).toString().trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementConstantDouble)) {
			return false;
		}
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if(this.value == other.value){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Double.valueOf(value).hashCode();
	}

}
