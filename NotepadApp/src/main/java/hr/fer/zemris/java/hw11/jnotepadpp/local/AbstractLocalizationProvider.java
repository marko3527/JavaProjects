package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider gives the translations based on the given key.
 * It has method for notifying the listeners that the change
 * has happened.
 * 
 * @author Marko
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	
	protected List<ILocalizationListener> listeners;
	
	
	/**
	 * Constructor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	
	/**
	 * Method that notifys all the listeners that the
	 * change of language has happened.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
	

}
