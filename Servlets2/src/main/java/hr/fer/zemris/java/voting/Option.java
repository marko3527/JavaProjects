package hr.fer.zemris.java.voting;


/**
 * Class that represents one option for which the user can vote for.
 * Each option consist of the name or title of the option, its id. The
 * link for the option can be used to display the pictures of that option,
 * it has  number of votes and a title of the poll to which option belongs.
 * 
 * @author Marko
 *
 */
public class Option {
	
	private String name;
	private String id;
	private int numberOfVotes;
	private String link;
	private String pollTitle;
	
	
	/**
	 * Empty constructor.
	 * 
	 */
	public Option() {
		
	}
	
	
	/**
	 * Constructor. It takes name, id and link of the option.
	 * 
	 * @param name
	 * @param id
	 * @param link
	 */
	public Option(String name, String id, String link, String pollTitle) {
		this.name = name;
		this.id = id;
		this.link = link;
		this.pollTitle = pollTitle;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return name of the option.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return id of the option.
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return link of the option.
	 */
	public String getLink() {
		return link;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return number of votes for option.
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	
	
	/**
	 * Getter. Returns the title of the poll to which option belongs.
	 * 
	 * @return
	 */
	public String getPollTitle() {
		return pollTitle;
	}
	
	
	
	/**
	 * Setter for the id of the option.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * Setter for the link of option.
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	
	/**
	 * Setter for the name of option.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Sets the number of votes for the option.
	 * 
	 * @param numberOfVotes
	 */
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
	
	/**
	 * Setter for the title of the poll to which it belongs.
	 * 
	 * @param pollTitle
	 */
	public void setPollId(String pollTitle) {
		this.pollTitle = pollTitle;
	}
	
	
	@Override
	public String toString() {
		return name;
	}
	
}
