package hr.fer.zemris.blog.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider class that offers two methods, setter and getter for 
 * EntityManagerFactory(emf),it provides an efficient way to construct multiple
 * {@code EntityManager} instances for that database. 
 * 
 * @author Marko
 *
 */
public class JPAEMFProvider {
	
public static EntityManagerFactory emf;
	
	/**
	 * Getter.
	 * 
	 * @return {@code EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter.
	 * 
	 * @param emf {@code EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}

}
