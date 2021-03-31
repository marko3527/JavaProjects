package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class is singleton. It has one instance of this class, 
 * and private constructor that the end user can't see it. 
 * It is used so that we don't have to deal with more
 * providers like this but instead we just change the language
 * on this one.
 * 
 * @author Marko
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	private ResourceBundle bundle;
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	
	/**
	 * Constructor. Sets the starting language to Croatian.
	 */
	private LocalizationProvider() {
		language = "hr";
		Locale locale = new Locale(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Getter. Returns the singleton object
	 * 
	 * @return singleton object
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	
	/**
	 * Setter. It sets the new language, maps the key and value translations
	 * based on the new language and calls the fire() method
	 * for notifying all the listeners that change has happened.
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = new Locale(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	

}
