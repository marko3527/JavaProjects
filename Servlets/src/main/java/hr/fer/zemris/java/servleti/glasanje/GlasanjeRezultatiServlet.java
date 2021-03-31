package hr.fer.zemris.java.servleti.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that handles displaying the results of the votes.
 * It renders the table with voting results, pie chart representation
 * of results and offers download of the excel file with 
 * representation of votes. And at the bottom of the page
 * it renders the link of the bands that are leading with votes
 * currently.
 * 
 * @author Marko
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		BandsUtil bandsUtil = new BandsUtil(Files.readAllLines(Paths.get(bandsFile)));
		bandsUtil.readVotes(fileName);
		
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
	
		
		if(lines.isEmpty()) {
			bandsUtil.writeVotes(fileName);
		}
	
		List<String> bestLinks = new ArrayList<>();
		List<String> bestBands = new ArrayList<>();
		for(Band band : bandsUtil.bandsWithMostVotes()) {
			bestLinks.add(band.getLink());
			bestBands.add(band.getName());
		}
		
		req.setAttribute("best", bestBands);
		req.setAttribute("links", bestLinks);
		req.setAttribute("names", bandsUtil.getNames());
		req.setAttribute("results", bandsUtil.returnResults());
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}

}
