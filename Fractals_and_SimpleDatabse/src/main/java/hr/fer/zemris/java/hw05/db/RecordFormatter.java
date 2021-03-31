package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that offers method for formatting the list fo records that should be 
 * outputed.
 * 
 * @author Marko
 *
 */
public class RecordFormatter {
	
	/**
	 * Method that finds the largest length of each field in student record
	 * so it can auto scale the table to fit every field in and then produces 
	 * the output.
	 * 
	 * @param records {@code List} records to be printed
	 * @return list of strings, where every string is one line of output
	 */
	public List<String> format(List<StudentRecord> records) {
		List<String> output = new LinkedList<String>();
		
		if(records.size() != 0) {
			int jmbagSpaces = 12;
			int gradeSpaces = 3;
			int firstNameSpaces = findLargestFirstName(records);
			int lastNameSpaces = findLargestLastName(records);
			String border = computeBorder(jmbagSpaces,lastNameSpaces,firstNameSpaces,gradeSpaces);
			output.add(border);
			for(StudentRecord record : records) {
				output.add(computeRecord(jmbagSpaces,lastNameSpaces,firstNameSpaces,gradeSpaces,record));
			}
			output.add(border);
		}
		
		output.add("Records selected: " + records.size());
		return output;
	}
	
	private String computeRecord(int jmbagSpaces, int lastNameSpaces, 
								 int firstNameSpaces, int gradeSpaces,
								 StudentRecord record) {
		
		String output = "| " + record.getJmbag();
		for(int i = 0; i < jmbagSpaces - record.getJmbag().length() - 1; i++) {
			output += " ";
		}
		output += "| " + record.getLastName();
		for(int i = 0; i < lastNameSpaces - record.getLastName().length() - 1; i++) {
			output += " ";
		}
		output += "| " + record.getFirstName();
		for(int i = 0; i < firstNameSpaces - record.getFirstName().length() - 1; i++) {
			output += " ";
		}
		output += "| " + record.getFinalGrade();
		for(int i = 0; i < gradeSpaces - 2; i++) {
			output += " ";
		}
		output += "|";
		return output;
	}

	/**
	 * Method that makes border of the output records.
	 * 
	 * @param jmbagSpaces
	 * @param lastNameSpaces
	 * @param firstNameSpaces
	 * @param gradeSpaces
	 * @return computed border
	 */
	private String computeBorder(int jmbagSpaces, int lastNameSpaces, 
								 int firstNameSpaces, int gradeSpaces) {
		
		String border = "+";
		for(int i = 0; i < jmbagSpaces; i++) {
			border += "=";
		}
		border += "+";
		for(int i = 0; i < lastNameSpaces; i++) {
			border += "=";
		}
		border += "+";
		for(int i = 0; i < firstNameSpaces; i++) {
			border += "=";
		}
		border += "+";
		for(int i = 0; i < gradeSpaces; i++) {
			border += "=";
		}
		border += "+";
		return border;
	}

	/**
	 * Finds the largest length of the first name in 
	 * records to be outputed
	 * 
	 * @return length of the largest first name in data base
	 */
	private int findLargestFirstName(List<StudentRecord> records) {
		int max = records.get(0).getFirstName().length();
		
		for(StudentRecord record : records) {
			int len = record.getFirstName().length();
			if(len > max) {
				max = len;
			}
		}
		
		return max + 2;
	}
	
	
	/**
	 * Finds the largest length of the last name in 
	 * records to be outputed
	 * 
	 * @return length of the largest first name in data base
	 */
	private int findLargestLastName(List<StudentRecord> records) {
		int max = records.get(0).getLastName().length();
		
		for(StudentRecord record : records) {
			int len = record.getLastName().length();
			if(len > max) {
				max = len;
			}
		}
		
		return max + 2;
	}

}
