package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Class that represents a function element during parsing a document. It has one
 * only read property.
 * 
 * @author Marko
 *
 */
public class ElementFunction extends Element{

	
	private String name;
	
	
	/**
	 * Constructor.
	 * 
	 * @param name {@code String}, value for name of the function
	 * @throws NullPointerException if argument name is null
	 */
	public ElementFunction(String name) {
		if(name == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.name = name;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} name of the function
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return "@" + name.trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementFunction)) {
			return false;
		}
		ElementFunction other = (ElementFunction) obj;
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
