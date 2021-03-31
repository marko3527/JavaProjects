package hr.fer.zemris.java.servleti.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that displays the starting page of voting application. 
 * Starting page displays the name of each band that can be voted for.
 * Each name is link that leads to the page which will register the vote.
 * 
 * @author Marko
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		BandsUtil loader = new BandsUtil(lines);
		
		
		req.setAttribute("ids", loader.getIds());
		req.setAttribute("names", loader.getNames());
		req.setAttribute("links", loader.getLinks());
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
	}

}
