package hr.fer.zemris.java.servleti.glasanje;


/**
 * Class that represents one band for which the user can vote for.
 * Each band has its name, their id, their link to the one of their
 * songs and number of votes currently.
 * 
 * @author Marko
 *
 */
public class Band {
	
	private String name;
	private String id;
	private int numberOfVotes;
	private String link;
	
	
	/**
	 * Constructor. It takes name, id and link of the band.
	 * 
	 * @param name
	 * @param id
	 * @param link
	 */
	public Band(String name, String id, String link) {
		this.name = name;
		this.id = id;
		this.link = link;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return name of the band.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return id of the band.
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return link of the band.
	 */
	public String getLink() {
		return link;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return number of votes for band.
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	
	
	/**
	 * Sets the number of votes for the band.
	 * 
	 * @param numberOfVotes
	 */
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
	
}
