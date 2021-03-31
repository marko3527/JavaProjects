package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo that shows the usage of BarChart component.
 * USer inputs the path to the file through
 * command line and from that file program 
 * reads the values to be displayed on chart.
 * 
 * @author Marko
 *
 */
public class BarChartDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BarChart chart;
	private static String filename;

	
	/**
	 * Constructor.
	 * 
	 */
	public BarChartDemo() {
		setSize(700,620);
		setTitle("Chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel fileName = new JLabel(filename, SwingConstants.CENTER);
		cp.add(fileName, BorderLayout.PAGE_START);
		
		BarChartComponent comp = new BarChartComponent(chart);
		cp.add(comp,BorderLayout.CENTER);
	}
	
	
	/**
	 * Main method from which the program starts.
	 * 
	 * @param args {@code commandLine arguments}
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalArgumentException("I need one argument, path to file with data!");
		}
		else {
			filename = args[0];
			chart = readFile(filename);
		}
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}
	
	
	/**
	 * Method that reads the file and produces the BarChart from data
	 * in the file if format is good.
	 * Supposed format is next:
	 * First line should be name of the x axis
	 * Second should be name of the y axis
	 * Third should be values for the chart
	 * Fourth should be min value of y
	 * Fifth should be max value of y
	 * Sixth should be distance between two y values
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static BarChart readFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		String xLabel = reader.readLine();
		String yLabel = reader.readLine();
		String unparsedValues = reader.readLine();
		String minY = reader.readLine();
		String maxY = reader.readLine();
		String density = reader.readLine();
		
		unparsedValues.replace("\\s+", " ");
		String[] values = unparsedValues.split(" ");
		List<XYValue> chartValues = new LinkedList<>();
		
		for(String value : values) {
			if(value.contains("\\s+")) {
				reader.close();
				throw new IllegalArgumentException("The value must be written in next format: x,y!\n"
												 + "No spaces needed.");
			}
			String[] xy = value.split(",");
			try {
				chartValues.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			} catch (NumberFormatException e) {
				System.out.println("Format of the file is not good. For values is not provided\n"
								 + "value parsable to integer.");
				System.exit(0);
			}
			
		}
		
		reader.close();
		return new BarChart(chartValues, xLabel, yLabel, Integer.parseInt(minY),
							Integer.parseInt(maxY), Integer.parseInt(density));
	}

}
