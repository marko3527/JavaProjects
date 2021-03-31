package hr.fer.zemris.java.servleti.glasanje;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that takes one parameter(a) which is id for which user is voting
 * for. It adds the votes and writes the current situation of the 
 * votes into text file which acts like a data base.
 * 
 * @author Marko
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		BandsUtil bands = new BandsUtil(Files.readAllLines(Paths.get(fileName)));
		
		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		if(!(new File(fileName).exists())) {
			bands.writeVotes(fileName);
		}
		bands.readVotes(fileName);
		String votedId = (String)req.getParameter("id");
		
		bands.addVote(votedId);
		bands.writeVotes(fileName);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
