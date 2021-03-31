package hr.fer.zemris.java.gui.layouts;

/**
 * Class that represents the bounds on the axis for 
 * calc layout. This class is just helping class for better organized code.
 * 
 * @author Marko
 *
 */
public class Bounds {
	
	private double startPosition;
	private double size;
	
	
	/**
	 * Constructor.
	 * 
	 * @param startPosition starting position
	 * @param size size of the component
	 */
	public Bounds(double startPosition, double size) {
		this.startPosition = startPosition;
		this.size = size;
	}
	
	
	/**
	 * Getter.
	 * @return x starting position
	 */
	public double getxStartPosition() {
		return startPosition;
	}
	
	
	/**
	 * Getter.
	 * @return size of the component
	 */
	public double getxWidth() {
		return size;
	}

}
