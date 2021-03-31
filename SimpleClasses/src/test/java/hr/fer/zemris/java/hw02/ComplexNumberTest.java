package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;

public class ComplexNumberTest {

	@Test
	public void contructorTest() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertEquals(3, c.getRealPart());
		assertEquals(4, c.getImaginaryPart());
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(4);
		assertEquals(4, c.getRealPart());
		assertEquals(0, c.getImaginaryPart());
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(4);
		assertEquals(0, c.getRealPart());
		assertEquals(4, c.getImaginaryPart());
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.fromMagnitudeAndAngle(-1, 2*Math.PI);
		});
		double angle = Math.PI/4;
		double magnitude = 1;
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle % Math.PI);
		assertEquals(magnitude*Math.cos(angle), c.getRealPart());
		assertEquals(magnitude*Math.sin(angle), c.getImaginaryPart());
	}
	
	@Test
	public void parseTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.parse("++2i");
		});
		ComplexNumber c = ComplexNumber.parse("2-3i");
		ComplexNumber c1 = ComplexNumber.parse("+2+3i");
		ComplexNumber c2 = ComplexNumber.parse("-2-3i");
		ComplexNumber c3 = ComplexNumber.parse("-2+3i");
		assertEquals(2, c.getRealPart());
		assertEquals(-3, c.getImaginaryPart());
		assertEquals(2, c1.getRealPart());
		assertEquals(3, c1.getImaginaryPart());
		assertEquals(-2, c2.getRealPart());
		assertEquals(-3, c2.getImaginaryPart());
		assertEquals(-2, c3.getRealPart());
		assertEquals(3, c3.getImaginaryPart());
		
		c = ComplexNumber.parse("+2");
		c1 = ComplexNumber.parse("-2");
		c2 = ComplexNumber.parse("-2i");
		c3 = ComplexNumber.parse("2i");
		assertEquals(2,c.getRealPart());
		assertEquals(-2, c1.getRealPart());
		assertEquals(-2, c2.getImaginaryPart());
		assertEquals(2, c3.getImaginaryPart());
		
	}
	
	@Test
	public void getRealTest() {
		ComplexNumber c = ComplexNumber.parse("2-3i");
		assertEquals(2, c.getRealPart());
	}
	
	@Test
	public void getImaginaryTest() {
		ComplexNumber c = ComplexNumber.parse("2-3i");
		assertEquals(-3, c.getImaginaryPart());
	}
	
	@Test 
	public void getMagnitudeTest() {
		ComplexNumber c = ComplexNumber.parse("4-3i");
		assertEquals(5, c.getMagnitude());
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(Math.PI/4, c.getAngle());
	}
	
	@Test
	public void addTest() {
		assertThrows(NullPointerException.class, () -> {
			new ComplexNumber(2,2).add(null);
		});
		ComplexNumber c = ComplexNumber.parse("2-3i");
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("-2-3i");
		ComplexNumber c3 = ComplexNumber.parse("-2+3i");
		ComplexNumber result = c.add(c1);
		assertEquals(4,result.getRealPart());
		assertEquals(0, result.getImaginaryPart());
		result = c.add(c2);
		assertEquals(-6, result.getImaginaryPart());
		assertEquals(0, result.getRealPart());
		result = c.add(c3);
		assertEquals(0, result.getRealPart());
		assertEquals(0, result.getImaginaryPart());
	}
	
	@Test
	public void subTest() {
		assertThrows(NullPointerException.class, () -> {
			new ComplexNumber(2,2).sub(null);
		});
		ComplexNumber c = ComplexNumber.parse("2-3i");
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("-2-3i");
		ComplexNumber c3 = ComplexNumber.parse("2-3i");
		ComplexNumber result = c.sub(c1);
		assertEquals(-6, result.getImaginaryPart());
		assertEquals(0, result.getRealPart());
		result = c.sub(c2);
		assertEquals(4,result.getRealPart());
		assertEquals(0,result.getImaginaryPart());
		result = c.sub(c3);
		assertEquals(0, result.getRealPart());
		assertEquals(0,result.getImaginaryPart());

	}
	
	@Test
	public void mulTest() {
		assertThrows(NullPointerException.class, () -> {
			new ComplexNumber(2,2).mul(null);
		});
		ComplexNumber c = new ComplexNumber(2,3);
		ComplexNumber c1 = new ComplexNumber(1,1);
		c = c.mul(c1);
		assertEquals(-1, c.getRealPart());
		assertEquals(5, c.getImaginaryPart());
	}
	
	@Test
	public void divTest() {
		assertThrows(NullPointerException.class, () -> {
			new ComplexNumber(2,2).div(null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new ComplexNumber(2,2).div(ComplexNumber.fromReal(0));
		});
		ComplexNumber c = new ComplexNumber(2,3);
		ComplexNumber c1 = new ComplexNumber(1,1);
		c = c.div(c1);
		assertEquals(5/2., c.getRealPart());
		assertEquals(1/2., c.getImaginaryPart());
	}
	
	@Test 
	public void powerTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ComplexNumber(2, 1).power(-1);
		});
		ComplexNumber c = new ComplexNumber(2,3);
		c = c.power(2);
		assertEquals(-5, Math.round(c.getRealPart()));
		assertEquals(12, Math.round(c.getImaginaryPart()));
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c = new ComplexNumber(2,1);
		assertThrows(IllegalArgumentException.class, () -> {
			new ComplexNumber(2, 1).root(-1);
		});
		int nThRoot = 2;
		ComplexNumber[] roots = new ComplexNumber[nThRoot];
		roots = c.root(nThRoot);
	
		assertEquals("1.455 + 0.344i", roots[0].toString());
		assertEquals("-1.455 -0.344i", roots[1].toString());
		
	}
	
	@Test
	public void toStringTest() {
		ComplexNumber c = ComplexNumber.parse("+2i");
		ComplexNumber c1 = ComplexNumber.parse("-2i");
		ComplexNumber c2 = ComplexNumber.parse("2 - 2i");
		ComplexNumber c3 = ComplexNumber.parse("2 + 2i");
		ComplexNumber c4 = ComplexNumber.parse("+2+2i");
		ComplexNumber c5 = ComplexNumber.parse("+2");
		
		assertEquals("2.000i", c.toString());
		assertEquals("-2.000i", c1.toString());
		assertEquals("2.000 -2.000i", c2.toString());
		assertEquals("2.000 + 2.000i", c3.toString());
		assertEquals("2.000 + 2.000i", c4.toString());
		assertEquals("2.000", c5.toString());
	}
	
	
	
}
