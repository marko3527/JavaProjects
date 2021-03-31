package hr.fer.zemris.blog.dao;

import hr.fer.zemris.blog.jpa.JPADAOImpl;

/**
 * Singleton class that offers the implementation of DAO interface
 * to communicate with database.
 * 
 * @author Marko
 *
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Method that returns one and only instance of DAO interface.
	 * 
	 * @return
	 */
	public static DAO getDAO() {
		return dao;
	}
}
