package hr.fer.zemris.java.hw05.db.getters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Interface that offers method for obtaining a requested 
 * field from a student record.
 * 
 * @author Marko
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Method that for given record obtains a requested field form 
	 * that record
	 * 
	 * @param record {@code StudentRecord}
	 * @return {@code String} representation of requested field
	 */
	public String get(StudentRecord record);
}
