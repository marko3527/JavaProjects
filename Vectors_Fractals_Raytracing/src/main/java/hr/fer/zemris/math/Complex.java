package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents unmodifiable complex numbers and
 * provides method for math calculations on complex 
 * numbers.
 * 
 * @author Marko
 *
 */
public class Complex {
	
	private double realPart;
	private double imaginaryPart;
	
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	
	
	/**
	 * Default constructor.
	 */
	public Complex() {
		this.realPart = 0;
		this.imaginaryPart = 0;
	}
	
	
	/**
	 * Constructor.
	 * 
	 * @param realPart
	 * @param imaginaryPart
	 */
	public Complex(double realPart, double imaginaryPart) {
		this.realPart = realPart;
		this.imaginaryPart = imaginaryPart;
	}
	
	
	/**
	 * Method that calculates the module of the complex number.
	 * 
	 * @return {@code double} module of the complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
	}
	
	
	/**
	 * Method that calculates the new complex number that is result
	 * of multiplying the two complex numbers.
	 * 
	 * @param c {@code Complex} complex number to be multiplied by
	 * @return {@code Complex} multiplied complex number
	 * @throws NullPointerException if the provided complex number is null reference
	 */
	public Complex multiply(Complex c) {
		if(c == null) {
			throw new NullPointerException("You privded complex number with 'null' reference! ");
		}
		double realPart = this.realPart * c.getRealPart() + 
						 (this.imaginaryPart * c.getImaginaryPart()) * -1;
		
		double imaginaryPart = this.imaginaryPart * c.getRealPart() +
							   this.realPart * c.getImaginaryPart();
		
		return new Complex(realPart, imaginaryPart);
	}
	
	
	/**
	 * Calculates a division of two complex numbers.
	 * 
	 * @param c {@code Complex} number method is dividing with
	 * @return {@code Complex} new complex number that is result of division
	 * @throws NullPointerException if the number to divide by is null
	 * @throws IllegalArgumentException if the number to divide by is zero
	 */
	public Complex divide(Complex c) {
		if(c == null) {
			throw new NullPointerException("You privded complex number with 'null' reference! ");
		}
		
		double realPart;
		double imaginaryPart;
		
		if(c.imaginaryPart == 0) {
			if(c.realPart == 0) {
				throw new IllegalArgumentException("Can't divide by zero!");
			}
			realPart = this.realPart / c.getRealPart();
			imaginaryPart = this.imaginaryPart / c.getRealPart();
			return new Complex(realPart, imaginaryPart);
		}
		else {
			Complex complexConjugate = new Complex(c.realPart, c.imaginaryPart * -1);
			Complex divisor = c.multiply(complexConjugate);
			Complex dividend = multiply(complexConjugate);
			return dividend.divide(divisor);
		}
		
	}
	
	
	/**
	 * It performs an operation of adding two complex numbers by adding their real parts
	 * and their imaginary parts.
	 * 
	 * @param c {@code Complex} number that should be added 
	 * @return {@code Complex} result of adding two complex numbers
	 * @throws NullPointerException if number to add is null
	 */
	public Complex add(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't add null reference to complex number!");
		}
		return new Complex(realPart + c.getRealPart(), imaginaryPart + c.getImaginaryPart());
	}
	
	/**
	 * It performs an operation of adding two complex numbers by adding their real parts
	 * and their imaginary parts.
	 * 
	 * @param c {@code Complex} number that should be added 
	 * @return {@code Complex} result of adding two complex numbers
	 * @throws NullPointerException if number to add is null
	 */
	public Complex sub(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't add null reference to complex number!");
		}
		return new Complex(realPart - c.getRealPart(), imaginaryPart - c.getImaginaryPart());
	}
	
	
	/**
	 * Method that returns negated complex number. 
	 * 
	 * @return {@code Complex} negated complex number
	 */
	public Complex negate() {
		return new Complex(-realPart, -imaginaryPart);
	}
	
	
	/**
	 * It takes a number and raises to the power of n.
	 * 
	 * @param n {@code int} the power to raise a complex number on
	 * @return {@code Complex} complex number raised to the power of n
	 * @throws IllegalArgumentException if n is smaller than zero
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("I can't calculate power of complex number to the negative!");
		}
		double newAngle = Math.toDegrees(getAngle()) * n;
		double newModule = Math.pow(module(), n);
		return new Complex(
			newModule * Math.cos(Math.toRadians(newAngle)), newModule * Math.sin(Math.toRadians(newAngle))
		);
		
	}
	
	/**
	 * It calculates nth root of complex number.
	 * 
	 * @param n {@code int} the nth root of number
	 * @return {@code List} n roots of complex numbers
	 * @throws IllegalArgumentException if n is smaller or equal to zero
	 */
	public List<Complex> root(int n){
		if(n <= 0) {
			throw new IllegalArgumentException("I can't calculate root of complex number to the negative!");
		}
		List<Complex> roots = new LinkedList<>();
		double newModule = Math.pow(module(), 1./n);
		double angle = Math.toDegrees(getAngle());
		
		for(int k = 0; k < n; k++) {
			double rootAngle = (angle + 2*k*180)/n;
			roots.add(new Complex(
				newModule * Math.cos(rootAngle), newModule * Math.sin(rootAngle)
			));
		}
		return roots;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("(%.2f , " ,realPart));
		if(imaginaryPart < 0) {
			sb.append(String.format("-i%.2f", Math.abs(imaginaryPart)));
		}
		else {
			sb.append(String.format("i%.2f", imaginaryPart));
		}
		sb.append(")");
		return sb.toString();
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} imaginary part
	 */
	public double getImaginaryPart() {
		return imaginaryPart;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} real part
	 */
	public double getRealPart() {
		return realPart;
	}
	
	
	/**
	 * Method that calculates the angle of the complex number.
	 * 
	 * @return {@code double} angle in radians
	 */
	private double getAngle() {
		double angle = Math.atan2(imaginaryPart,realPart);
		return angle;
	}
	

}
