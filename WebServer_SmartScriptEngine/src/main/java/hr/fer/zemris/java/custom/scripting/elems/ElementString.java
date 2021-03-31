package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Class that represents an element under quotations during parsing a document.
 * It has one only read property.
 * 
 * @author Marko
 *
 */
public class ElementString extends Element{

	
	private String value;
	
	/**
	 * Constructor.
	 * 
	 * @param value {@code String} value of word
	 * @throws NullPointerException if argument value is null
	 */
	public ElementString(String value) {
		if(value == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.value = value;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} value of word
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return value.trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementString)) {
			return false;
		}
		ElementString other = (ElementString) obj;
		if(this.value.equals(other.getValue())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
