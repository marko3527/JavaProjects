package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.NotepadUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Action that is being performed when the user asks
 * to exit the notepad app.
 * 
 * @author Marko
 *
 */
public class ExitAction extends LocalizableAction{

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
	public ExitAction(String key, ILocalizationProvider provider,
					  DefaultMultipleDocumentModel documents,
					  JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.frame = frame;
		this.putValue(SHORT_DESCRIPTION, provider.getString("exitDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		for(int i = documents.getNumberOfDocuments() - 1; i >= 0; i--) {
			SingleDocumentModel document = documents.getDocument(i);
			NotepadUtil.exitCheck(document, frame, provider, documents);
		}
	}
	
	

}
