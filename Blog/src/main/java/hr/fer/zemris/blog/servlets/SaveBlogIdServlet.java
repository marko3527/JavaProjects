package hr.fer.zemris.blog.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet that stores ID of blog entry that is currently being
 * edited into sessions attribute so changes can be applied after submiting
 * them.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/saveBlogId/*")
public class SaveBlogIdServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		String[] parameters = path.split("/");
		req.getSession().setAttribute("selectedBlogId", Long.parseLong(parameters[2]));
		req.setAttribute("nickname", parameters[1]);
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + 
						  parameters[1] + "/" + parameters[2]);
	}
}
