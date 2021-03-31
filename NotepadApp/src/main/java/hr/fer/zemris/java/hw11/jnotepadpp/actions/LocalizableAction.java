package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action that is affected by the changes of the language.
 * 
 * @author Marko
 *
 */
public class LocalizableAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ILocalizationListener listener;
	protected String key;
	
	protected ILocalizationProvider provider;
	
	
	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		
		String translation = provider.getString(key);
		putValue(NAME, translation);
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, provider.getString(key));
				putValue(SHORT_DESCRIPTION, provider.getString(key + "Description"));
			}
		};
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String translation = provider.getString(key);
		putValue(NAME, translation);
	}
	
	
	/**
	 * Getter.
	 * 
	 * @return {@code ILocalizationListener} listener for change of the language
	 */
	public ILocalizationListener getListener() {
		return listener;
	}
	

}
