package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that offers method for adding and removing listeners
 * that will listen to the change of the languages. Main method 
 * of this interface is for getting translation based on the current 
 * language and given key.
 * 
 * @author Marko
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds the localization listener.
	 * 
	 * @param listener
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	
	
	/**
	 * Removes the localization listener.
	 * 
	 * @param listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Gets the translation.
	 * 
	 * @param key {@code String} for translation value
	 * @return value that is stored under key
	 */
	public String getString(String key);
	
	/**
	 * Getter.Returns the currently used language.
	 * 
	 * @return currently used language.
	 */
	public String getCurrentLanguage();

}
