package hr.fer.zemris.java.gui.prim;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/**
 * Class that implements ListModel and offers the method for
 * adding listeners to the list, and removing them. Also
 * it offers the methods for adding and removing the elements from
 * the list
 * 
 * @author Marko
 *
 * @param <E>
 */
public class PrimListModel implements ListModel<Integer>{
	
	private List<Integer> elements = new LinkedList<>();
	private List<ListDataListener> listeners = new LinkedList<>();
	private int lastPrimeNumber;
	
	public PrimListModel(){
		lastPrimeNumber = 1;
		elements.add(Integer.valueOf(lastPrimeNumber));
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	
	/**
	 * Add element to the list.
	 * 
	 * @param element
	 */
	public void add(Integer element) {
		int pos = elements.size();
		elements.add(element);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	/**
	 * Method for generating first next prime number after given
	 * number in argument.
	 * 
	 * @param lastPrimeNumber {@code int} number to start the prime number calc from
	 * @return {@code int} next prime number
	 */
	protected int next() {
		
		lastPrimeNumber++;
		
		while(true) {
			boolean flag = true;
			if(lastPrimeNumber < 4) {
				break;
			}
			if(lastPrimeNumber % 2 == 0) {
				lastPrimeNumber++;
				continue;
			}
			for(int i = 2; i <= Math.sqrt(lastPrimeNumber); i++) {
				if(lastPrimeNumber % i == 0) {
					lastPrimeNumber++;
					flag = false;
					break;
				}
			}
			if(flag) {
				return lastPrimeNumber;
			}
		}
		return lastPrimeNumber;
	}
	
}
