package hr.fer.zemris.blog.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.blog.dao.DAOProvider;
import hr.fer.zemris.blog.model.BlogComment;
import hr.fer.zemris.blog.model.BlogEntry;


/**
 * Servlet that handles all servlets with path /servleti/author/*
 * and depending on the rest of the path renders pages.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/author/*")
public class BlogEntriesServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cases:
	 * 1) path is like /servleti/author/NICK where NICK is nickname of the registered user
	 * 		method renders page blogList.jsp which displays all entries of the user with 
	 * 		nickname NICK
	 * 2) path is like /servleti/author/NICK/* where NICK is nickname of the registere user
	 * 		if path is like /servleti/author/NICK/new method renders page for new blog entry
	 * 		if path is like /servleti/author/NICK/edit method renders page for editing blog entry
	 * 		if path is like /servleti/author/NICK/NUM, where NUM is id of the blog entry method
	 * 			renders page for displaying one blog entry with comments and adding new comments.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getPathInfo().trim();
		String[] params = path.split("/");
		
		if(params.length == 2) {
			String nickname = params[1];
			List<BlogEntry> entries = DAOProvider.getDAO().getAuthorsEntries(nickname);
			
			req.setAttribute("blogEntries", entries);
			req.setAttribute("nickname", nickname);
			req.getRequestDispatcher("/WEB-INF/pages/blogList.jsp").forward(req, resp);
		}
		
		else if(params.length == 3) {
			String nickname = params[1];
			String action =  params[2];
			
			if(action.equals("new")) {
				req.setAttribute("nickname", nickname);
				req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
			}
			else if(action.equals("edit")) {
				Long selectedBlogId = (Long) req.getSession().getAttribute("selectedBlogId");
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(selectedBlogId);
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
			}
			else {
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(action));
				if(entry != null) {
					List<BlogComment> comments = DAOProvider.getDAO().getComments(entry.getId());
					req.setAttribute("comments", comments);
				}
				req.setAttribute("blogEntry", entry);
				req.setAttribute("nickname", params[1]);
				req.getRequestDispatcher("/WEB-INF/pages/oneBlogEntry.jsp").forward(req, resp);
			}
		}
	}
	
	
	/**
	 * 1) path is like /servleti/author/NICK/* where NICK is nickname of the registere user
	 * 		if path is like /servleti/author/NICK/new method calls method for registering 
	 * 			new user into database.
	 * 		if path is like /servleti/author/NICK/edit method gets blogEntryId stored in
	 * 			servlet context and gets BlogEntry with that ID from database and changes attributes
	 * 			depending on the request paramaters.
	 * 		if path is like /servleti/author/NICK/NUM, where NUM is id of the blog entry, method
	 * 			registers new comment under blogEntry into database.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().trim();
		String[] params = path.split("/");
		
		if(params.length == 3) {
			String nickname = params[1];
			String action =  params[2];
			
			if(action.equals("new")) {
				DAOProvider.getDAO().registerNewEntry(req, nickname);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname);
			}
			else if(action.equals("edit")) {
				String title = req.getParameter("title");
				String text = req.getParameter("text");
				Long editedEntryId = (Long)req.getSession().getAttribute("selectedBlogId");
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(editedEntryId);
				entry.setTitle(title);
				entry.setText(text);
				entry.setLastModifiedAt(new Date());
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" +
								  nickname + "/" + editedEntryId);
			}
			else {
				Long entryId = Long.parseLong(action);
				DAOProvider.getDAO().registerNewComment(req, entryId);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nickname + "/" + action);
			}
		}
	}
}
