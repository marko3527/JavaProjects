package hr.fer.zemris.java.voting.listeners;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.OptionsUtil;
import hr.fer.zemris.java.voting.Poll;
import hr.fer.zemris.java.voting.PollsUtil;


/**
 * Listener for web application 'voting-app' that connects to a database 
 * where the votes will be stored. After connecting to database it checks whether
 * the needed tables exist in database and if they are non-existent it creates them
 * and populates them with options from text file options-poll.txt using 
 * OptionsUtil class.
 * 
 * @author Marko
 *
 */
@WebListener
public class InitializeListener implements ServletContextListener{

	/**
	 * When initializing the context of the voting-app it makes a 
	 * connection to database, checks whether the needed tables exist 
	 * and sets the reference to pooled data source under context attribute
	 * under key 'hr.fer.zemris.dbpool'
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String propertiesFile = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Properties dbProperties = new Properties();
		
		try {
			dbProperties.load(Files.newInputStream(Paths.get(propertiesFile)));
			String host = dbProperties.getProperty("host");
			String port = dbProperties.getProperty("port");
			String name = dbProperties.getProperty("name");
			String user = dbProperties.getProperty("user");
			String password = dbProperties.getProperty("password");
			String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name +
								   ";user=" + user + ";password=" + password;
			
			
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e) {
				throw new RuntimeException("Mistake while initializing the pool!", e);
			}
			
			cpds.setJdbcUrl(connectionURL);
			createTablesIfNotExist(cpds,sce);
			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Mistake while reading properties file");
		}
		
		
	}

	
	/**
	 * When the context of the voting-app is being closed 
	 * this method will destroy the poll that holds the connection
	 * to database.
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().
									  getAttribute("hr.fer.zemris.dbpool");
		try {
			DataSources.destroy(cpds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method that calls the methods for creating needed tables for voting 
	 * app to work properly.
	 * 
	 * @param cpds
	 * @param sce
	 */
	private void createTablesIfNotExist(ComboPooledDataSource cpds, ServletContextEvent sce) {
		try {
			Connection con = cpds.getConnection();
			createPolls(con,sce);
			createPollOptions(con,sce);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database is not available!");
			System.exit(0);
		}
	}
	
	
	/**
	 * Method that checks whether the table 'PollOptions' exist in database
	 * and if the answer is no then it creates the table and populates it 
	 * with the data. If the table exist it checks whether it has more than
	 * one option in table 'PollOptions' so the user can use it properly and else if 
	 * populates the table.
	 * 
	 * @param con
	 * @param sce
	 * @throws SQLException
	 */
	private void createPollOptions(Connection con, ServletContextEvent sce) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		ResultSet rset = dbmd.getTables(null, null, "POLLOPTIONS", null);
		if(rset.next()) {
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM PollOptions");
			ResultSet result = pst.executeQuery();
			if(result.next()) {
				if(result.getInt(1) < 2) {
					populatePollOptions(con, sce);
				}
			}
			return;
		}
		else {
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("CREATE TABLE PollOptions"
										 + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
										 + "optionTitle VARCHAR(100) NOT NULL,"
										 + "optionLink VARCHAR(150) NOT NULL,"
										 + "pollID BIGINT,"
										 + "votesCount BIGINT,"
										 + "FOREIGN KEY (pollID) REFERENCES Polls(id))");
				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was a problem with creating tables!");
				System.exit(0);
			}
			populatePollOptions(con, sce);
		}
	}
	
	
	/**
	 * Method for populating table 'PollOptions' with options held in
	 * the file 'options-polls.txt' using class OptionsUtil which offers
	 * the method for returning list of all options.
	 * 
	 * @param con
	 * @param sce
	 */
	private void populatePollOptions(Connection con, ServletContextEvent sce) {
		String optionsFile = sce.getServletContext().getRealPath("/WEB-INF/options-poll.txt");
		try {
			OptionsUtil optionsUtil = new OptionsUtil(Files.readAllLines(Paths.get(optionsFile)));
			List<Option> options = optionsUtil.getOptions();
			for(Option option : options) {
				PreparedStatement pst = null;
				try {
					pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
											 + "(?,?,(SELECT id FROM Polls WHERE title = ?),?)");
					pst.setString(1, option.getName());
					pst.setString(2, option.getLink());
					pst.setString(3, option.getPollTitle());
					pst.setInt(4, option.getNumberOfVotes());
					pst.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Method that checks whether the table 'Polls' exist in database
	 * and if the answer is no then it creates the table and populates it 
	 * with the data. If the table exist it checks whether it has more than
	 * one poll in table 'Polls' so the user can use it properly and else if 
	 * populates the table
	 * 
	 * @param con
	 * @param sce
	 * @throws SQLException
	 */
	private void createPolls(Connection con, ServletContextEvent sce) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		ResultSet rset = dbmd.getTables(null, null, "POLLS", null);
		if(rset.next()) {
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM Polls");
			ResultSet result = pst.executeQuery();
			if(result.next()) {
				if(result.getInt(1) < 2) {
					populatePollOptions(con, sce);
				}
			}
			return;
		}
		else {
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("CREATE TABLE Polls"
										 + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
										 + "title VARCHAR(150) NOT NULL,"
										 + "message CLOB(2048) NOT NULL)");
				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was a problem with creating tables!");
				System.exit(0);
			}
			populatePolls(con, sce);
		}
		
	}
	
	
	/**
	 * Method that populates table 'Polls' with available polls which
	 * are written in text file 'polls.txt' with PollsUtil class which offers
	 * the method for returning list of all polls.
	 * 
	 * @param con
	 * @param sce
	 */
	private void populatePolls(Connection con, ServletContextEvent sce) {
		String pollsFile = sce.getServletContext().getRealPath("/WEB-INF/polls.txt");
		try {
			PollsUtil pollsUtil = new PollsUtil(Files.readAllLines(Paths.get(pollsFile)));
			List<Poll> polls = pollsUtil.getPolls();
			for(Poll poll : polls) {
				PreparedStatement pst = null;
				try {
					pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES"
											 + "(?,?)");
					pst.setString(1, poll.getTitle());
					pst.setString(2, poll.getMessage());
					pst.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
