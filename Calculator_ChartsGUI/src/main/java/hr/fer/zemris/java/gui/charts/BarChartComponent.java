package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;


/**
 * Class that will handle drawing of the bar chart.
 * 
 * @author Marko
 *
 */
public class BarChartComponent extends JComponent{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color barColor = new Color(70,130,180);
	private static final Color shadowColor = new Color(128,128,128, 80);
	private static final Color axisColors = new Color(128,128,128);
	private static final Color meshColor = new Color(70, 130, 180, 80);
	
	private static final Font labelsFont = new Font("Arial", Font.PLAIN, 18);
	private static final Font axisValuesFont = new Font("Arial", Font.BOLD, 16);

	private BarChart chart;
	
	/**
	 * Represents the position of the label under xAxis
	 */
	private Point xLabel;
	
	/**
	 * Represents the position of the label by yAxis
	 */
	private Point yLabel;
	
	private int yOffset;
	private int xOffset;
	
	/**
	 * width of the bar base on how many of the bars should be drawn
	 */
	private int widthOfBar;
	
	/**
	 * origin of the chart
	 */
	private Point origin;
	
	/**
	 * distance between two y values
	 */
	private int unitLength;
	
	/**
	 * length of the x and y axis
	 */
	private int xLength;
	private int yLength;
	
	/**
	 * arrows on the end of both axis
	 */
	private Polygon xArrow;
	private Polygon yArrow;
	
	
	/**
	 * Constructor.
	 * 
	 * @param chart reference to chart with values that need to be drawn
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		calculateFixedValues();
		
		g2d.setFont(labelsFont);
		drawLabels(g2d);
		
		drawYAxisValues(g2d);
		
		drawValues(g2d);
		
		g2d.setColor(axisColors);
		g2d.fillPolygon(yArrow);
		g2d.fillPolygon(xArrow);
		
		g2d.setColor(axisColors);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(origin.x - 5, origin.y, origin.x + xLength, origin.y);
		g2d.drawLine(origin.x, origin.y + 5, origin.x, origin.y - yLength);
		g2d.setStroke(new BasicStroke(1));
		
		
	}
	
	
	/**
	 * Method that calculates the fixed values of the chart like font height
	 * font width, bar width... 
	 */
	private void calculateFixedValues() {
		int canvasWidth = this.getWidth();
		int canvasHeight = this.getHeight();
		
		/**
		 * This offset is used for distance between labels and axis values and
		 * between axis values and axis. This offset is not based on the size
		 * of the window.
		 */
		yOffset = 15;
		xOffset = 10;
		
		/**
		 * label font infos
		 */
		FontMetrics labelsMetrics = getFontMetrics(labelsFont);
		int labelHeight = labelsMetrics.getHeight();
		int xlabelWidth = labelsMetrics.stringWidth(chart.getxLabel());
		
		/**
		 * values written on the axis infos
		 */
		FontMetrics valuesMetrics = getFontMetrics(axisValuesFont);
		int valuesWidth = valuesMetrics.stringWidth("" + chart.getMaxY());
		int valuesHeight = valuesMetrics.getHeight();
		
		/**
		 * Starting point of coordinate system
		 */
		origin = new Point(
			xOffset + labelHeight + xOffset + valuesWidth,
			canvasHeight - (yOffset + labelHeight + yOffset + valuesHeight)
		);
		
		/**
		 * length between two y's
		 */
		unitLength = (origin.y - yOffset*2)/(chart.getMaxY()/chart.getDensity());
		
		/**
		 * Width of one bar based on number of bars
		 */
		widthOfBar = (canvasWidth - origin.x - xOffset*2)/chart.getValues().size();
		
		/**
		 * length of the x axis, remaining of the canvas 
		 */
		xLength = canvasWidth - origin.x - xOffset*2;
		yLength = origin.y - yOffset*2;
		
		/**
		 * positions of labels
		 */
		xLabel = new Point(xLength/2 - xlabelWidth/2 + origin.x,
				   		   canvasHeight - yOffset);
		yLabel = new Point(xOffset + labelHeight - xOffset,
				(Math.abs(canvasHeight - origin.y)) + yLength/2);
		
		/**
		 * arrows of the axis
		 */
		yArrow = new Polygon(
				new int[] {origin.x - 5, origin.x, origin.x + 5},
				new int[] {origin.y - yLength + 5, origin.y - yLength - 10, origin.y - yLength + 5},
				3
		);
		xArrow = new Polygon(
				new int[] {origin.x + xLength, origin.x + xLength + 10, origin.x + xLength},
				new int[] {origin.y - 5, origin.y, origin.y + 5},
				3
		);
		
	}
	
	
	/**
	 * Method that draws the label strings
	 * 
	 * @param g2d
	 */
	private void drawLabels(Graphics2D g2d) {
		
		g2d.drawString(chart.getxLabel(), xLabel.x, xLabel.y);
		
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI/2);
		g2d.setTransform(at);
		g2d.drawString(chart.getyLabel(), -yLabel.y, yLabel.x);
		at.rotate(Math.PI/2);
		g2d.setTransform(at);
		
	}
	
	
	/**
	 * Method that draws the bars on the chart and the x value
	 * under every bar and vertical lines of the mesh.
	 * 
	 * @param g2d {@code Graphics2D}
	 */
	private void drawValues(Graphics2D g2d) {
		List<XYValue> values = chart.getValues();
		
		for(int i = 0; i < values.size(); i++) {
			XYValue value = values.get(i);
			String stringValue = "" + value.getX();
			FontMetrics metrics = g2d.getFontMetrics();
			int widthOfString = metrics.stringWidth(stringValue);
			int heightOfString = metrics.getHeight();	
			
			g2d.setColor(shadowColor);
			g2d.fillRect(i * widthOfBar + origin.x,
					 	origin.y,
					 	widthOfBar ,
					 	(-value.getY() * unitLength + 5)/chart.getDensity());
			
			g2d.setColor(barColor);
			g2d.fillRect(i * widthOfBar + origin.x,
						 origin.y,
						 widthOfBar - 3,
						 (-value.getY() * unitLength)/chart.getDensity());
			
			g2d.setColor(meshColor);
			g2d.drawLine(i*widthOfBar + origin.x, origin.y + yOffset - 5,
						 i*widthOfBar + origin.x, origin.y - yLength);
			
			g2d.setColor(Color.black);
			g2d.drawString(stringValue,
						   origin.x + i*widthOfBar + widthOfBar/2 - widthOfString/2,
						   origin.y + yOffset + heightOfString);
		}
	}
	
	
	/**
	 * Method that draws the values written by the side of y axis.
	 * Also it draws the horizontal lines of the mesh.
	 * 
	 * @param g2d
	 */
	private void drawYAxisValues(Graphics2D g2d) {
		g2d.setFont(axisValuesFont);
		
		for(int i = 0; i <= chart.getMaxY()/chart.getDensity(); i += 1) {
			String toDraw = "" + i*chart.getDensity();
			g2d.setColor(Color.black);
			FontMetrics metrics = g2d.getFontMetrics();
			g2d.drawString(toDraw, origin.x - xOffset - metrics.stringWidth(toDraw),
						   origin.y - i*unitLength + 5);
			
			g2d.setColor(meshColor);
			g2d.drawLine(origin.x - 5, origin.y - i*unitLength,
						 origin.x + xLength, origin.y - i*unitLength);
		}
		
	}
}
