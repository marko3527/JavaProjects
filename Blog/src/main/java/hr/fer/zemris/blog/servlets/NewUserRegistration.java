package hr.fer.zemris.blog.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.blog.dao.DAOProvider;
import hr.fer.zemris.blog.model.BlogUser;


/**
 * Servlet that handles new user registration. If the request method is GET
 * then this servlet renders page newUserForm.jsp and if the request method 
 * is POST then it creates a BlogUserForm from HTTPServletRequest and
 * validates it, if form is not valid it stores mistakes into servlet context
 * by calling method storeMistakes() and if form is valid it stores
 * new user into database.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/newUser")
public class NewUserRegistration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/newUserForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUserForm form = new BlogUserForm();
		form.fromHttpRequest(req);
		BlogUser user = new BlogUser();
		try {
			form.validate();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ignorable) {
		}
		
		if(form.hasMistakes()) {
			storeMistakes(form, req);
			req.getRequestDispatcher("/WEB-INF/pages/newUserForm.jsp").forward(req, resp);
		}
		
		else {
			form.fillUser(user);
			boolean succesful = DAOProvider.getDAO().newUser(user);
			if(!succesful) {
				req.getServletContext().setAttribute("registration.mistakes.nick", "User with this nick exist!");
				req.getRequestDispatcher("/WEB-INF/pages/newUserForm.jsp").forward(req, resp);
			}
			else {
				//TODO: uspjesna registracija!!
				resp.sendRedirect(req.getContextPath());
			}
		}
	}
	
	
	/**
	 * Method that stores mistake detected in BlogUserForm so page can 
	 * display to the user all mistakes he made during registration process.
	 * 
	 * @param form
	 * @param req
	 */
	private void storeMistakes(BlogUserForm form, HttpServletRequest req) {
		req.getServletContext().setAttribute("registration.mistakes.fName", form.getMistake("firstName"));
		req.getServletContext().setAttribute("registration.mistakes.lName", form.getMistake("lastName"));
		req.getServletContext().setAttribute("registration.mistakes.email", form.getMistake("email"));
		req.getServletContext().setAttribute("registration.mistakes.pass", form.getMistake("password"));
		req.getServletContext().setAttribute("registration.mistakes.nick", form.getMistake("nick"));
	}
}
