package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents variable element during parsing the document. It has one only
 * read property.
 * 
 * @author Marko
 *
 */
public class ElementVariable extends Element{
	
	private String name;
	
	/**
	 * Contructor.
	 * 
	 * @param name {@code String} name that this element should have
	 * @throws NullPointerException if argument name is null
	 */
	public ElementVariable(String name) {
		if(name == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.name = name;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} value of name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name.trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementVariable)) {
			return false;
		}
		ElementVariable other = (ElementVariable) obj;
		if(this.name.equals(other.getName())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

}
