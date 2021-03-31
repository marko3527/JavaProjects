package hr.fer.zemris.math;


/**
 * Class that represents the vector in three dimensions and offers math
 * operation on vectors in 3D space. Every operation returns a new 
 * vector, so any of this class's fields can't be modified. 
 * 
 * @author Marko
 *
 */
public class Vector3 {
	
	private double x;
	private double y;
	private double z;
	
	
	/**
	 * Constructor. 
	 * 
	 * @param x {@code double} amount on the x axis (width)
	 * @param y {@code double} amount on the y axis (height)
	 * @param z {@code double} amount on the z axis (depth)
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	/**
	 * Method that computes the "length" of the vector by calculating 
	 * square root of each axis component to the second power.
	 * 
	 * @return {@code double} norm
	 */
	public double norm() {
		return Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
	}
	
	
	/**
	 * Method that calculates the normalized vector. Normalized vector
	 * is a vector where each of his axis values is divided by the 
	 * length of the vector (norm).
	 * 
	 * @return {@code Vector3} normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		double xNormalized = x / norm;
		double yNormalized = y / norm;
		double zNormalized = z / norm;
		
		return new Vector3(xNormalized, yNormalized, zNormalized);
	}
	
	
	/**
	 * Method that adds components of the two vectors and 
	 * returns the new vector.
	 * 
	 * @param other {@code Vector3} vector to be added
	 * @return {@code Vector3} result of adding other to current vector
	 * @throws NullPointerException if the arguments is null
	 */
	public Vector3 add(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Can't add null reference to vector!");
		}
		return new Vector3(
			x + other.getX(), y + other.getY(), z + other.getZ()
		);
	}
	
	
	/**
	 * Method that subtracts components of the two vectors and 
	 * returns the new vector.
	 * 
	 * @param other {@code Vector3} vector to be added
	 * @return {@code Vector3} result of subtracting other values from current vector 
	 * @throws NullPointerException if the arguments is null
	 */
	public Vector3 sub(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Can't add null reference to vector!");
		}
		return new Vector3(
			x - other.getX(), y - other.getY(), z - other.getZ()
		);
	}
	
	
	/**
	 * Method that computes the dot product of the two vectors. 
	 * Dot product is simply multiplying corresponding values of the 
	 * two vectors and adding them together. 
	 * 
	 * @param other {@code Vector3} other vector for calculating the dot product
	 * @return {@code double} value of the dot product of two vectors
	 * @throws NullPointerException if the arguments is null
	 */
	public double dot(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Can't add null reference to vector!");
		}
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}
	
	
	/**
	 * Method that calculates the cross product of two vectors.
	 * Result of the cross product is a vector that is perpendicular to 
	 * both the a and b vectors
	 * 
	 * @param other {@code Vector3} second vector 
	 * @return {@code Vector3} perpendicular vector 
	 * @throws NullPointerException if the arguments is null
	 */
	public Vector3 cross(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Can't add null reference to vector!");
		}
		double xCross = y * other.z - z * other.y;
		double yCross = z * other.x - x * other.z;
		double zCross = x * other.y - y * other.x;
		
		return new Vector3(xCross, yCross, zCross);
		
	}
	
	
	/**
	 * Method that scales the vector simply by multiplying each
	 * component with some given value.
	 * 
	 * @param s {@code double} value to scale vector for
	 * @return {@code Vector3} scaled vector
	 * @throws NullPointerException if the arguments is null
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}
	
	
	/**
	 * Calculates the cos of the angle between two vectors.
	 * 
	 * @param other {@code Vector3} second vector 
	 * @return {@code double} cos value of the angle
	 * @throws NullPointerException if the arguments is null
	 */
	public double cosAngle(Vector3 other) {
		if(other == null) {
			throw new NullPointerException("Can't add null reference to vector!");
		}
		
		double dotProduct = dot(other);
		double firstVLength = norm();
		double secondVLenght = other.norm();
		
		return dotProduct/(firstVLength * secondVLenght);
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} x value of the vector
	 */
	public double getX() {
		return x;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} y value of the vector
	 */
	public double getY() {
		return y;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} z value of the vector
	 */
	public double getZ() {
		return z;
	}
	
	
	/**
	 * Method that returns the array with values of the 
	 * each component of the vector. [xValue, yValue, zValue]
	 * 
	 * @return {@code double[]} array
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
	

}
