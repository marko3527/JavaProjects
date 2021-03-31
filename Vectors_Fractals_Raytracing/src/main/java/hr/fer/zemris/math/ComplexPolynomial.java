package hr.fer.zemris.math;

/**
 * Class that models the polynomial over the complex numbers.
 * User has to provide factors from z0 to zn
 * 
 * @author Marko
 *
 */
public class ComplexPolynomial {
	private Complex[] factors;
	
	
	/**
	 * Constructor.
	 * 
	 * @param factors {@code Complex[]} factors that stand in front 
	 * 				  of the corresponding power of z. The first factor is
	 * 				  for z0, next for z1 and so on
	 * @throws NullPointerException if argument is null
	 */
	public ComplexPolynomial(Complex ... factors) {
		if(factors == null) {
			throw new NullPointerException("You provided null reference");
		}
		for(Complex factor : factors) {
			if(factor == null) {
				throw new NullPointerException("You provided null reference");
			}
		}
		
		this.factors = factors;
	}
	
	
	/**
	 * Method that returns the the number of order of the modeled
	 * polynomial. 
	 * 
	 * @return {@code short} number of order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	
	/**
	 * Method for multiplying two polynomials.
	 * 
	 * @param p {@code ComplexPolynomial} polynomial to multiply with
	 * @return {@code ComplexPolynomial} new mutliplied polynomial
	 * @throws NullPointerException if argument is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if(p == null) {
			throw new NullPointerException("You provided null reference in method 'multiply'!");
		}
		int newOrder = order() + p.order();
		Complex[] newFactors = new Complex[newOrder + 1];
		
		for(int i = 0; i < factors.length; i++) {
			for(int j = 0; j < p.factors.length; j++) {
				Complex newFactor = newFactors[i + j];
				if(newFactor == null) {
					newFactors[i + j] = factors[i].multiply(p.factors[j]);
				}
				else {
					newFactors[i + j] = newFactor.add(factors[i].multiply(p.factors[j]));
				}
			}
		}
		
		return new ComplexPolynomial(newFactors);
		
	}
	
	
	/**
	 * Method that calculates the deriavation of the given 
	 * polynomial.
	 * 
	 * @return {@code ComplexPolynomial} derived polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[order()];
		
		for(int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	
	/**
	 * Computes polynomial value at given point z.
	 * It goes through factors field and it raises the z number 
	 * to the power of index of that factor and then multiplying 
	 * that number with factor at index and finally adding
	 * all together
	 * 
	 * @param z {@code Complex} given point
	 * @return {@code Complex} calculated complex number in given point
	 * @throws NullPointerException if argument is null
	 */
	public Complex apply(Complex z) {
		if(z == null) {
			throw new NullPointerException("You provided null reference in method 'apply'!");
		}
		Complex result = new Complex();
		
		for(int i = 1; i < factors.length; i++) {
			Complex help = z.power(i);
			result = result.add(factors[i].multiply(help));
		}
		
		return result.add(factors[0]);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = factors.length - 1; i > 0; i--) {
			sb.append("+" + factors[i] + "*" + "z^" + i);
		}
		sb.append("+" + factors[0]);
		
		return sb.toString().replaceFirst("\\+", "");
	}

}
