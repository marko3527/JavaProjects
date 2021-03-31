package hr.fer.zemris.java.voting.dao;

import java.util.List;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.Poll;

/**
 * Interface that offers methods for connecting to database and fetching
 * all sorts of data.
 * 
 * @author Marko
 *
 */
public interface DAO {
	
	
	/**
	 * Method that connects to databse and returns the list of all options
	 * that have the corresponding poll id.
	 * 
	 * @param pollId {@code long} id of the poll for which we want to display options
	 * @return
	 * @throws DAOException
	 */
	public List<Option> getOptionList(long pollId) throws DAOException;
	
	
	/**
	 * Method that returns one option of poll that has corresponding poll id
	 * and id.
	 * 
	 * @param id of the option 
	 * @param pollId id of the poll that option belongs to
	 * @return
	 * @throws DAOException
	 */
	public Option getOption(long id, long pollId) throws DAOException;
	
	
	/**
	 * Method that returns one poll with corresponding pollId
	 * 
	 * @param pollId {@code long} id of the poll that needs to be fetched from database
	 * @return
	 * @throws DAOException
	 */
	public Poll getPoll(long pollId) throws DAOException;

	
	/**
	 * Method that returns the list of all polls that user
	 * can select to see available options for polls and vote for it.
	 * 
	 * @return {@code List<Poll>}
	 */
	public List<Poll> getPolls();
	
	
	public void writeVote(Option option, long pollId);

}
