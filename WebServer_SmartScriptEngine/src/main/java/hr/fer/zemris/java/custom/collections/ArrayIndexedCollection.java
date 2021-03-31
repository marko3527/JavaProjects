package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class that represents array-backed collection of objects.
 * Duplicates are allowed and null values are not allowed.
 * When the collection is full it is doubling it's capacity.
 * It has 4 constructors.
 * 
 * @author Marko
 *
 */
public class ArrayIndexedCollection implements List{
	
	/**
	 * Private class that implements {@code ElementsGetter} interface 
	 * 
	 * @author Marko
	 *
	 */
	private static class ArrayIndexedElementsGetter implements ElementsGetter {
		
		private ArrayIndexedCollection internColl;
		private int searchIndex = 0;
		private long savedModificationCount;
		
		public ArrayIndexedElementsGetter(ArrayIndexedCollection collection, long modificationCount) {
			this.internColl = collection;
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != internColl.modificationCount) {
				throw new ConcurrentModificationException("There are changes to collection! ");
			}
			if(searchIndex >= internColl.size) {
				return false;
			}
			return true;
		}

		@Override
		public Object getNextElement() {
			if(savedModificationCount != internColl.modificationCount) {
				throw new ConcurrentModificationException("There are changes to collection! ");
			}
			if(searchIndex >= internColl.elements.length) {
				throw new NoSuchElementException("There is no more elements to get! ");
			}
			Object valueToReturn = internColl.elements[searchIndex];
			searchIndex++;
			return valueToReturn;
		}
	}

	
	/**
	 * number of elements stored in collection
	 */
	private int size;
	private Object[] elements;
	private long modificationCount = 0;
	
	/**
	 * Default constructor, preallocates memory for 16 objects to be stored
	 * in collection
	 */
	public ArrayIndexedCollection() {
		 this(16);
	}
	
	/**
	 * Constructor
	 * 
	 * @param initialCapacity number of elements that could be stored in collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 0) {
			throw new IllegalArgumentException("I can't alocate memory for 0 or less objects!");
		}
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Constructor that preallocates memory for array of objects depending 
	 * on other collection and is storing values from other collection to the
	 * newly created.
	 * 
	 * @param other collection of objects to be added to the new collection
	 * 
	 * @throws {@link NullPointerException} if collection other is null
	 */
	public ArrayIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("Provided collection is null");
		}
		elements = new Object[other.size()];
		addAll(other);
	}
	
	/**
	 * Constructor that preallocates memory for array of objects depending on other 
	 * collection that should be added to that array(Collection) of objects.
	 * 
	 * @param initialCapacity number of elements that could be stored in collection
	 * @param other other collection of objects to be added to the new collection
	 * 
	 * @throws {@link IllegalArgumentException} if initialCapacity is smaller than 1 and
	 * 		   {@link NullPointerException} if collection other is null
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection other) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("I can't alocate memory for 0 or less objects!");
		}
		if(other == null) {
			throw new NullPointerException("Provided collection is null");
		}
		elements = initialCapacity < other.size() ? new Object[other.size()] : new Object[initialCapacity];
		addAll(other);
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayIndexedElementsGetter(this, modificationCount);
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value){
		if(value == null) {
			throw new NullPointerException("I can't check if the value 'null' exists in collection!");
		}
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		getter.processRemaining(value -> {
			if(tester.test(value)) {
				add(value);
			}
		});
	}
	

	/**
	 * {@inheritDoc}
	 * First it checks whether the value exists in collection.
	 */
	@Override
	public boolean remove(Object value){
		if(value == null) {
			throw new NullPointerException("I can't remove value 'null' from collection!");
		}
		if(contains(value)) {
			int indexOfObject = indexOf(value);
			if(indexOfObject != -1) {
				elements[indexOfObject] = null;
				size--;
				shiftObjectsToLeft(indexOfObject);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] elementsToReturn = new Object[size];
		for(int i = 0; i < size; i++) {
			elementsToReturn[i] = elements[i];
		}
		return elementsToReturn;
	}
	
	@Override
	public void clear() {
		for(int i = size - 1; i >= 0; i--) {
			remove(elements[i]);
		}
	}
	
	/**
	 * Checks if there is place for object to be stored in array, if not
	 * then it reallocates that array with double of size and then adds 
	 * the value to the array.
	 * 
	 * 
	 * Average complexity of this method is O(n).
	 * 
	 */
	@Override
	public void add(Object value) throws NullPointerException{
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to collection! ");
		}
		if(size == elements.length) {
			doubleTheSize(elements);
		}
		elements[size] = value;
		size++;
	}
	
	/**
	 * It takes care of doubling the capacity of array and copying the old values
	 * into newly allocated array.
	 * 
	 * @param copyOfElements array with old values that should be copied
	 */
	private void doubleTheSize(Object[] copyOfElements) {
		modificationCount++;
		elements = new Object[2*size];
		for(int i = 0; i < size; i++) {
			elements[i] = copyOfElements[i];
		}
	}
	
	/**
	 * {@inheritDoc} 
	 * @throws NullPointerException if object that we are returning is null reference   
	 */
	public Object get(int index) {
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Can't accept that index! ");
		}
		Object objectToReturn = elements[index];
		if(objectToReturn == null) {
			throw new NullPointerException("The object at the index" + index + " is null reference! ");
		}
		else {
			return objectToReturn;
		}
	}
	
	@Override
	public int indexOf(Object value) {
		int valueToReturn = -1;
		try {
			if(!contains(value)) {
				return valueToReturn;
			}
		}catch(NullPointerException ex) {
			return valueToReturn;
		}
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				valueToReturn = i;
				break;
			}
		}
		return valueToReturn;
	}
	
	@Override
	public void remove(int index) {
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		elements[index] = null;
		shiftObjectsToLeft(index);
		
		size--;
	}
	
	/**
	 * It switches the objects to the left after removing one object.
	 * 
	 * @param index starting point from which we have to move objects
	 */
	private void shiftObjectsToLeft(int index) {
		modificationCount++;
		for(; index < size - 1; index++) {
			if(elements[index + 1] != null) {
				elements[index] = elements[index + 1];
				elements[index + 1] = null;
			}
			else {
				return;
			}
			
		}
	}
	
	/**
	 * Switches the object to the right so it creates a place to insert an object.
	 * 
	 * @param index from which we are switching objects to right
	 */
	private void shiftObjectsToRight(int index) {
		modificationCount++;
		for(int currentIndex = size - 1; currentIndex >= index; currentIndex--) {
			elements[currentIndex + 1] = elements[currentIndex];
		}
	}
	
	
	@Override
	public void insert(Object value, int position) {
		if(position >= size || position < 0) {
			throw new IndexOutOfBoundsException();
		}
		else if(value == null) {
			throw new NullPointerException("I can't add 'null' to the collection!");
		}
		if(size == elements.length) {
			doubleTheSize(elements);
		}
		shiftObjectsToRight(position);
		elements[position] = value;
		size++;
		
	}
	
}
