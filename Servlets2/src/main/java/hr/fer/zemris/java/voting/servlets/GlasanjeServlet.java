package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.Poll;
import hr.fer.zemris.java.voting.dao.DAOProvider;


/**
 * Servlet that displays the starting page of voting application. 
 * Starting page displays the name of each option that can be voted for.
 * Each name is link that leads to the page which will register the vote.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int pollId = Integer.parseInt(req.getParameter("pollID"));
		req.getServletContext().setAttribute("pollId", pollId);
		List<Option> options = DAOProvider.getDAO().getOptionList(pollId);
		Poll poll = DAOProvider.getDAO().getPoll(pollId);
		
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
		
	}

}

