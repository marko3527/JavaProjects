package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that turns the characters in selected text into
 * lower case.
 * 
 * @author Marko
 *
 */
public class ToLowerCaseAction extends LocalizableAction {

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
	public ToLowerCaseAction(String key, ILocalizationProvider provider, DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("toLowerDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String selectedText = documents.getCurrentDocument().getTextComponent().getSelectedText();
		if(selectedText != null) {
			String lowerCaseText = selectedText.toLowerCase();
			String newText = documents.getCurrentDocument().
							 getTextComponent().getText().
							 replace(selectedText, lowerCaseText);
			documents.getCurrentDocument().getTextComponent().setText(newText);
		}
	}

}
