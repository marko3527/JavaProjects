package hr.fer.zemris.java.hw05.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.db.filters.IFilter;

/**
 * Class that takes the list of lines written in text file and creates a list of 
 * studen records with an index for fast retrieval of records.
 * 
 * @author Marko
 *
 */
public class StudentDatabase {
	
	private List<StudentRecord> listOfRecords;
	private Map<String, StudentRecord> dataBaseIndex;
	
	/**
	 * Constructor. It creates a list of records from stream of database rows and 
	 * creates a hash map that represents index of database for fast retrieval.
	 * 
	 * @param dataBaseRows {@code List} rows of the database written in txt. file 
	 */
	public StudentDatabase(List<String> dataBaseRows) {
		listOfRecords = new LinkedList<StudentRecord>();
		dataBaseIndex = new HashMap<String, StudentRecord>();
		
		dataBaseRows.stream().forEach(row -> {
			String[] splitRow = row.split("\\s+");
			String jmbag = splitRow[0];
			String lastName = "";
			String firstName = "";
			int finalGrade = 0; 
			
			if(dataBaseIndex.containsKey(jmbag)) {
				System.out.println("In the database can't be two records of student with jmbag " + 
								   jmbag);
				System.exit(1);
			}
			
			if(splitRow.length == 4) {
				lastName = splitRow[1];
				firstName = splitRow[2];
				finalGrade = Integer.parseInt(splitRow[3]);
			}
			
			else if(splitRow.length == 5) {
				lastName = splitRow[1] + " " + splitRow[2];
				firstName = splitRow[3];
				finalGrade = Integer.parseInt(splitRow[4]);
			}
			
			if(finalGrade < 1 || finalGrade > 5) {
				System.out.println("The final grade for student " + firstName + " " + lastName + 
								   " is not within bounds, so it is smaller than 1 or larger than 5!");
				System.out.println("The program is now stopping!");
				System.exit(1);
			}
			
			StudentRecord newRecord = new StudentRecord(jmbag, firstName, lastName, finalGrade);
			listOfRecords.add(newRecord);
			dataBaseIndex.put(jmbag, newRecord);
		});
	}
	
	/**
	 * Constructor that takes already made list of student records and computes 
	 * a index on jmbag.
	 * 
	 * @param records {@code LinkedList}
	 */
	public StudentDatabase(LinkedList<StudentRecord> records) {
		
		if(records == null) {
			throw new NullPointerException("Can't accept null reference to list of records!");
		}
		
		dataBaseIndex = new HashMap<String, StudentRecord>();
		this.listOfRecords = records;
		for(StudentRecord record : records) {
			dataBaseIndex.put(record.getJmbag(), record);
		}
	}
	
	
	/**
	 * Method that takes advantage of using index for database. So for given
	 * jmbag it checks whether that jmbag exists in map and if it doesn't returns null
	 * and if it does then returns the Studen record stored as value for that jmbag.
	 * 
	 * @param jmbag {@code String} key to search the value of
	 * @return {@code null} if this jmbag does not exist in the database else
	 * 		   {@code StudentRecors} if jmbag exists and it found the value for the key
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(dataBaseIndex.containsKey(jmbag)) {
			return dataBaseIndex.get(jmbag);
		}
		return null;
	}
	
	
	/**
	 * Method that filters the database records through given filter.
	 * 
	 * @param filter {@code IFilter} filter with whom method tests if the record is acceptable
	 * @return {@code List} records that are accepted by the filter.
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> acceptedRecords = new LinkedList<>();
		
		listOfRecords.stream().forEach(record -> {
			if(filter.accepts(record)) {
				acceptedRecords.add(record);
			}
		});
		
		return acceptedRecords;
	}

}
