package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Interface that will handle returning the objects from collection.
 * It implements two methods, one for returning the next Element, and other 
 * that checks whether we have next element.
 * 
 * @author Marko
 * @param <T> type of elements that getter should generate
 *
 */
public interface ElementsGetter<T> {
	
	/**
	 * Method that checks if collection has more elements that getter didn't if there are not changes
	 * made to collection.
	 * 
	 * @return {@code true} if there are elements that need to be returned
	 * 		   {@code false} if we returned all elements.
	 * @throws ConcurrentModificationException if there are changes made to collection
	 */
	boolean hasNextElement();
	
	/**
	 * Method that returns the next existing value in collection if there are not changes
	 * made to collection.
	 * 
	 * @return {@code Object} the next value in collection that is still not been returned.
	 * @throws NoSuchElementException if method returned all values from collection
	 * 		   but user still wants something to be returned.
	 * 
	 * @throws ConcurrentModificationException if there are changes made to collection
	 */
	T getNextElement();
	
	/**	
	 * Method that gets the elements from collection and performs some action on all of them.
	 * 
	 * @param p {@code Processor} interface that is capable of performing operations.
	 */
	default void processRemaining(Processor<? super T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
