package hr.fer.zemris.java.hw05.db;

/**
 * Class that represent record for one student and stores information about 
 * his jmbag, first and last name and his final grade.
 * 
 * @author Marko
 *
 */
public class StudentRecord {
	
	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;
	
	
	/**
	 * Constructor. If user tries to make record with finalGrade smaller than 1 or greater than 5 
	 * it prints out the appropriate message to the user and exists the program.
	 * 
	 * @param jmbag {@code String} 
	 * @param firstName {@code String} name of the student
	 * @param lastName {@code String} last name of the student
	 * @param finalGrade {@code int} student's final grade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.finalGrade = finalGrade;
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return final grade for student record
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return firstName for student record
	 */
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return jmbag for student record
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return last name for student record
	 */
	public String getLastName() {
		return lastName;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		
		if(this.jmbag.equals(other.getJmbag())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
}
