package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that has functionality like Map, but it can hold more
 * items under one key. Keys have to be instances of the String and
 * and the values will be instances of class ValueWrapper
 * 
 * @author Marko
 *
 */
public class ObjectMultistack {
	
	/**
	 * Private class that is acting like single node
	 * in linked list.
	 * 
	 * @author Marko
	 *
	 */
	private static class MultistackEntry {
		
		private ValueWrapper value;
		private MultistackEntry next;
		
		
		/**
		 * Constructor.
		 *
		 * @param value {@code ValueWrapper} value that is entry to stack set to
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
		
		
	}
	
	private Map<String, MultistackEntry> mapOfStacks;
	
	
	/**
	 * Constructor. Initializes map with list of {@code ValueWrapper}.
	 */
	public ObjectMultistack() {
		this.mapOfStacks = new HashMap<String, MultistackEntry>();
	}
	
	
	/**
	 * Method that adds the value to end of the list 
	 * contained under key keyName.
	 * 
	 * @param keyName {@code String} key under which we get the list of values
	 * @param valueWrapper {@code ValueWrapper} 
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		
		MultistackEntry currentTopValue = mapOfStacks.get(keyName);
		mapOfStacks.put(keyName, new MultistackEntry(valueWrapper, currentTopValue));
		
	}
	
	
	/**
	 * Method that returns the last value in list 
	 * contained under key keyName and removes that value from
	 * the list.
	 * 
	 * @param keyName {@code String} key under which we get the list of values
	 * @return {@code ValueWrapper} last value in that list
	 */
	public ValueWrapper pop(String keyName) {
		
		MultistackEntry currentTopValue = mapOfStacks.get(keyName);
		if(currentTopValue == null) {
			throw new EmptyStackException();
		}
		
		mapOfStacks.put(keyName, currentTopValue.next);
		return currentTopValue.value;

	}
	
	
	/**
	 * Method that returns the last value in list under
	 * key keyName but it doesn't remove it.
	 * 
	 * @param keyName {@code String} key under which we get the list of values
	 * @return {@code ValueWrapper} last value in that list
	 */
	public ValueWrapper peek(String keyName) {
		
		MultistackEntry currentTopValue = mapOfStacks.get(keyName);
		if(currentTopValue == null) {
			throw new EmptyStackException();
		}
		
		return currentTopValue.value;
		
	}
	
	
	/**
	 * Method that checks if the list under key keyName 
	 * is empty.
	 *  
	 * @param keyName {@code String} key under which we get the list of values
	 * @return {@code true} if the list is empty
	 * 		   {@code false} if the list has at least one value
	 */
	public boolean isEmpty(String keyName) {
		
		if(mapOfStacks.get(keyName) != null) {
			return false;
		}
		else {
			return true;
		}
		
	}

}
