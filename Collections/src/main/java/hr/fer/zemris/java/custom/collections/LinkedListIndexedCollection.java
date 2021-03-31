package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class that implements linked list collection. 
 * Duplicate elements are allowed and null values are not allowed.
 * It has 2 constructors.
 * 
 * @author Marko
 * @param <T> type of elements held in collection
 *
 */
public class LinkedListIndexedCollection<T> implements List<T>{
	
	/**
	 * Private class that is modeling one node of the linked list.
	 * It implements one constructor along with the default.
	 * 
	 * @author Marko
	 *
	 */
	private static class ListNode<T>{
		ListNode<T> previous;
		ListNode<T> next;
		T value;
		
		public ListNode(ListNode<T> previous, ListNode<T> next, T value) {
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
	private static class LinkedListElementsGetter<T> implements ElementsGetter<T> {
		private ListNode<T> searchNode;
		private LinkedListIndexedCollection<T> internColl;
		private long savedModificationCount;
		
		public LinkedListElementsGetter(LinkedListIndexedCollection<T> collection) {
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
		public T getNextElement() {
			if(savedModificationCount != internColl.modificationCount) {
				throw new ConcurrentModificationException("There are changes to collection! ");
			}
			if(searchNode == null) {
				throw new NoSuchElementException("There is no more elements to get! ");
			}
			T valueToReturn = searchNode.value;
			searchNode = searchNode.next;
			return valueToReturn;
		}
	}
	

	
	
	/**
	 * number of elements in collection
	 */
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
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
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		addAll(other);
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LinkedListElementsGetter<T>(this);
	}
	
	
	@Override
	public void add(T value){
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to collection! ");
		}
		if(first == null) {
			first = new ListNode<T>(null,null,value);
			last = first;
		}
		else {
			ListNode<T> newObject = new ListNode<>(last,null,value);
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
		ListNode<T> searchNode = first;
		while(searchNode != null) {
			if(searchNode.value.equals(value)) {
				return true;
			}
			searchNode = searchNode.next;
		}
		return false;
	}
	
	@Override
	public void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		getter.processRemaining( value -> {
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
			ListNode<T> searchNode = first;
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
		ListNode<T> searchNode = first;
		for(int i = 0; i < size ; i++, searchNode = searchNode.next) {
			elements[i] = searchNode.value;
		}
		return elements;
	}
	
	public T get(int index) {
		if(index >= size || size < 0) {
			throw new IndexOutOfBoundsException("Can't accept that index! ");
		}
		ListNode<T> searchNode = first;
		while(index > 0) {
			searchNode = searchNode.next;
			index--;
		}
		return searchNode.value;
	}
	
	@Override
	public void insert(T value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("I can't accept that index! ");
		}
		if(value == null) {
			throw new NullPointerException("I can't add 'null' to the collection!");
		}
		modificationCount++;
		ListNode<T> searchNode = first;
		while(position > 0) {
			searchNode = searchNode.next;
			position--;
		}
		ListNode<T> newNode = new ListNode<>(searchNode.previous, searchNode, value);
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
		ListNode<T> searchNode = first;
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
		ListNode<T> searchNode = first;
		while(index > 0) {
			searchNode = searchNode.next;
			index--;
		}
		searchNode.previous.next = searchNode.next;
		searchNode.next.previous = searchNode.previous;
		size--;
	}


}
