package hr.fer.zemris.java.servleti.colors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that looks in the parameters and depending on it sets
 * the seesion attribute of the application to string value of that color.
 * And after setting session color it looks up for the path stored under
 * key referer and redirects the application to that path.
 * 
 * 
 * @author Marko
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		if(color != null) {
			req.getSession().setAttribute("pickedBgCol", color);
		}
		else {
			req.getSession().setAttribute("pickedBgCol", "white");
		}
		
		resp.sendRedirect((String) req.getSession().getAttribute("referer"));
	}
	
	

}
