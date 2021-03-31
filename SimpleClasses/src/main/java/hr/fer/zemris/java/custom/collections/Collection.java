package hr.fer.zemris.java.custom.collections;


/**
 * Class {@code Collection} represents general collection of objects. 
 * It implements methods that help with adding objects to collection,
 * removing them and others that give useful informations like if the 
 * collection is empty, or size of the collection.
 * 
 * @author Marko
 *
 */
public class Collection {
	
	
	/**
	 * Method for checking whether the collection has any objects in it.
	 * 
	 * @return {@code true} if collection has no objects and 
	 * 		   {@code false} if collection holds one or more objects
	 */
	public boolean isEmpty() {
		
		if(size() == 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Method counts the number of objects held in particular collection.
	 * 
	 * @return number that indicates count of objects in collection
	 */
	public int size() {
		return 0;
	}
	
	
	/**
	 * Definition of method that is going to add object to existing collection.
	 * Argument shouldn't be {@code null}.
	 * 
	 * @param value the object that is supposed to be added to collection 
	 * 
	 * @throws NullPointerException if the given argument is null
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to collection! ");
		}
	}
	
	
	/**
	 * Method that checks whether the object exist in existing collection.
	 * Argument shouldn't be {@code null}.
	 * 
	 * @param value object for which we are checking if it exists in collection
	 * @return {@code true} if object is in collection and 
	 * 		   {@code false} if object does not exist in collection
	 * 
	 * @throws NullPointerException if the given argument is null
	 * 
	 */
	public boolean contains(Object value) {
		if(value == null) {
			throw new NullPointerException("I can't check if the value 'null' exists in collection!");
		}
		return false;
	}
	
	
	/**
	 * Method that removes provided object from collection of objects.
	 * Argument shouldn't be {@code null}.
	 * 
	 * @param value the object that should be removed from the collection 
	 * @return {@code true} if object is successfully removed and 
	 * 		   {@code false} if method encountered some kind of problem during the removal of object
	 * 
	 * @throws NullPointerException if the given argument is null
	 * 
	 */
	public boolean remove(Object value) {
		if(value == null) {
			throw new NullPointerException("I can't remove value 'null' from collection!");
		}
		return false;
	}
	
	
	/**
	 * Take the collection of objects and returns it as array of objects
	 * 
	 * @return array data type of objects that are currently in the existing collection
	 * @throws UnsupportedOperationException if it is not able to perform this operation.
	 * 
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("I am not able to perform this action!");
	}
	
	
	/**
	 * For each object in given collection method is going to call method process of object Processor.
	 * Argument shouln't be null.
	 * 
	 * @param processor the object which will handle adding object to a collection
	 * 
	 * @throws NullPointerException if the given argument is null
	 * 
	 */
	public void forEach(Processor processor) {
		if(processor == null) {
			throw new NullPointerException("You provided 'null' reference! ");
		}
	}
	
	
	/**
	 * Method is adding to the existing collection all elements from given collection.
	 * It defines a local class that extends class Processor and is overriding method process
	 * to add value to existing collection.
	 * Argument is not supposed to be null.
	 * 
	 * @param other is collection of objects that should be added to existing collection
	 * 
	 */
	public void addAll(Collection other) {
		
		/**
		 * Extends class Processor and overriding method process 
		 * to be capable of adding values to existing collection.
		 * 
		 * @author Marko
		 *
		 */
		class LocalProcessor extends Processor{
			
			/**
			 * Performs operation of adding value to an existing collection
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}
		LocalProcessor localProcessor = new LocalProcessor();
		other.forEach(localProcessor);
	}
	
	/**
	 * Method that removes all of the objects in existing collection.
	 */
	public void clear() {
		
	}
	
	

}
