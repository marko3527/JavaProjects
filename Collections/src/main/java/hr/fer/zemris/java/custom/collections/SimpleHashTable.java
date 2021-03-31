package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Class that represents a table of diffuse addressing based on hash values. 
 * Each entry in table is a (key,value) pair. The slot to put a entry in is calculated based
 * on hash value of the key.
 * There are 2 constructors.
 * 
 * @author Marko
 *
 * @param <K> type of the key
 * @param <V> value type of one entry
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K,V>>{
	
	
	/**
	 * Class that represents one entry in a hash table. It has 
	 * a key and a value and reference to the next item in the current slot.
	 * 
	 * @author Marko
	 *
	 * @param <K> type of the key
	 * @param <V> type of the value
	 */
	public static class TableEntry<K, V>{
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		
		/**
		 * Constructor.
		 * 
		 * @param key 
		 * @param value
		 * @throws NullPointerException if key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null) {
				throw new NullPointerException("Key can't be null reference!");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Getter.
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Getter.
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter.
		 * @param value 
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Class that implements a custom iterator to run through elements of the table.
	 * 
	 * @author Marko
	 *
	 */
	private class HashTableIterator implements Iterator<TableEntry<K, V>>{
		int numberOfElements = 0;
		int startingNumberOfPairs = numberOfPairs;
		int currSlot = 0;
		TableEntry<K, V> previousElement = listOfHeads[currSlot];
		int currentModificationCount = modificationCount;
		/**
		 * The flag that tells the iterator if remove function can be called
		 */
		boolean canCallRemove = false;
		
		@Override
		public boolean hasNext() {
			if(currentModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The table has been changed!");
			}
			if(numberOfElements < startingNumberOfPairs) {
				return true;
			}
			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			if(currentModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The table has been changed!");
			}
			if(numberOfElements == 0) {
				skipNulls();
			}
			else {
				if(previousElement.next == null) {
					currSlot++;
					previousElement = listOfHeads[currSlot];
					skipNulls();
				}
				else {
					previousElement = previousElement.next;
				}
			}
			canCallRemove = true;
			numberOfElements++;
			return previousElement;
		}
		
		@Override
		public void remove() {
			if(currentModificationCount != modificationCount) {
				throw new ConcurrentModificationException("The table has been changed!");
			}
			if(!canCallRemove) {
				throw new IllegalStateException("The remove is called multiple times");
			}
			currentModificationCount++;
			SimpleHashTable.this.remove(previousElement.key);
			canCallRemove = false;
		}
		
		/**
		 * Method that skips the empty slots.
		 * 
		 */
		private void skipNulls() {
			while(previousElement == null) {
				currSlot++;
				previousElement = listOfHeads[currSlot];
			}
		}
		
	}
	
	private int size;
	private TableEntry<K,V>[] listOfHeads;
	private int numberOfPairs = 0;
	/**
	 * the threshold when the table increases its size
	 */
	private double increasingBorder;
	
	private int modificationCount;
	
	/**
	 * Constructor that creates a hash table with 16 slots.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {
		this.size = 16;
		this.increasingBorder = this.size * 0.75;
		listOfHeads = (TableEntry<K, V>[]) new TableEntry[size];
	}
	
	/**
	 * Constructor that makes a hash table with desired size but rounded to nearest largest
	 * power of 2. If desired size is 30 then the size of the table will be 32.
	 * 
	 * @param wantedSize {@code int} wanted table size
	 * @throws IllegalArgumentException if argument is smaller than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int wantedSize) {
		if(wantedSize < 1) {
			throw new IllegalArgumentException("Size of the hash table must be at least 1!");
		}
		this.size = nearestPowerOfTwo(wantedSize);
		this.increasingBorder = this.size * 0.75;
		listOfHeads = (TableEntry<K,V>[]) new TableEntry[size];
	}
	
	/**
	 * Method that puts a entry in a slot based on the hash code of the key, and
	 * if slot is not empty it puts that entry at the end of the list. If there 
	 * is a object with that key method overwrites that value
	 * 
	 * @param key of the entry
	 * @param value of the entry
	 * @throws NullPointerException if key argument is null
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key can't be null reference!");
		}
		int slot = hash(key);
		TableEntry<K,V> entry = new TableEntry<>(key, value, null);
		putAtEnd(listOfHeads, slot, entry, true);
		if(numberOfPairs >= increasingBorder) {
			reorganizeTable();
		}
	}
	
	/**
	 * Method returns a value that has a given a key. If this key does 
	 * not exist it returns null.
	 * 
	 * @param key {@code Object} the searching parameter for value
	 * @return {@code null} if the provided key does not exist and value of the object
	 * 			that has provided key
	 */
	public V get(Object key) {
		int slot = hash(key);
		if(listOfHeads[slot] != null) {
			TableEntry<K, V> searchNode = listOfHeads[slot];
			while(searchNode != null) {
				if(searchNode.key.equals(key)) {
					return searchNode.value;
				}
				searchNode = searchNode.next;
			}
		}
		return null;
	}
	
	/**
	 * Method that returns the number of pairs in the table.
	 * 
	 * @return {@code int} number of entrys in table
	 */
	public int size() {
		return numberOfPairs;
	}
	
	
	/**
	 * Method that calculates hash value of the key and based on a calculated value
	 * searches the list of entrys and looks for the provided key.
	 * 
	 * @param key 
	 * @return {@code true} if table contains given key
	 * 		   {@code false} if table doesn't contain given key
	 * @throws NullPointerException if the given key is null reference
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			throw new NullPointerException("Hash table doesn't contain null reference as key!");
		}
		int slot = hash(key);
		if(listOfHeads[slot] != null) {
			TableEntry<K, V> searchNode = listOfHeads[slot];
			while(searchNode != null) {
				if(searchNode.key.equals(key)) {
					return true;
				}
				searchNode = searchNode.next;
			}
		}
		return false;
	}
	
	
	/**
	 * Method that searches every slot in a table and then a whole list in that
	 * slot to find a given value. If the value isn't found it returns false.
	 * 
	 * @param value {@code Object} given value to be looked for
	 * @return {@code true} if the given value exist in table
	 * 		   {@code false} if the given value does not exist in table
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < size; i++) {
			TableEntry<K, V> searchNode = listOfHeads[i];
			while(searchNode != null) {
				if(searchNode.value.equals(value)) {
					return true;
				}
				searchNode = searchNode.next;
			}
		}
		return false;
	}
	
	/**
	 * Method removes entry with given key if that key exist else it does nothing.
	 * 
	 * @param key {@code Object} given value of the key whose entry should be deleted
	 */
	public void remove(Object key) {
		int slot = hash(key);
		if(containsKey(key)) {
			removeFromList(listOfHeads[slot], key);
			numberOfPairs--;
		}
		modificationCount++;
	}
	
	/**
	 * Method that checks whether the table is empty.
	 * 
	 * @return {@code true} if there is one or more elements in table
	 * 		   {@code false} if there are no elements in table 
	 */
	public boolean isEmpty() {
		return numberOfPairs == 0;
	}
	
	
	/**
	 * Clears the table by setting all slots to null and setting number of pairs to zero.
	 * 
	 */
	public void clear() {
		for(int i = 0; i < size; i++) {
			listOfHeads[i] = null;
		}
		numberOfPairs = 0;
		modificationCount++;
	}
	
	
	/**
	 * Method that goes through the list of a slot and removes entry
	 * with given key.
	 * 
	 * @param first {@code TableEntry} first element i
	 * @param key {@code Object} given value of the key whose entry should be deleted
	 */
	private void removeFromList(TableEntry<K, V> first, Object key) {
		if(first.key.equals(key)) {
			listOfHeads[hash(key)] = first.next;
		}
		else {
			while(first.next != null) {
				if(first.next.key.equals(key)) {
					first.next = first.next.next;
					return;
				}
				first = first.next;
			}
		}
	}
	
	
	/**
	 * Calculates the slot for entry to be stored in the table based on the 
	 * division with remainder.
	 * 
	 * @param key {@code Object} given key on which method calls hashCode
	 * @return {@code int} calculated slot
	 */
	private int hash(Object key) {
		return Math.abs(key.hashCode()) % size;
	}
	
	
	/**
	 * Method that finds the smallest power of two that is larger than given number.
	 * It perfmors a bitwise operations of shifting to the left and right. First 
	 * it shifts the provided number till it is equals to zero and counts the number
	 * of shifts. Then it return the number one shifted by left for number of counts
	 * shifted by right. 
	 * 
	 * @param wantedSize {@code int} number for which we have to find power of two
	 * @return {@code int} smallest power of two larger than wantedSize
	 */
	private int nearestPowerOfTwo(int wantedSize) {
		if((wantedSize & (wantedSize - 1)) == 0) {
			return wantedSize;
		}
		else {
			int numberOfShifts = 0;
			while(wantedSize != 0) {
				wantedSize = wantedSize >> 1;
				numberOfShifts++;
			}
			return 1 << numberOfShifts;
			
		}
	}
	
	/**
	 * Reorganizes the current table in case that the load of the table is 
	 * larger or equals to 75% of number of slots.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void reorganizeTable() {
		int oldSize = size;
		size = size * 2;
		TableEntry<K, V>[] newSlots = (TableEntry<K, V>[]) new TableEntry[size];
		for(int i = 0; i < oldSize; i++) {
			TableEntry<K, V> entry = listOfHeads[i];
			while(entry != null) {
				int newSlot = hash(entry.key);
				TableEntry<K, V> newEntry = new TableEntry<K, V>(entry.key, entry.value, null);
				putAtEnd(newSlots, newSlot, newEntry, false);
				entry = entry.next;
			}
		}
		increasingBorder = this.size * 0.75;
		listOfHeads = newSlots;
	}
	
	/**
	 * Put the new entry at the last position in the overflow if overflow in a slot
	 * exist, or as a first entry in slot if overflow does not exist.
	 * 
	 * @param newSlots this is an array of slots, where we search for the position to put our entry in
	 * @param newSlot {@code int} newly calculated position in table to put entry in
	 * @param newEntry {@code TableEntry} new entry that should be put in the table
	 * @param changeNumberOfPairs {@code true} if this method is being used when we put 
	 * 		  new entrys in it, {@code false} if this method is being used when reorganizing the table
	 */
	private void putAtEnd(TableEntry<K, V>[] newSlots, int newSlot, TableEntry<K, V> newEntry,
						  boolean changeNumberOfPairs) {
		TableEntry<K, V> searchNode = newSlots[newSlot];
		if(newSlots[newSlot] == null) {
			newSlots[newSlot] = newEntry;
			if(changeNumberOfPairs) {
				numberOfPairs++;
			}
			modificationCount++;
		}
		else {
			while(searchNode.next != null) {
				if(searchNode.key == newEntry.key) {
					searchNode.value = newEntry.value;
					return;
				}
				searchNode = searchNode.next;
			}
			if(searchNode.key == newEntry.key) {
				searchNode.value = newEntry.value;
				return;
			}
			searchNode.next = newEntry;
			if(changeNumberOfPairs) {
				numberOfPairs++;
			}
			modificationCount++;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < size; i++) {
			TableEntry<K, V> entry = listOfHeads[i];
			while(entry != null) {
				sb.append(entry.key + "=" + entry.value + ", ");
				entry = entry.next;
			}
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		sb.append("]");
		return sb.toString();
		
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new HashTableIterator();
	}
	


}
