package hr.fer.zemris.java.voting;


/**
 * Class that keeps the information about each poll, its id,
 * message and title.
 * 
 * @author Marko
 *
 */
public class Poll {
	
	private String id;
	private String title;
	private String message;
	
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param title
	 * @param message
	 */
	public Poll(String id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Empty contstructor.
	 * 
	 */
	public Poll() {
		
	}

	
	/**
	 * Getter. Return the value of id of the poll.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Setter for id value of the poll.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * Getter. Returns the title of the poll.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	
	/**
	 * Setter for the title value of the poll.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/**
	 * Getter. Returns the message of the poll.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	
	/**
	 * Setter for the message value of the poll.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
