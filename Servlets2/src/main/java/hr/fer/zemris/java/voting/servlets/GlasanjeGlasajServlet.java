package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.dao.DAOProvider;


/**
 * Servlet that takes one parameter(a) which is id for which option user is voting
 * for. It adds the votes and writes the current situation of the 
 * votes into data base.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int votedId = Integer.parseInt(req.getParameter("id"));
		int pollId = (int)req.getServletContext().getAttribute("pollId");
		Option option = DAOProvider.getDAO().getOption(votedId, pollId);
		option.setNumberOfVotes(option.getNumberOfVotes() + 1);
		
		DAOProvider.getDAO().writeVote(option, pollId);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}

}
