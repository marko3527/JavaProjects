package hr.fer.zemris.blog.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.blog.jpa.JPAEMFProvider;

/**
 * Web listener that handles the creation and closing of EntityManagerFactory.
 * 
 * @author Marko
 *
 */
@WebListener
public class BlogInitListener implements ServletContextListener{

	/**
	 * {@inheritDoc}
	 * Creates entityManagerFactory and sets it as value of attribute
	 * of servlet context so it can be grabbed when context is being destroyed.
	 *  
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("blog.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	
	/**
	 * {@inheritDoc}
	 * Grabs the entity manager factory from servlet context attributes and
	 * if it's different than null, performs closing of it.
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory)sce.getServletContext().getAttribute("blog.emf");
		
		if(emf != null) {
			emf.close();
		}
	}
}
