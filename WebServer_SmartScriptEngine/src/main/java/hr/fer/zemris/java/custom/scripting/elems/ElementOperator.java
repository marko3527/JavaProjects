package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Class that represents operator element in parsing a document. It has one 
 * only read property.
 * 
 * @author Marko
 *
 */
public class ElementOperator extends Element{
	
	
	private String symbol;
	
	
	/**
	 * Constructor.
	 * 
	 * @param symbol {@code String} value of symbol
	 * @throws NullPointerException if argument symbol is null
	 */
	public ElementOperator(String symbol) {
		if(symbol == null) {
			throw new NullPointerException("Can't accept 'null' reference!");
		}
		this.symbol = symbol;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code String} value of symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol.trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementOperator)) {
			return false;
		}
		ElementOperator other = (ElementOperator) obj;
		if(this.symbol.equals(other.getSymbol())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}

}
