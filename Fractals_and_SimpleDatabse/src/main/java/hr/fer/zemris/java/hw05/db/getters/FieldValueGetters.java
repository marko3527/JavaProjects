package hr.fer.zemris.java.hw05.db.getters;

/**
 * Class that implements concrete strategies for obtaining 
 * requested field from student record. It defines three static final
 * variables, one for each field in student record.
 * 
 * @author Marko
 *
 */
public class FieldValueGetters {
	
	
	/**
	 * Returns the first name of given student record.
	 */
	public final static IFieldValueGetter FIRST_NAME = record -> {
		return record.getFirstName();
	};
	
	/**
	 * Returns the last name of given student record.
	 */
	public final static IFieldValueGetter LAST_NAME = record -> {
		return record.getLastName();
	};
	
	/**
	 * Returns the jmbag of given student record.
	 */
	public final static IFieldValueGetter JMBAG = record -> {
		return record.getJmbag();
	};
}
