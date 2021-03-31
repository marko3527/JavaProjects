package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that is being performed when the user want to
 * create new document.
 * 
 * @author Marko
 *
 */
public class OpenNewDocAction extends LocalizableAction {
	
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
	public OpenNewDocAction(String key, ILocalizationProvider provider,
						    DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("newDescription"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		documents.createNewDocument();
	}


}
