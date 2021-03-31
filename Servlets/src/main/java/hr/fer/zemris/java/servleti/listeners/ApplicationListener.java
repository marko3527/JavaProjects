package hr.fer.zemris.java.servleti.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * Listener that stores the value of time when the application
 * has been started under servlet contexts attributes.
 * 
 * @author Marko
 *
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long miliseconds = System.currentTimeMillis();
		sce.getServletContext().setAttribute("miliseconds", miliseconds);
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
