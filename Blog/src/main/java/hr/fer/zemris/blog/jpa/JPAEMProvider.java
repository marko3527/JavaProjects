package hr.fer.zemris.blog.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.blog.dao.DAOException;

/**
 * Provider class which can create a new EntityManager and save it into 
 * {@code ThreadLocal}, after creating EntityManager it will be closed by 
 * {@code JPAFilter} that closes EntityManager saved in ThreadLocal locals
 * 
 * @author Marko
 *
 */
public class JPAEMProvider {
	
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Getter. It checks if there is an instance of EntityManager stored
	 * in {@code ThreadLocal} and returns it else it creates a new instance of 
	 * {@code EntityManager} and returns that instance.
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	
	/**
	 * Method that closes and removes EntityManager saved in {@code ThreadLocal} locals.
	 * This method should be called after all of the work on EntityManager
	 * has been done, it is called by {@code JPAFilter} that filters all
	 * servlets.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
}
