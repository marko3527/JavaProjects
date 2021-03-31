package hr.fer.zemris.java.hw05.db.filters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Interface for filtering the student records.
 * 
 * @author Marko
 *
 */
public interface IFilter {
	
	/**
	 * Method that checks whether the given student record should pass through filter.
	 * 
	 * @param record {@code StudentRecord}
	 * @return {@code true} if the given record passed through filter
	 * 		   {@code false} if the given record didn't pass through filter
	 */
	public boolean accepts(StudentRecord record);

}
