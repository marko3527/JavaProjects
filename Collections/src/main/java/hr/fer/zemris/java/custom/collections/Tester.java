package hr.fer.zemris.java.custom.collections;

/**
 * Interface that checks whether the object is acceptable or not.
 * It implements just one method that handles all the work.
 * 
 * @author Marko
 * @param <T> the type of the tested object
 *
 */
public interface Tester<T> {
	
	/**
	 * 
	 * @param obj that should be tested if acceptable.
	 * @return {@code true} if object is acceptable
	 * 		   {@code false} if object is not acceptable
	 */
	boolean test(T obj);

}
