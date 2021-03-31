package hr.fer.zemris.blog.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.blog.dao.DAO;
import hr.fer.zemris.blog.dao.DAOException;
import hr.fer.zemris.blog.dao.DAOProvider;
import hr.fer.zemris.blog.model.BlogComment;
import hr.fer.zemris.blog.model.BlogEntry;
import hr.fer.zemris.blog.model.BlogUser;

/**
 * Specific implementation of interface DAO.
 * 
 * @author Marko
 *
 */
public class JPADAOImpl implements DAO{

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			BlogUser user = (BlogUser) em.createQuery("FROM BlogUser b WHERE b.nick = :nickname")
					.setParameter("nickname", nick)
					.getSingleResult();
			return user;
		}catch (NoResultException e) {
			return null;
		}
	}
	
	
	@Override
	public boolean newUser(BlogUser user) throws DAOException {
		BlogUser userWithNick = getBlogUser(user.getNick());
		
		if(userWithNick == null) {
			user.setBlogEntries(new ArrayList<BlogEntry>());
			EntityManager em = JPAEMProvider.getEntityManager();
			em.persist(user);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogUser> users = em.createQuery("FROM BlogUser").getResultList();
		return users;
	}
	
	@Override
	public List<BlogEntry> getAuthorsEntries(String nick) throws DAOException {
		BlogUser user = getBlogUser(nick);
		return user.getBlogEntries();
	}
	
	@Override
	public List<BlogComment> getComments(Long id) throws DAOException {
		BlogEntry entry = getBlogEntry(id);
		return entry.getComments();
	}
	
	
	@Override
	public void registerNewEntry(HttpServletRequest req, String nickname) throws DAOException {
		String title = req.getParameter("title");
		String message = req.getParameter("text");
		
		BlogUser creator = DAOProvider.getDAO().getBlogUser(nickname);
		
		BlogEntry entry = new BlogEntry();
		entry.setComments(new ArrayList<BlogComment>());
		entry.setText(message);
		entry.setTitle(title);
		entry.setCreator(creator);
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(new Date());
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		creator.getBlogEntries().add(entry);
		
		em.persist(entry);
	}
	
	@Override
	public void registerNewComment(HttpServletRequest req, Long entryId) throws DAOException {
		BlogEntry entry = getBlogEntry(entryId);
		String text = (String)req.getParameter("text");
		
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(entry);
		comment.setMessage(text);
		comment.setPostedOn(new Date());
		
		String nickname = (String) req.getSession().getAttribute("current.user.nick");
		if(nickname != null) {
			BlogUser commentCreator = getBlogUser(nickname);
			comment.setUsersEMail(commentCreator.getEmail());
		}
		else {
			comment.setUsersEMail("unknown");
		}

		entry.getComments().add(comment);
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}
}
