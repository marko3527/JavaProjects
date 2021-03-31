package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Action that is being performed when the user wants 
 * to save document. If the document is '(unnamed)' then 
 * it open a file chooser save as dialog and then user can
 * save it as a new file, but if the document already has it's
 * path then it is saved at that path.
 * 
 * @author Marko
 *
 */
public class SaveAction extends LocalizableAction {

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
	public SaveAction(String key, ILocalizationProvider provider,
					  DefaultMultipleDocumentModel documents, 
					  JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.frame = frame;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("saveDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		SingleDocumentModel currentDoc = documents.getCurrentDocument();
		if(currentDoc == null) {
			JOptionPane.showMessageDialog(frame, provider.getString("noFiles"));
			return;
		}
		if(currentDoc.getFilePath().toString().isEmpty()) {
			new SaveAsAction("saveAs", provider, documents, frame).actionPerformed(e);
		}
		else {
			documents.saveDocument(currentDoc, currentDoc.getFilePath());
		}
	}
	
	

}
