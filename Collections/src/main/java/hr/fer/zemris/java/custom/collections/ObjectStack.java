package hr.fer.zemris.java.custom.collections;


/**
 * Class that represents the stack like behaviour of collection. So adding a new element
 * will result in pushing that element to the top of the stack and popping it
 * removes the last element in the stack.
 * Class {@code} ArrayIndexedCollection is used as adaptee
 * 
 * @author Marko
 * @param <T> type of elements held in stack
 *
 */
public class ObjectStack<T> {
	
	
	ArrayIndexedCollection<T> elements;
	
	/**
	 * Default constructor.
	 */
	public ObjectStack() {
		elements = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * Checks if the stack is empty by adapting the function from {@code ArrayIndexedCollection}
	 * 
	 * @return {@code true} if stack is empty
	 * 		   {@code false} if stack is not empty
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	/**
	 * Number of elements in the stack.
	 *
	 * @return {@code int} number of elements
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * Pushes the element to the top of the stack.
	 * 
	 * @param value object to push on stack
	 */
	public void push(T value) {
		try {
			elements.add(value);
		}catch(NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Grabbing the last element on the stack and removing it.
	 * 
	 * @return {@code Object} element at the top of the stack
	 */
	public T pop() {
		int lastIndex = elements.size() - 1;
		T element = elements.get(lastIndex);
		elements.remove(element);
		return element;
	}
	
	/**
	 * Look at the element at top of the stack and doesn't remove it.
	 * 
	 * @return {@code Object} value at the top of the stack
	 */
	public T peek() {
		int lastIndex = elements.size() - 1;
		return elements.get(lastIndex);
	}
	
	/**
	 * Clearing the stack.
	 * 
	 */
	public void clear() {
		elements.clear();
	}

}
