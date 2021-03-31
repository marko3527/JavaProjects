package hr.fer.zemris.java.servleti.glasanje;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet that makes the pie chart data representing number of 
 * votes for each band.
 * 
 * @author Marko
 *
 */
@WebServlet("/glasanje-grafika")
public class PieGlasanjeServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ServletOutputStream output = resp.getOutputStream();
		String bandsFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		BandsUtil loader = new BandsUtil(Files.readAllLines(Paths.get(bandsFile)));
		String resultFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		loader.readVotes(resultFile);
		List<Band> bands = loader.getBands();
		
		
		try {
			DefaultPieDataset dataSet = new DefaultPieDataset();
			
			for(int i = 0; i < bands.size(); i++) {
				try {
					dataSet.setValue(bands.get(i).getName(),
									 bands.get(i).getNumberOfVotes());
				} catch (IndexOutOfBoundsException e) {
					dataSet.setValue(bands.get(i).getName(), 0);
				}
			}	
			
			JFreeChart chart = ChartFactory.createPieChart("", dataSet,
					   true, true, false);
			
			PiePlot colorConfigurator = (PiePlot)chart.getPlot();
			colorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
	        colorConfigurator.setLabelBackgroundPaint(new Color(220, 220, 220));  
	        
	        resp.setContentType("image/png");
			
	        
			ChartUtilities.writeChartAsPNG(output, chart, 640, 640);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
		
		
	}

}
