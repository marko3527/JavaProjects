package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.Poll;
import hr.fer.zemris.java.voting.dao.DAOProvider;


/**
 * Servlet that gets the reference to DAO interface and fetches the list of all
 * polls and dispatches the request to the file 'polls.jsp' which just
 * list all available polls as anchor html element.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDAO().getPolls();
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}

}
