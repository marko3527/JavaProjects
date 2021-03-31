package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Class that knows the current state of the turtle.
 * It knows the position of the turtle, the angle turtle is watching,
 * the colour of the line that turtle draws with and finally 
 * the effective length of a single line drawn by turtle.
 * 
 * @author Marko
 *
 */
public class TurtleState {
	
	private Vector2D currentPosition;
	private Vector2D angle;
	private Color color;
	private double effectiveLength;
	
	
	/**
	 * Constructor that accepts the angle of the turtle as a number. It calls the other
	 * constructor to assign other variables.
	 * 
	 * @param currentPosition {@code Vector2D} the current position of the turtle
	 * @param angle {@code double} angle in degrees in which the turtle is facing
	 * @param color {@code Color} colour of the line turtle draws
	 * @param effectiveLength {@code double} length of the line
	 */
	public TurtleState(Vector2D currentPosition, double angle, Color color, double effectiveLength) {
		this(currentPosition, color, effectiveLength);
		this.angle = new Vector2D(1, 0);
		this.angle.rotate(Math.toRadians(angle));
	}
	
	
	/**
	 * Constructor that accepts the angle of the turtle as a vector. It calls the other
	 * constructor to assign other variables. Variable angle should be vector with length 1.
	 * 
	 * @param currentPosition {@code Vector2D} the current position of the turtle
	 * @param angle {@code Vector2D} angle in degrees in which the turtle is facing
	 * @param color {@code Color} colour of the line turtle draws
	 * @param effectiveLength {@code double} length of the line
	 */
	public TurtleState(Vector2D currentPosition, Vector2D angle, Color color, double effectiveLength) {
		this(currentPosition, color, effectiveLength);
		this.angle = angle;
	}
	
	
	/**
	 * Private constructor.
	 * 
	 * @param currentPosition {@code Vector2D} the current position of the turtle
	 * @param color {@code Color} colour of the line turtle draws
	 * @param effectiveLength {@code double} length of the line
	 */
	private TurtleState(Vector2D currentPosition, Color color, double effectiveLength) {
		if(currentPosition == null) {
			throw new NullPointerException("Turtle state can't accept null references!");
		}
		if(color == null) {
			this.color = Color.black;
		}
		this.currentPosition = currentPosition;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}
	
	
	/**
	 * Method that returns object {@code TurtleState} that is a copy of the
	 * current state of the turtle.
	 * 
	 * @return a reference to newly created turtle stated that is copy of current one
	 */
	public TurtleState copy() {
		Vector2D copiedPosition = new Vector2D(currentPosition.getX(), currentPosition.getY());
		Vector2D copiedAngle = new Vector2D(angle.getX(), angle.getY());
		return new TurtleState(copiedPosition,
							   copiedAngle,
							   color, 
							   effectiveLength);
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return currentPosition of the state
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param currentPosition {@code Vector2D} position to set the state on
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return angle of the state
	 */
	public Vector2D getAngle() {
		return angle;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param angle {@code Vector2D} angle for the state to be set on
	 */
	public void setAngle(Vector2D angle) {
		this.angle = angle;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param color {@code Color} color of the line
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code} the current value of line color
	 */
	public Color getColor() {
		return color;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param effectiveLength {@code double} length of the line
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code double} length of the line
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

}
