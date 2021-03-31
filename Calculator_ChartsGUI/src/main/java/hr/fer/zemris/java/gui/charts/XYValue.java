package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents the x,y that should be represented
 * as one bar in bar chart.
 * 
 * @author Marko
 *
 */
public class XYValue {
	
	private int x;
	private int y;
	
	
	/**
	 * COnstructor.
	 * 
	 * @param x xAxis value
	 * @param y yAxis value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return xAxis value
	 */
	public int getX() {
		return x;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return yAxis value
	 */
	public int getY() {
		return y;
	}

}
