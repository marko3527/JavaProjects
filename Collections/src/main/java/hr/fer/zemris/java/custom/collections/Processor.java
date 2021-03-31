package hr.fer.zemris.java.custom.collections;


/**
 * The interface that represent an object which is capable of performing some operation on provided object
 * It defines just one method that should be overridden, as in this class it has no implementation 
 * 
 * @author Marko
 * @param <T> type of value that processor should perform operation on
 *
 */
public interface Processor<T> {
	
	
	/**
	 * 
	 * Generic method, with empty body, that is used to represent a "rule" on how to 
	 * perform operation on object when we know which operation we will perform
	 * Argument is not supposed to be null
	 * 
	 * @param value provided object to perform an operation on 
	 * 
	 * @throws NullPointerException if the given argument is null
	 */
	public void process(T value);

}
