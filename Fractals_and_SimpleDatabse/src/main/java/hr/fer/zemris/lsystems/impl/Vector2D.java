package hr.fer.zemris.lsystems.impl;

/**
 * Two dimensional vector is a math object that has direction and magnitude. 
 * Vector has a x value and y value. This class provides methods for doing 
 * math calculations with vectors.
 * 
 * @author Marko
 *
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	
	/**
	 * Constructor.
	 * 
	 * @param x {@code double} x value of the vector
	 * @param y {@code double} y value of the vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double}, x value of the vector
	 */
	public double getX() {
		return x;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double}, y value of the vector
	 */
	public double getY() {
		return y;
	}
	
	
	/**
	 * Method that translates a vector for another vector by adding components of two.
	 * It changes the value of the vector for which is being called.
	 * 
	 * @param offset {@code Vector2D} value for which the vector should be translated
	 * @throws NullPointerException if argument is null
	 */
	public void translate(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("Provide not null reference for offsetting the vector!");
		}
		x += offset.x;
		y += offset.y;
	}
	
	
	/**
	 * Method that translates a vector for another vector by adding components of two.
	 * It returns new Vector2D that represents a translated vector
	 * 
	 * @param offset {@code Vector2D} value for which the vector should be translated
	 * @return {@code Vector2D} translated vector
	 * @throws NullPointerException if argument is null
	 */
	public Vector2D translated(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("Provide not null reference for offsetting the vector!");
		}
		double x = this.x + offset.x;
		double y = this.y + offset.y;
		
		return new Vector2D(x, y);
	}
	
	
	/**
	 * Method that rotates a vector by some angle. Angle should be
	 * provided in radians.
	 * It changes the value of the vector for which is being called.
	 * 
	 * @param angle {@code double} value of the angle that vector should be rotated for
	 */
	public void rotate(double angle) {
		double newX = x * Math.cos(angle) - y * Math.sin(angle);
		double newY = x * Math.sin(angle) + y * Math.cos(angle);
		x = newX;
		y = newY;
	}
	
	
	/**
	 * Method that rotates a vector by some angle. Angle should be
	 * provided in radians. It returns a new rotated vector.
	 * 
	 * @param angle {@code double} value of the angle that vector should be rotated for
	 * @return {@code Vector2D} rotated vector
	 */
	public Vector2D rotated(double angle) {
		double x = this.x*Math.cos(angle) - this.y*Math.sin(angle);
		double y = this.x*Math.sin(angle) + this.y*Math.cos(angle);
		
		return new Vector2D(x,y);
	}
	
	
	/**
	 * Metod that sclaes a vector by a provided amount.
	 * It changes the value of the vector for which is being called.
	 * 
	 * @param scaler {@code double} the amount that vector should be scaled by
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	
	/**
	 * Metod that sclaes a vector by a provided amount.
	 * It returns a newly scaled vector.
	 * 
	 * @param scaler {@code double} the amount that vector should be scaled by
	 * @return {@code Vector2D} scaled vector
	 */
	public Vector2D scaled(double scaler) {
		double x = this.x * scaler;
		double y = this.y * scaler;
		
		return new Vector2D(x,y);
	}
	
	
	/**
	 * Method that makes a copy of a vector and returns it.
	 * It changes the value of the vector for which is being called.
	 *  
	 * @return {@code Vector2D} copy of the vector
	 */
	public Vector2D copy() {
		return new Vector2D(x,y);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vector2D)) {
			return false;
		}
		Vector2D vector = (Vector2D) obj;
		if(this.x == vector.x && this.y == vector.y) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Double.valueOf(x).hashCode() + Double.valueOf(y).hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Vector = (%.2f, %.2f)", x, y));
		return sb.toString();
	}
	
	

}
