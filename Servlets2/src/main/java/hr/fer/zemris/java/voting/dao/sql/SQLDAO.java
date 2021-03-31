package hr.fer.zemris.java.voting.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.Poll;
import hr.fer.zemris.java.voting.dao.DAO;
import hr.fer.zemris.java.voting.dao.DAOException;

/**
 * Concrete implementation of interface DAO that communicates with the database.
 * 
 * @author Marko
 *
 */
public class SQLDAO implements DAO{

	
	@Override
	public List<Option> getOptionList(long pollId) throws DAOException {
		List<Option> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, votesCount FROM "
									 + "PollOptions WHERE pollID = ?");
			pst.setLong(1, pollId);
			try {
				ResultSet rset = pst.executeQuery();
				try {
					while(rset != null && rset.next()) {
						Option option = new Option();
						option.setId("" + rset.getLong(1));
						option.setName(rset.getString(2));
						option.setLink(rset.getString(3));
						option.setNumberOfVotes(rset.getInt(4));
						options.add(option);
					}
				} finally {
					try {
						rset.close();
					} catch (Exception ignorable) {
						
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
					
				}
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", e);
		}
		return options;
	}

	
	@Override
	public Option getOption(long id, long pollId) throws DAOException {
		Option option = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, votesCount FROM "
					 				 + "PollOptions WHERE id = ? AND pollID = ?");
			pst.setLong(1, id);
			pst.setLong(2, pollId);
			try {
				ResultSet rset = pst.executeQuery();
				try {
					if(rset != null && rset.next()) {
						option = new Option();
						option.setId("" + rset.getLong(1));
						option.setName(rset.getString(2));
						option.setLink(rset.getString(3));
						option.setNumberOfVotes(rset.getInt(4));
						
					}
					
				} finally {
					try {
						rset.close();
					} catch (Exception ignorable) {

					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
	
				}
			}
		} catch (Exception e) {
			throw new DAOException("Fetching user with id =  " + id + " did not succed.", e);
		}
		return option;	
	}

	
	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM "
					 				 + "Polls WHERE id = ?");
			pst.setLong(1, id);
			try {
				ResultSet rset = pst.executeQuery();
				try {
					if(rset != null && rset.next()) {
						poll = new Poll();
						poll.setId("" + rset.getLong(1));
						poll.setTitle(rset.getString(2));
						poll.setMessage(rset.getString(3));
						
					}
					
				} finally {
					try {
						rset.close();
					} catch (Exception ignorable) {

					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
	
				}
			}
		} catch (Exception e) {
			throw new DAOException("Fetching poll with id =  " + id + " did not succed.", e);
		}
		return poll;	
	}
	
	
	@Override
	public List<Poll> getPolls() {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM "
									 + "Polls");
			try {
				ResultSet rset = pst.executeQuery();
				try {
					while(rset != null && rset.next()) {
						Poll poll = new Poll();
						poll.setId("" + rset.getLong(1));
						poll.setTitle(rset.getString(2));
						poll.setMessage(rset.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rset.close();
					} catch (Exception ignorable) {
						
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
					
				}
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", e);
		}
		return polls;
	}
	
	
	@Override
	public void writeVote(Option option, long pollId) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("UPDATE PollOptions "
									 + "SET votesCount = ? "
									 + "WHERE id = ? AND pollId = ?");
			pst.setInt(1, option.getNumberOfVotes());
			pst.setString(2, option.getId());
			pst.setLong(3, pollId);
			
			pst.execute();
		} catch (Exception e) {
			throw new DAOException("Writing vote did not succed!", e);
		}
	}
	
}
