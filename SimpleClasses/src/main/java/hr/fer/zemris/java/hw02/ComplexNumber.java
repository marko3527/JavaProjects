package hr.fer.zemris.java.hw02;


/**
 * Class that represents unmodified complex number and provides 
 * support for all calculations with complex numbers.
 * 
 * @author Marko
 *
 */
public class ComplexNumber {
	
	private double realPart;
	private double imaginaryPart;
	
	/**
	 * Constructor that accepts two {@code} double arguments.
	 * 
	 * @param realPart of the complex number
	 * @param imaginaryPart of the complex number
	 */
	public ComplexNumber(double realPart, double imaginaryPart) {
		this.realPart = realPart;
		this.imaginaryPart = imaginaryPart;
	}
	
	/**
	 * Getter.
	 * @return {@code double} imaginary part of complex number 
	 */
	public double getImaginaryPart() {
		return imaginaryPart;
	}
	
	/**
	 * Getter.
	 * @return {@code double} real part of complex number 
	 */
	public double getRealPart() {
		return realPart;
	}
	
	/**
	 * Calculates the magnitude of the complex number by the formula: 
	 * squareRoot(realPart*realPart + imaginaryPart*imaginaryPart).
	 * 
	 * @return {@code double} magnitude calculated
	 */
	public double getMagnitude() {
		double magnitude = Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
		return magnitude;
	}
	
	/**
	 * Calculates the angle of the complex number by the formula:
	 * atan2(imaginaryPart/realPart).
	 * 
	 * @return {@code double} value of angle in radians
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginaryPart,realPart);
		return angle;
	}
	
	/**
	 * Method that returns a new instance of complex number based just on real part.
	 * 
	 * @param real type {@code double}
	 * @return new instance of {@code ComplexNumber};
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Method that returns a new instance of complex number based just on imaginary part.
	 * 
	 * @param imaginary type {@code double}
	 * @return new instance of {@code ComplexNumber};
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Method that returns a new instance of complex number based on magnitude and angle.
	 * 
	 * @param magnitude of type {@code double}, magnitude of complex number
	 * @param angle of type {@code double}, angle of complex number in radians
	 * @return new instance of {@code ComplexNumber}
	 * @throws IllegalArgumentException if magnitude is smaller than zero 
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0 ) {
			throw new IllegalArgumentException("Magnitude is not right!");
		} 
		double realPart = magnitude*Math.cos(angle);
		double imaginaryPart = magnitude*Math.sin(angle);
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	/**
	 * It takes a line of String and parses it as a new complex number.
	 * 
	 * @param s {@code String} line to parse
	 * @return {@code ComplexNumber} parsed complex number
	 * @throws IllegalArgumentException if input string is not properly provided
	 */
	public static ComplexNumber parse(String s) {
		if(!appropriateInput(s)) {
			throw new NumberFormatException("Input string is not correct(Maybe it has \n"
					+ "2 mathematical signs one after another or maybe is 'i' before number)");
		}
		ComplexNumber parsedNumber = null;
		//if number does not have i it means it is just real part
		if(!s.contains("i")) {
			parsedNumber = fromReal(Double.parseDouble(s));
		}
		//this covers numbers of type like '-31 + 24i' and '31 +  24i and +2i'
		else if(s.contains("+")) {
			s=s.replace("i", "");
			String[] tokens = s.split("\\+");
			if(tokens.length == 3) {
				parsedNumber = new ComplexNumber(Double.parseDouble(tokens[1]), 
	 					 						 Double.parseDouble(tokens[2]));
			}
			else if(!isNumber(tokens[0])) {
				parsedNumber = fromImaginary(Double.parseDouble(s));
			}
			else{
				parsedNumber = new ComplexNumber(Double.parseDouble(tokens[0]), 
												 Double.parseDouble(tokens[1]));
			}
		}
		else if(s.contains("-")) {
			s=s.replace("i", "");
			String[] tokens = s.split("-");
			/*
			 * this covers numbers like '-2-2i', splitting the string by '-' we get 3 elements
			 * ["",2,2i]
			 */
			if(tokens.length == 3) {
				parsedNumber = new ComplexNumber(Double.parseDouble(tokens[1])*(-1), 
										 	     Double.parseDouble(tokens[2])*(-1));
			}
			else {
				/*
				 * this covers numbers like '2-2i', splitting the string by '-' we get 2 elements
				 * [2,2i]
				 */
				if(isNumber(tokens[0])) {
					parsedNumber = new ComplexNumber(Double.parseDouble(tokens[0]),
													 Double.parseDouble(tokens[1])*(-1));
				}
				/*
				 * this covers numbers like '-2i',splitting the string by '-' we get 2 elements
				 * ["",2i]
				 */
				else {
					parsedNumber = fromImaginary(Double.parseDouble(tokens[1])*(-1));
				}
			}
			
		}
		if(!s.contains("+") && !s.contains("-") && s.contains("i")) {
			s = s.replace("i", "");
			parsedNumber = fromImaginary(Double.parseDouble(s));
		}
		return parsedNumber;
	}
	
	
	/**
	 * Method checks if the input string has more than 2 signs.
	 * 
	 * @param s {@code String} 
	 * @return {@code true} if it has just one sign before the number
	 * 		   {@code false} if it has more than 2
	 */
	private static boolean appropriateInput(String s) {
		if(s.contains("i") && s.charAt(s.length() - 1) != 'i') {
			return false;
		}
		for(int i = 0; i < s.length() - 1; i++) {
			if(s.charAt(i) == '+' || s.charAt(i) == '-') {
				if(s.charAt(i + 1) == '+' || s.charAt(i + 1) == '-') {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks if argument is parsable as number
	 * 
	 * @param element {@code String} element to parse
	 * @return {@code true} if it is number
	 * 		   {@code false} if it isn't number
	 */
	private static boolean isNumber(String element) {
		if(element == null) {
			return false;
		}
		else {
			try {
				Double.parseDouble(element);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		
	}
	
	/**
	 * It performs an operation of adding two complex numbers by adding their real parts
	 * and their imaginary parts.
	 * 
	 * @param c {@code ComplexNumber} number that should be added 
	 * @return {@code ComplexNumber} result of adding two complex numbers
	 * @throws NullPointerException if number to add is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("You privded complex number with 'null' reference! ");
		}
		double realPart = this.realPart + c.realPart;
		double imaginaryPart = this.imaginaryPart + c.imaginaryPart;
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	
	/**
	 * It performs an operation of subtracting two complex numbers by adding their real parts
	 * and their imaginary parts.
	 * 
	 * @param c {@code ComplexNumber} number that should be subtracted, type {@code ComplexNumber}
	 * @return {@code ComplexNumber} result of subtracting two complex numbers
	 * @throws NullPointerException if number to subtract is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("You privded complex number with 'null' reference! ");
		}
		double realPart = this.realPart - c.realPart;
		double imaginaryPart = this.imaginaryPart - c.imaginaryPart;
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	/**
	 * Calculates a multiplication of two complex numbers.
	 * 
	 * @param c {@code ComplexNumber} number method is multiplying with
	 * @return {@code ComplexNumber} new complex number that is result of multiplication
	 * @throws NullPointerException if the number to multiply by is null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("You privded complex number with 'null' reference! ");
		}
		double realPart = this.realPart * c.realPart + this.imaginaryPart * c.imaginaryPart *(-1);
		double imaginaryPart = this.realPart * c.imaginaryPart + this.imaginaryPart * c.realPart;
		return new ComplexNumber(realPart,imaginaryPart);
	}
	
	/**
	 * Calculates a division of two complex numbers.
	 * 
	 * @param c {@code ComplexNumber} number method is dividing with
	 * @return {@code ComplexNumber} new complex number that is result of division
	 * @throws NullPointerException if the number to divide by is null
	 * @throws IllegalArgumentException if the number to divide by is zero
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("You provided complex number with 'null' reference! ");
		}
		double real;
		double imaginary;
		if(c.imaginaryPart == 0) {
			if(c.realPart == 0) {
				throw new IllegalArgumentException("Can't divide by zero");
			}
			real = this.realPart / c.realPart;
			imaginary = this.imaginaryPart / c.realPart;
			return new ComplexNumber(real, imaginary);
		}
		else {
			ComplexNumber complexConjNumber = complexConjugate(c);
			ComplexNumber divisor = c.mul(complexConjNumber);
			ComplexNumber dividend = mul(complexConjNumber);
			return dividend.div(divisor);
		}
	}
	
	/**
	 * It takes a number and raises to the power of n.
	 * 
	 * @param n {@code int} the power to raise a complex number on
	 * @return {@code ComplexNumber} complex number raised to the power of n
	 * @throws IllegalArgumentException if n is smaller than zero
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("I can't calculate power of complex number to the negative!");
		}
		double angle = getAngle();
		double magnitude = getMagnitude();
		double newAngle = Math.toDegrees(angle) * n;
		return fromMagnitudeAndAngle(Math.pow(magnitude, n), Math.toRadians(newAngle));
		
	}
	
	/**
	 * It calculates nth root of complex number.
	 * 
	 * @param n {@code int} the nth root of number
	 * @return {@code ComplexNumber[]} n roots of complex numbers
	 * @throws IllegalArgumentException if n is smaller or equal to zero
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("I can't calculate root of complex number to the negative!");
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitude = Math.pow(getMagnitude(), 1./n);
		double angle = Math.toDegrees(getAngle());
		for(int k = 0; k < n; k++) {
			double rootAngle = (angle + 2*k*180)/n;
			roots[k] = fromMagnitudeAndAngle(magnitude, Math.toRadians(rootAngle));
		}
		return roots;
	}
	
	/**
	 * It calculates a complex conjugate of a given complex number
	 * 
	 * @param c {@code ComplexNumber} number to calculate complex conjugate of
	 * @return {@code ComplexNumber} complex conjguate of given number
	 */
	private ComplexNumber complexConjugate(ComplexNumber c) {
		return new ComplexNumber(c.realPart, c.imaginaryPart*(-1));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(realPart != 0) {
			sb.append(String.format("%.3f",realPart));
		}
		if(imaginaryPart != 0) {
			if(sb.length() != 0) {
				String s = imaginaryPart >0 ? " + " : " ";
				sb.append(s + String.format("%.3f", imaginaryPart) + "i");
			}
			else {
				sb.append(String.format("%.3f", imaginaryPart) + "i");
			}
		}
		return sb.toString();
	}
	
	

}
