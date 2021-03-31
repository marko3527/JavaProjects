package hr.fer.zemris.java.custom.collections;

/**
 * Interface that extends interface Collection and is defining four new methods
 * that are being used in orded to work with collections. 
 * 
 * @author Marko
 *
 */
public interface List extends Collection {
	
	/**
	 * Searches the collection to find the object at {@code index}.
	 * 
	 * @param index at where the wanted object should be 
	 * @return the object at given index
	 * @throws IndexOutOfBoundsException if the index is not valid
	 */
	Object get(int index);
	
	/**
	 * Method that inserts object to given position while keeping all other objects, so
	 * it shifts all of objects with index greater than the given position to the right.
	 * 
	 * @param value to be inserted
	 * @param position of object in collection that is going to be inserted
	 * 
	 * @throws IndexOutOfBoundsException if index is not within the accepted bounds
	 * @throws NullPointerException if object that is supposed to be inserted is null reference
	 */
	void insert(Object value, int position);
	
	/**
	 * Searches the collection to return the index of given object, first it checks
	 * if given object exist in collection and if it does then it searches the collection 
	 * to find at which element it is.
	 * 
	 * @param value object to find an index of
	 * @return index of given object in collection or -1 if object can't be found
	 */
	int indexOf(Object value);
	
	
	/**
	 * Removes the object at given index and shifts all others objects, that have index
	 * larger than given index, to the left.
	 * 
	 * @param index of the collection from where we want to remove object
	 * @throws IndexOutOfBoundsException if index is not within the accepted bounds
	 */
	void remove(int index);
}
