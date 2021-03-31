package hr.fer.zemris.java.servleti.story;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that randomly selects the color of the font each time 
 * when this servlet is called.
 * 
 * @author Marko
 *
 */
@WebServlet("/story")
public class RandomColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] colors = new String[] {
		"black", "green", "yellow", "red", "blue", "darkGray", "orange", 	
	};
	private Random rand = new Random();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int selectedColorIndex = rand.nextInt(colors.length - 1);
		
		req.setAttribute("fontColor", colors[selectedColorIndex]);
		
		req.getRequestDispatcher("/stories/funny.jsp").forward(req, resp);
	}
	
}
