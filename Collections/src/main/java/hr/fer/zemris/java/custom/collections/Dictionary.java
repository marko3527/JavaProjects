package hr.fer.zemris.java.custom.collections;

/**
 * Dictionary is a set of entrys that have a key and a value. One key
 * has one value. It relys on adapting the ArrayIndexedCollection.
 * It provides methods for storing and removing entrys from dictionary.
 * 
 * @author Marko
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K,V> {

	
	/**
	 * Private class that is modelling one entry in a dictionary.
	 * It has a key and a value.
	 * 
	 * @author Marko
	 *
	 * @param <K> parameter that is used as a key
	 * @param <V> parameter that is used as value
	 */
	private static class Pair<K,V>{
		private K key;
		private V value;
		
		public Pair(K key, V value){
			if(key == null) {
				throw new NullPointerException("Key can't be null!");
			}
			this.key = key;
			this.value = value;
		}
	}
	
	private ArrayIndexedCollection<Pair<K,V>> collectionOfEntrys;
	
	/**
	 * Empty constructor. It initializes the array of entrys.
	 * 
	 */
	public Dictionary(){
		collectionOfEntrys = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	
	/**
	 * Method that returns a boolean value representing whther the 
	 * dictionary is empty or not.
	 * 
	 * @return {@code true} if dictionary is empty
	 * 		   {@code false} if dictionary is not empty
	 */
	public boolean isEmpty() {
		return collectionOfEntrys.isEmpty();
	}
	
	
	/**
	 * Method that returns the number of entrys in dictionary.
	 * 
	 * @return {@code int} number of entrys
	 */
	public int size() {
		return collectionOfEntrys.size();
	}
	
	
	/**
	 * Method that removes all entry from dictionary, leaving the dictionary empty.
	 * 
	 */
	public void clear() {
		collectionOfEntrys.clear();
	}
	
	
	/**
	 * Method that adds a new entry to the dictionary.
	 * 
	 * @param key of the entry, the value by which entry will be looked for
	 * @param value of the entry
	 */
	public void put(K key, V value) {
		Pair<K, V> entry = new Pair<>(key, value);
		collectionOfEntrys.add(entry);
	}
	
	
	/**
	 * Method that grabs the array of entrys and it searches it to find
	 * the provided key. If that key doesn't exist it returns null and else 
	 * it returns the value that stored under provided key. 
	 * 
	 * @param key {@code Object} a key for which value we are searching
	 * @return the value stored under the key
	 */
	public V get(Object key) {
		for(int i = 0; i < size(); i++) {
			Pair<K, V> entry = collectionOfEntrys.get(i);
			if(entry.key.equals(key)) {
				return entry.value;
			}
		}
		return null;
	}

}
