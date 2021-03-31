package hr.fer.zemris.java.voting.dao;

import hr.fer.zemris.java.voting.dao.sql.SQLDAO;

/**
 * Singleton class that offers the implementation of DAO interface
 * to communicate with database.
 * 
 * @author Marko
 *
 */
public class DAOProvider {
	
	private static DAO dao = new SQLDAO();
	
	
	/**
	 * Returns the reference to the DAO interface.
	 * 
	 * @return
	 */
	public static DAO getDAO() {
		return dao;
	}

}
