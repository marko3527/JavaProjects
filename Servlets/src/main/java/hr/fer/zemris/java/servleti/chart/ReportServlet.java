package hr.fer.zemris.java.servleti.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that just dispatches the request to 'report.jsp' page which will
 * render the pie chart result.
 * 
 * @author Marko
 *
 */
@WebServlet("/report")
public class ReportServlet extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
	}
}
