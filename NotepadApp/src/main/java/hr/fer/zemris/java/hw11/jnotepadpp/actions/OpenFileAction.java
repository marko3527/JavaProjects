package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that is being performed when the user 
 * wants to open existing file. It opens the file chooser
 * dialog and then loads the file content into editor.
 * 
 * @author Marko
 *
 */
public class OpenFileAction extends LocalizableAction {

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
	public OpenFileAction(String key, ILocalizationProvider provider,
						  DefaultMultipleDocumentModel documents, 
						  JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.frame = frame;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("openDescription"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open document");
		
		if(fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File file = fileChooser.getSelectedFile();
		Path path = file.toPath();
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(frame, "File does not exist!");
		}
		else {
			documents.loadDocument(path);
		}
	}
	
	

}
