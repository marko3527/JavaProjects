package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**
 * Demo that shows the usage of polynomial methods.
 *
 * @author Marko
 *
 */
public class ComplexPolynomialDemo {
	
	
	public static void main(String[] args) {
		 
		ComplexPolynomial polynomialThree = new ComplexPolynomial(
			Complex.ONE, new Complex(1, 1), new Complex(2,2)
		);
		
		ComplexPolynomial polynomialTwo = new ComplexPolynomial(
			new Complex(2,0), new Complex(2, 1)
		);
		
		ComplexPolynomial multipliedPolynomial = polynomialThree.multiply(polynomialTwo);
		ComplexPolynomial derivedPolynomial = multipliedPolynomial.derive();
		
		System.out.println(multipliedPolynomial);
		System.out.println(derivedPolynomial);
		
		System.out.println();

		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
			new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
		);
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		
		
	}

}
