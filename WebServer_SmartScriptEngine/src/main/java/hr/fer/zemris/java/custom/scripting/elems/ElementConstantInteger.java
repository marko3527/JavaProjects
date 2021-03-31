package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents integer number element during parsing a document. It has one
 * only read property.
 * 
 * @author Marko
 *
 */
public class ElementConstantInteger extends Element{
	
	private int value;
	
	/**
	 * Constructor.
	 * 
	 * @param value {@code int} value of number
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code int} value of number
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.valueOf(value).toString().trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementConstantInteger)) {
			return false;
		}
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if(this.value == other.getValue()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(value).hashCode();
	}
	
	

}
