package hr.fer.zemris.java.servleti.listeners;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that dispatches the request and response to the page 'appinfo.jsp'
 * which will show for how long this application has been running.
 * 
 * @author Marko
 *
 */
@WebServlet("/info")
public class AppInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/pages/appinfo.jsp").forward(req, resp);
	}
	
	

}
