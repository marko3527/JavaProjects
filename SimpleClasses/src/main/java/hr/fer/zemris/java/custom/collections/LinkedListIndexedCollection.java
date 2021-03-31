package hr.fer.zemris.java.custom.collections;


/**
 * Class that implements linked list collection. 
 * Duplicate elements are allowed and null values are not allowed.
 * It has 2 constructors.
 * 
 * @author Marko
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
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
	 * number of elements in collection
	 */
	private int size;
	private ListNode first;
	private ListNode last;
	
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
	public void add(Object value) throws NullPointerException{
		super.add(value);
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
		size = 0;
		first = null;
		last = null;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) throws NullPointerException{
		super.contains(value);
		ListNode searchNode = first;
		while(searchNode != null) {
			if(searchNode.value.equals(value)) {
				return true;
			}
			searchNode = searchNode.next;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * First it checks whether the value exists in collection.
	 */
	@Override
	public boolean remove(Object value) throws NullPointerException{
		super.remove(value);

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
	
	@Override
	public void forEach(Processor processor) {
		ListNode searchNode = first;
		while(searchNode != null) {
			if(searchNode.value != null){
				processor.process(searchNode.value);
				searchNode = searchNode.next;
			}
		}
	}
	
	/**
	 * Searches the collection to find the object at {@code index}.
	 * 
	 * @param index at where the wanted object should be 
	 * @return the object at given index
	 * @throws IndexOutOfBoundsException if the index is not valid
	 */
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
	
	/**
	 * It inserts a new list node to the collection at the given index while not overriding
	 * object at that index.
	 * 
	 * @param value of the inserted object
	 * @param position at which the object should be inserted
	 * @throws IndexOutOfBoundsException if the index is not allowed
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("I can't accept that index! ");
		}
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
	
	
	/**
	 * It searches the collection and returns the index 
	 * of first occurrence of the object with provided value.
	 * 
	 * @param value 
	 * @return index of the first of occurrence of the provided value
	 */
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
	
	/**
	 * Searches the collection and removes the object on provided index.
	 * 
	 * @param index of the object to be removed 
	 * @throws IndexOutOfBoundsException if index is out of bonds
	 */
	public void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Can't accept that index! ");
		}
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
