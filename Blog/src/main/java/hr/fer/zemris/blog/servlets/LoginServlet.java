package hr.fer.zemris.blog.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.blog.dao.DAOProvider;
import hr.fer.zemris.blog.model.BlogUser;
import hr.fer.zemris.blog.util.PasswordEncoder;


/**
 * Servlet that handles the login page. When request method is GET then it 
 * renders the page 'startingPage.jsp' and if request method is POST then it
 * tries to verify that inputed user exist with inputed password. If user has been
 * verified then it set's session attributes and else it displays the appropriate
 * message.
 * 
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/main")
public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", DAOProvider.getDAO().getAllUsers());
		req.getRequestDispatcher("/WEB-INF/pages/startingPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		try {
			String passHash = PasswordEncoder.encodePassword(req.getParameter("password"));
			List<String> mistakes = new ArrayList<>();
			
			BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
			
			if(user == null) {
				req.getServletContext().setAttribute("login.nickname", nick);
				addLoginMistake(mistakes, req, resp);
			}
			else if(!(user.getPasswordHash().equals(passHash))) {
				req.getServletContext().setAttribute("login.nickname", nick);
				addLoginMistake(mistakes, req, resp);
			}
			else {
				req.getSession().setAttribute("current.user.id", user.getId());
				req.getSession().setAttribute("current.user.fn", user.getFirstName());
				req.getSession().setAttribute("current.user.ln", user.getLastName());
				req.getSession().setAttribute("current.user.nick", user.getNick());
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
			}
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ignorable) {
		}
	}
	
	
	/**
	 * If there was mistake during verification of the user this method registers
	 * that mistake so the page could display it.
	 * 
	 * @param mistakes
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addLoginMistake(List<String> mistakes, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		mistakes.add("User nickname od password is incorrect!");
		req.getServletContext().setAttribute("mistakes", mistakes);
		resp.sendRedirect(req.getContextPath());
	}
}
