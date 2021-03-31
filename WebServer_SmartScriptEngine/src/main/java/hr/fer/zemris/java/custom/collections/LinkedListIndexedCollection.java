package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class that implements linked list collection. 
 * Duplicate elements are allowed and null values are not allowed.
 * It has 2 constructors.
 * 
 * @author Marko
 *
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * Private class that is modeling one node of the linked list.
	 * It implements one constructor along with the default.
	 * 
	 * @author Marko
	 *
	 */
	private class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
		
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			if(value == null) {
				throw new NullPointerException("I can't make a node with 'null' value! ");
			}
			this.value = value;
		}
		
	};
	
	/**
	 * Private class that implements {@code ElementsGetter} interface 
	 * 
	 * @author Marko
	 *
	 */
	private static class LinkedListElementsGetter implements ElementsGetter {
		private ListNode searchNode;
		private LinkedListIndexedCollection internColl;
		private long savedModificationCount;
		
		public LinkedListElementsGetter(LinkedListIndexedCollection collection) {
			this.internColl = collection;
			this.searchNode = internColl.first;
			this.savedModificationCount = collection.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != internColl.modificationCount) {
				throw new ConcurrentModificationException("There are changes to collection! ");
			}
			if(searchNode != null) {
				return true;
			}
			return false;
		}
		
		@Override
		public Object getNextElement() {
			if(savedModificationCount != internColl.modificationCount) {
				throw new ConcurrentModificationException("There are changes to collection! ");
			}
			if(searchNode == null) {
				throw new NoSuchElementException("There is no more elements to get! ");
			}
			Object valueToReturn = searchNode.value;
			searchNode = searchNode.next;
			return valueToReturn;
		}
	}
	

	
	
	/**
	 * number of elements in collection
	 */
	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount = 0;
	
	/**
	 * Default constructor.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param other collection to be copied to new collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
	
	
	@Override
	public void add(Object value){
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to collection! ");
		}
		if(first == null) {
			first = new ListNode(null,null,value);
			last = first;
		}
		else {
			ListNode newObject = new ListNode(last,null,value);
			last.next = newObject;
			last = newObject;
		}
		size++;
	}
	
	@Override
	public void clear() {
		modificationCount++;
		size = 0;
		first = null;
		last = null;
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
		ListNode searchNode = first;
		while(searchNode != null) {
			if(searchNode.value.equals(value)) {
				return true;
			}
			searchNode = searchNode.next;
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
	public boolean remove(Object value) throws NullPointerException{
		if(value == null) {
			throw new NullPointerException("I can't remove value 'null' from collection!");
		}
		modificationCount++;
		if(contains(value)) {
			ListNode searchNode = first;
			while(searchNode != null) {
				if(searchNode.value.equals(value)) {
					searchNode.previous = searchNode.next;
					searchNode.next.previous = searchNode.previous;
					size--;
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] elements = new Object[size];
		ListNode searchNode = first;
		for(int i = 0; i < size ; i++, searchNode = searchNode.next) {
			elements[i] = searchNode.value;
		}
		return elements;
	}
	
	public Object get(int index) {
		if(index >= size || size < 0) {
			throw new IndexOutOfBoundsException("Can't accept that index! ");
		}
		ListNode searchNode = first;
		while(index > 0) {
			searchNode = searchNode.next;
			index--;
		}
		return searchNode.value;
	}
	
	@Override
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("I can't accept that index! ");
		}
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to the collection!");
		}
		modificationCount++;
		ListNode searchNode = first;
		while(position > 0) {
			searchNode = searchNode.next;
			position--;
		}
		ListNode newNode = new ListNode(searchNode.previous, searchNode, value);
		searchNode.previous.next = newNode;
		searchNode.previous = newNode;
		size++;
		
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
		ListNode searchNode = first;
		while(searchNode != null) {
			valueToReturn++;
			if(searchNode.value.equals(value)) {
				break;
			}
			searchNode = searchNode.next;
		}
		return valueToReturn;
	}
	
	@Override
	public void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Can't accept that index! ");
		}
		modificationCount++;
		ListNode searchNode = first;
		while(index > 0) {
			searchNode = searchNode.next;
			index--;
		}
		searchNode.previous.next = searchNode.next;
		searchNode.next.previous = searchNode.previous;
		size--;
	}


}
