package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;


/**
 * This action does not have any specific action to perform
 * except change the name when the language is changed.
 * It is used for menu's names.
 * 
 * @author Marko
 *
 */
public class MenuAction extends LocalizableAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 */
	public MenuAction(String key, ILocalizationProvider provider) {
		super(key, provider);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

}
