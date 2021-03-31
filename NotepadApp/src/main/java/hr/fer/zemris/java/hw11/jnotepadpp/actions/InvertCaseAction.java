package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that inverts the casing of the characters
 * in selected text. If the selected text does not exist
 * this action won't be able to be performed.
 * 
 * @author Marko
 *
 */
public class InvertCaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;

	
	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 * @param documents {@code DefaultMultipleDocumentModel} documents opened in app
	 */
	public InvertCaseAction(String key, ILocalizationProvider provider, DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("invertDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String selectedText = documents.getCurrentDocument().getTextComponent().getSelectedText();
		
		if(selectedText != null) {
			String inverted = changeCase(selectedText);
			String newText = documents.getCurrentDocument().
					 		 getTextComponent().getText().
					 		 replace(selectedText, inverted);
			documents.getCurrentDocument().getTextComponent().setText(newText);
		}
	}
	
	
	/**
	 * Method that changes the casing of the text given in argument, 
	 * and hold all the characters that are not letters same as they 
	 * were before.
	 * 
	 * @param text {@code String} text to be inverted
	 * @return {@code String} inverted casing text
	 */
	private String changeCase(String text) {
		StringBuilder newText = new StringBuilder();
		
		for(char textChar : text.toCharArray()) {
			if(Character.isLowerCase(textChar)) {
				newText.append(("" + textChar).toUpperCase());
			}
			else if(Character.isUpperCase(textChar)) {
				newText.append(("" + textChar).toLowerCase());
			}
			else {
				newText.append(textChar);
			}
		}
		return newText.toString();
	}
	

}
