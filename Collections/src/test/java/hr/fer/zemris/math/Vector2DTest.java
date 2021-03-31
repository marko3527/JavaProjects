package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	public void getterTest() {
		Vector2D vector = new Vector2D(2.59, 1.58);
		assertEquals(2.59, vector.getX());
		assertEquals(1.58, vector.getY());
	}
	
	@Test
	public void translateTest() {
		Vector2D vector = new Vector2D(2, 4);
		vector.translate(new Vector2D(1, 2));
		assertEquals(3, vector.getX());
		assertEquals(6, vector.getY());
		
		Vector2D translated = vector.translated(new Vector2D(1, 1));
		assertEquals(4, translated.getX());
		assertEquals(7, translated.getY());
		assertEquals(3, vector.getX());
		assertEquals(6, vector.getY());
	}
	
	@Test
	public void rotateTest() {
		Vector2D vector = new Vector2D(1,1);
		vector.rotate(Math.toRadians(45));
		assertEquals("0.00", String.format("%.2f", vector.getX()));
		assertEquals("1.41", String.format("%.2f", vector.getY()));
		
		Vector2D rotated = vector.rotated(Math.toRadians(-45));
		assertEquals("1.00", String.format("%.2f", rotated.getX()));
		assertEquals("1.00", String.format("%.2f", rotated.getX()));
		assertEquals("0.00", String.format("%.2f", vector.getX()));
		assertEquals("1.41", String.format("%.2f", vector.getY()));
	}
	
	
	@Test
	public void scaleTest() {
		Vector2D vector = new Vector2D(2,2);
		vector.scale(5);
		assertEquals(10, vector.getX());
		assertEquals(10, vector.getY());
		
		Vector2D scaled = vector.scaled(0.5);
		assertEquals(5, scaled.getX());
		assertEquals(5, scaled.getY());
		assertEquals(10, vector.getX());
		assertEquals(10, vector.getX());
	}
	
	
	@Test
	public void copyTest() {
		Vector2D vector = new Vector2D(2,2);
		Vector2D copyed = vector.copy();
		assertEquals(true, vector.equals(copyed));
	}

}
