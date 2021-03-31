package hr.fer.zemris.blog.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.blog.model.BlogComment;
import hr.fer.zemris.blog.model.BlogEntry;
import hr.fer.zemris.blog.model.BlogUser;

/**
 * Interface that offers methods for connecting and communicating with
 * database.
 * 
 * @author Marko
 *
 */
public interface DAO {
	
	/**
	 * Method that returns the BlogEntry registered in database with 
	 * id provided as argument.
	 * 
	 * @param id Id of the blog entry that should be returned.
	 * @return {@code BlogEntry}
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Method that returns BlogUser registered in database with 
	 * id provided as argument.
	 * 
	 * @param id Id of registered blog user that should be returned.
	 * @return {@code BlogUser}
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Method that returns BlogUser registered in database with 
	 * nick provided as argument.
	 * 
	 * @param nick nickname of registered blog user that should be returned
	 * @return {@code BlogUser}
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Method that saves new user that was created by {@code BlogUserForm}
	 * if the user can be saved to the database.
	 * 
	 * @param user User to be saved
	 * @return {@code true} if user was successfully saved to database
	 * 		   {@code false} if there was any problem during saving the user to database
	 * @throws DAOException
	 */
	public boolean newUser(BlogUser user) throws DAOException;
	
	/**
	 * Method returns all registered users as List
	 * 
	 * @return {@code List}
	 * @throws DAOException
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	
	
	/**
	 * Method that returns all blog entries one author with provided nickname
	 * as argument.
	 * 
	 * @param nick nickname of author which entries should be returned
	 * @return {@code List} list of all blog entries
	 * @throws DAOException
	 */
	public List<BlogEntry> getAuthorsEntries(String nick) throws DAOException;
	
	
	/**
	 * Method that registers new blog into database under nickname provided as argument 
	 * nick.
	 * 
	 * @param req {@code HttpServletRequest} request from which this method creates a blog 
	 * entry
	 * @param nickname {@code String} nickname of the creator that made blog entry
	 * @throws DAOException
	 */
	public void registerNewEntry(HttpServletRequest req, String nickname) throws DAOException;
	
	
	/**
	 * Method that returns all comments made under blog with id provided as argument
	 * id
	 * 
	 * @param id {@code Long} id of the entry for which comments should be returned
	 * @return {@code List} list of comments made for one blog entry
	 * @throws DAOException
	 */
	public List<BlogComment> getComments(Long id) throws DAOException;
	
	
	/**
	 * Method that registers new comment into database, this comment is 
	 * made under blog entry id provided as argument id
	 * 
	 * @param req {@code HttpServletRequest} from which method creates a comment
	 * @param entryId {@code Long} id of the entry for which this comment was made
	 * @throws DAOException
	 */
	public void registerNewComment(HttpServletRequest req, Long entryId) throws DAOException;

}
