package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.NotepadUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Action closes the currently selected document.
 * 
 * @author Marko
 *
 */
public class CloseCurrentAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	private JNotepadPP frame;

	
	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 * @param documents {@code DefaultMultipleDocumentModel} documents opened in app
	 * @param frame to display messages on
	 */
	public CloseCurrentAction(String key, ILocalizationProvider provider,
							  DefaultMultipleDocumentModel documents, 
							  JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("closeDescription"));
		this.frame = frame;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		SingleDocumentModel currentDocument = documents.getCurrentDocument();
		
		if(currentDocument != null) {
			if(currentDocument.isModified()) {
				int value = NotepadUtil.exitCheck(currentDocument, frame,
												  provider, documents);
				if(value == 1) {
					documents.closeDocument(documents.getCurrentDocument());
				}
			}
			else {
				documents.closeDocument(documents.getCurrentDocument());
			}
		}
	}
	
	
	
	

}
