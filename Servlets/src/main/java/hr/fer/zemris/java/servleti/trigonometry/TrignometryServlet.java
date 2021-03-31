package hr.fer.zemris.java.servleti.trigonometry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that takes two arguments, first argument is lowerBound(a) and
 * second is upperBound(b) and calculates the sin and cos values 
 * between those bounds.
 * 
 * @author Marko
 *
 */
@WebServlet("/trigonometric")
public class TrignometryServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Object lowerBound = req.getParameter("a");
		Object upperBound = req.getParameter("b"); 
		
		TrigonometryFunctions functions = new TrigonometryFunctions(
										  lowerBound == null ? null : Integer.parseInt("" + lowerBound),
										  upperBound == null ? null : Integer.parseInt("" + upperBound));
		
		req.setAttribute("cosValues", functions.getCosValues());
		req.setAttribute("sinValues", functions.getSinValues());
		req.setAttribute("angles", functions.getAngles());
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	

}
