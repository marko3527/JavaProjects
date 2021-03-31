package hr.fer.zemris.java.voting.filters;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import hr.fer.zemris.java.voting.dao.sql.SQLConnectionProvider;


/**
 * Filters that catches all request that have url 'servleti/*' and
 * sets the connection to database by calling method from SQLConnectionProvider
 * for setting connection. The connection that is being set is one that has
 * been grabbed from the polled data source that is stored under servlet context
 * attributes with key 'hr.fer.zemris.dbpool'
 * 
 * @author Marko
 *
 */
@WebFilter(filterName="f1", urlPatterns={"/servleti/*"})
public class ConnectionSetterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	
	/**
	 * {@inheritDoc}
	 * If the connection from pooled data source is not available that means
	 * database is unavailable at the moment and filter then throw IOException
	 * indicating that the database is not available else it sets the local thread
	 * value of SQLConnectionProvider to connection fetched from pooled data source.
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		DataSource ds = (DataSource)request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try {
				con.close();
			} catch (SQLException ignorable) {
				
			}
		}
		
	}

	@Override
	public void destroy() {
		
	}
	

}
