package hr.fer.zemris.java.voting;

import java.util.ArrayList;
import java.util.List;


/**
 * Util that offers method for reading the text file with options available for voting.
 * It also offers methods for calculating the option that has the highest number
 * of votes.
 * 
 * @author Marko
 *
 */
public class OptionsUtil {
	
	private List<String> optionsAttributes;
	
	private List<Option> options = new ArrayList<>();
	
	
	/**
	 * Constructor. It takes the lines held in 'options-poll.txt' 
	 * and calls the method processAttributes() which will make a list
	 * of options.
	 * 
	 * @param optionAttributes
	 */
	public OptionsUtil(List<String> optionAttributes) {
		this.optionsAttributes = optionAttributes;
		processAttributes();
	} 
	
	
	/**
	 * Method that makes list of option based on the lines in the
	 * text fie options-poll.txt 
	 */
	private void processAttributes() {
		for(String line : optionsAttributes) {
			String[] attributes = line.split("\\t");
			options.add(new Option(attributes[1], attributes[0], attributes[2], attributes[3]));
		}
	}
	
	
	
	
	/**
	 * Method that returns the list of options that have the highest
	 * number of votes.
	 * 
	 * @return {@code List<Option>}
	 */
	public static List<Option> optionsWithMostVotes(List<Option> options) {
		int mostVotes = findLargestNumberOfVotes(options);
		List<Option> optionsWithMostVotes = new ArrayList<>();
		
		for(Option option : options) {
			if(option.getNumberOfVotes() == mostVotes) {
				optionsWithMostVotes.add(option);
			}
		}
		
		return optionsWithMostVotes;
	}
	
	
	/**
	 * Method that finds the number that represents the
	 * highest number of votes in list of options.
	 * 
	 * @return {@code int} highest number of votes.
	 */
	private static int findLargestNumberOfVotes(List<Option> options) {
		int mostVotes = options.get(0).getNumberOfVotes();
		for(Option option : options) {
			if(option.getNumberOfVotes() > mostVotes) {
				mostVotes = option.getNumberOfVotes();
			}
		}
		return mostVotes;
	}
	
	
	/**
	 * Getter. Returns the list of all available options for this voting 
	 * application.
	 * 
	 * @return
	 */
	public List<Option> getOptions() {
		return options;
	}
	

}
