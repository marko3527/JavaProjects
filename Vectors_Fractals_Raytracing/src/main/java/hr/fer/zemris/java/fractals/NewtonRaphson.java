package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that offers one method for calculating the fractals with newton
 * raphson iteration based method.
 * 
 * @author Marko
 *
 */
public class NewtonRaphson {
	
	private ComplexRootedPolynomial rootedPolynomial;
	private ComplexPolynomial polynomial;
	private double rootThreshold = 0.002;
	private double convergenceThreshold = 0.001;
	
	/**
	 * Constructor.
	 * 
	 * @param polynomial {@code ComplexRootedPolynomial} function of fractal
	 * @throws NullPointerException if argument is null
	 */
	public NewtonRaphson(ComplexRootedPolynomial rootedPolynomial) {
		if(rootedPolynomial == null) {
			throw new NullPointerException("You provided null reference!");
		}
		this.rootedPolynomial = rootedPolynomial;
		this.polynomial = this.rootedPolynomial.toComplexPolynom();
	}
	
	
	/**
	 * Populates the data with calculations for complex numbers that
	 * range from xMin to xMax and yMin to yMax.
	 * 
	 * @param reMin
	 * @param reMax
	 * @param imMin
	 * @param imMax
	 * @param width
	 * @param height
	 * @param yMin
	 * @param yMax
	 * @param maxIteration
	 * @param data
	 * @param cancel
	 * @param offset
	 */
	public  void calculate(double reMin, double reMax, double imMin, double imMax, int width,
						  int height, int yMin, int yMax, int maxIteration, short[] data,
						  AtomicBoolean cancel, int offset) {
		
		for(int y = yMin; y < yMax; y++) {
			for(int x = 0; x < width; x++) {
				
				Complex c = mapToPlain(x, y, width, height, reMin, reMax,imMin, imMax);
				Complex zn = c;
				int iteration = 0;
				
				while(iteration < maxIteration) {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.derive().apply(zn);
					Complex znOld = zn;
					Complex fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					double mod = znOld.sub(zn).module();
					if(mod <= convergenceThreshold) {
						break;
					}
					iteration++;
				}
				
				int index = rootedPolynomial.indexOfClosestRootFor(zn, rootThreshold);
				data[offset++] = (short) (index + 1);
			}
		}
		
	}
	
	
	/**
	 * Method that calculates the complex number in a given restricted plain.
	 * That number will be further tested for convergation.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param reMin
	 * @param reMax
	 * @param imMin
	 * @param imMax
	 * @return {@code Complex}
	 */
	private static Complex mapToPlain(int x, int y, int width, int height, double reMin,
									  double reMax, double imMin, double imMax) {
		double realPart = ((double)x/(width - 1))*(reMax - reMin) + reMin;
		double imaginaryPart = ((double)(height - 1 - y)/(height - 1))*(imMax - imMin) + imMin;
		
		return new Complex(realPart, imaginaryPart);
	}

}
