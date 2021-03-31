package hr.fer.zemris.java.servleti.colors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that dispatches the request to 'colors.jsp' which will render
 * colors that could be set as background in application. Before dispatching
 * the request in session parameters set the path of the page that made the request
 * for changing color so the user can be returned to that page.
 * 
 * @author Marko
 *
 */
@WebServlet("/chooseColor")
public class ColorChooserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("referer", req.getHeader("referer"));
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
		
	}

}
