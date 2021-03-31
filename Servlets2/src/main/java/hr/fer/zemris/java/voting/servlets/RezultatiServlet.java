package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.OptionsUtil;
import hr.fer.zemris.java.voting.dao.DAOProvider;


/**
 * Servlet that displays the results of the votes by showing table of
 * all options and their current number of votes, showing the graphic
 * representation of voted options in pie chart, offers the download of
 * XLS file with all informations about votes and it also offers the 
 * name of the options that have the most votes.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class RezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pollId = (int) req.getServletContext().getAttribute("pollId");
		List<Option> options = DAOProvider.getDAO().getOptionList(pollId);
		
		List<Option> mostVoted = OptionsUtil.optionsWithMostVotes(options);
		req.setAttribute("options", options);
		req.setAttribute("mostVotes", mostVoted);
		
		req.getRequestDispatcher("/WEB-INF/pages/rezultati.jsp").forward(req, resp);
	}
	
	

}
