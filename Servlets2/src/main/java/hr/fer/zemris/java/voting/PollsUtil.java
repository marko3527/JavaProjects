package hr.fer.zemris.java.voting;

import java.util.ArrayList;
import java.util.List;


/**
 * PollsUtil offers method for loading all polls from the text file 
 * 'polls.txt' and returning them as list.
 * 
 * @author Marko
 *
 */
public class PollsUtil {
	
	private List<Poll> polls = new ArrayList<>();
	
	
	/**
	 * Contstructor. It takes lines of the text file 'polls.txt' 
	 * and calls the method processAttribute().
	 * 
	 * @param lines
	 */
	public PollsUtil(List<String> lines) {
		processAttributes(lines);
	}
	
	
	/**
	 * Method that takes the lines of the text file 'polls.txt' and 
	 * makes the list of available polls.
	 * 
	 * @param lines
	 */
	private void processAttributes(List<String> lines) {
		for(String line : lines) {
			String[] attributes = line.split("\t");
			polls.add(new Poll(attributes[0], attributes[1], attributes[2]));
		}
	}
	
	
	/**
	 * Method that returns the list of available polls.
	 * 
	 * @return
	 */
	public List<Poll> getPolls() {
		return polls;
	}

}
