package hr.fer.zemris.java.servleti.chart;

import java.awt.Color;
import java.io.IOException;

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
 * Demo servlet that shows how to generate a pie chart that 
 * will be displayed in html page. Data shown in this pie chart 
 * is not relevant and it is here just for demo purposes.
 * 
 * @author Marko
 *
 */
@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream output = resp.getOutputStream();
		
		try {
			DefaultPieDataset pieChart = new DefaultPieDataset();
			
			pieChart.setValue("Linux", 24);
			pieChart.setValue("Windows", 56);
			pieChart.setValue("Mac-OS", 20);
			
			JFreeChart chart = ChartFactory.createPieChart("", pieChart,
														   true, true, false);
			
			PiePlot colorConfigurator = (PiePlot)chart.getPlot();
			colorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
            colorConfigurator.setLabelBackgroundPaint(new Color(220, 220, 220));  
            
            resp.setContentType("image/png");
			
            
			ChartUtilities.writeChartAsPNG(output, chart, 640, 640);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			output.close();
		}
	}

}
