package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that provides method for calculating the polynomial on
 * the complex number that looks like this :
 * f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)
 * User has to provided constant z0 and all of the roots annotated with zn
 * 
 * @author Marko
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;
	
	
	/**
	 * Constructor.
	 * 
	 * @param constant {@code Complex} constant complex number that is often reffered
	 * 				   to as z0
	 * @param roots {@code Complex[]} n number of roots, reffered as zn
	 * @throws NullPointerException if any argument is null
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if(constant == null || roots == null) {
			throw new NullPointerException("You provided null reference!");
		}
		for(Complex root : roots) {
			if(root == null) {
				throw new NullPointerException("You provided null reference");
			}
		}
		
		this.constant = constant;
		this.roots = roots;
	}
	
	
	/**
	 * Method that calculates polynomial value at given point z.
	 * 
	 * @param z {@code Complex} point to calculate the polynomial value
	 * @return {@code Complex} calculated value
	 * @throws NullPointerException if the argument is null
	 */
	public Complex apply(Complex z) {
		if(z == null) {
			throw new NullPointerException("You provided null reference in method"
					+ "'apply'!");
		}
		Complex result = constant;
		
		for(Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}
	
	
	/**
	 * Method that converts the representation of polynomial 
	 * with roots to representation of polynomial with powers.
	 * 
	 * @return {@code ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial constantPolynomial = new ComplexPolynomial(constant);
		List<ComplexPolynomial> polynomials = new LinkedList<>();
		
		for(Complex root : roots) {
			polynomials.add(new ComplexPolynomial(root.negate(), Complex.ONE));
		}
		
		ComplexPolynomial result = polynomials.get(0);
		for(int i = 1; i < polynomials.size(); i++) {
			result = result.multiply(polynomials.get(i));
		}
		
		return result.multiply(constantPolynomial);
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant);
		
		for(Complex root : roots) {
			sb.append("*(z-" + root + ")");
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Method that returns the index of closest root to the given 
	 * complex number by distance within threshold.
	 * 
	 * @param z {@code Complex} complex number to find the closest root
	 * @param threshold {@code double} threshold
	 * @return {@code int} index of closest root
	 * @throws NullPointerException if argument complex number z is null
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if(z == null) {
			throw new NullPointerException("You provided null reference in method "
					+ "'indexOfClosestRoot'!");
		}
		
		double distance = calculateDistance(roots[0], z);
		double smallestDistance = 0;
		int indexOfSmallest = -1;
		
		if(distance < threshold) {
			smallestDistance = distance;
			indexOfSmallest = 0;
		}
	
		for(int i = 1; i < roots.length; i++) {
			distance = calculateDistance(roots[i], z);
			if(smallestDistance == 0) {
				if(smallestDistance < threshold) {
					smallestDistance = distance;
					indexOfSmallest = i;
				}
			}
			else {
				if(distance <= threshold && distance <= smallestDistance) {
					smallestDistance = distance;
					indexOfSmallest = i;
				}
			}
			
		}
		return indexOfSmallest;
	}
	
	
	/**
	 * Method that calculates the distance between two given 
	 * complex numbers.
	 * 
	 * @param root {@code Complex} first complex number
	 * @param z {@code Complex} second complex number
	 * @return {@code double} distance between two numbers
	 */
	private double calculateDistance(Complex root, Complex z) {
		return Math.sqrt(Math.pow((root.getRealPart() - z.getRealPart()),2) + 
						 Math.pow((root.getImaginaryPart() - z.getImaginaryPart()), 2)
		);
	}

}
