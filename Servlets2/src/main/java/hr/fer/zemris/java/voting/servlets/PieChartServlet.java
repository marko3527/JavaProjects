package hr.fer.zemris.java.voting.servlets;

import java.awt.Color;

import java.io.IOException;
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

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.dao.DAOProvider;

/**
 * Servlet that makes the pie chart data representing number of 
 * votes for each option.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class PieChartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ServletOutputStream output = resp.getOutputStream();
		int pollId = (int)req.getServletContext().getAttribute("pollId");
		List<Option> options = DAOProvider.getDAO().getOptionList(pollId);
		
		
		try {
			DefaultPieDataset dataSet = new DefaultPieDataset();
			
			for(int i = 0; i < options.size(); i++) {
				try {
					dataSet.setValue(options.get(i).getName(),
									 options.get(i).getNumberOfVotes());
				} catch (IndexOutOfBoundsException e) {
					dataSet.setValue(options.get(i).getName(), 0);
				}
			}	
			
			JFreeChart chart = ChartFactory.createPieChart("", dataSet,
					   true, true, false);
			
			PiePlot colorConfigurator = (PiePlot)chart.getPlot();
			colorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
	        colorConfigurator.setLabelBackgroundPaint(new Color(240, 240, 240));  
	        
	        resp.setContentType("image/png");
			
			ChartUtilities.writeChartAsPNG(output, chart, 640, 640);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
		
	}

}
