package hr.fer.zemris.java.voting.dao.sql;

import java.sql.Connection;


/**
 * Class that has two static methods for setting the connection to database and
 * one for getting that same connection. The connection is being set 
 * at initalization of the web app context.
 * 
 * @author Marko
 *
 */
public class SQLConnectionProvider {
	
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	
	/**
	 * If the connection is null it removes the current thread's value
	 * for 'connections' and else it sets it to 'con' provided in 
	 * agrument.
	 * 
	 * @param con
	 */
	public static void setConnection(Connection con) {
		if(con == null) {
			connections.remove();
		}
		else {
			connections.set(con);
		}
	}
	
	
	/**
	 * Returns the current local thread's value.
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}
