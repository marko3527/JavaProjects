package hr.fer.zemris.java.gui.charts;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements one bar chart. It defines the list of 
 * values to be shown. Minimum and maximum y are the limits
 * for drawing each bar individually. It also has x axis label
 * and y axis label.
 * 
 * @author Marko
 *
 */
public class BarChart {
	
	private List<XYValue> values = new LinkedList<>();
	private String xLabel;
	private String yLabel;
	private int minY;
	private int maxY;
	private int density;
	
	/**
	 * Constructor. It checks whether the given y values are larger or equal
	 * to given min value and checks the maxY, if it is larger than minY
	 * 
	 * @param values list of XYValues 
	 * @param xLabel string under x axis
	 * @param yLabel string under y axis
	 * @param minY minimum y
	 * @param maxY maximum y, can't be smaller or equal to minY
	 * @param density difference between two y's
	 */
	public BarChart(List<XYValue> values, String xLabel, String yLabel,
					int minY, int maxY, int density) {
		if(values == null || xLabel == null || yLabel == null) {
			throw new IllegalArgumentException("You provided null reference!");
		}
		if(minY < 0) {
			throw new IllegalArgumentException("Min y can't be smaller than zero!");
		}
		if(maxY <= minY) {
			throw new IllegalArgumentException("Max y must be larger than minY");
		}
		if(maxY % density != 0) {
			maxY = (int) (Math.ceil((double)maxY/density) * density);
		}
		this.maxY = maxY;
		this.minY = minY;
		this.density = density;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		
		for(XYValue value : values) {
			if(value.getY() < minY) {
				throw new IllegalArgumentException("Y value can't be smaller than minimum y!");
			}
		}
		this.values = values;
		
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return  maxY
	 */
	public int getMaxY() {
		return maxY;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return xLabel string
	 */
	public String getxLabel() {
		return xLabel;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return yLabel string
	 */
	public String getyLabel() {
		return yLabel;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return density of the y values
	 */
	public int getDensity() {
		return density;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return values to draw
	 */
	public List<XYValue> getValues() {
		return values;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return minY
	 */
	public int getMinY() {
		return minY;
	}

}
