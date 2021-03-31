package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener that has one method and it is triggered
 * when the change of the languages happen.
 * 
 * @author Marko
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Method that is being called when the change of the language 
	 * has happened
	 */
	public void localizationChanged();

}
