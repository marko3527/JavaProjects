package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Action that is saving the document to wanted path.
 * If the selected path already exist like opened document
 * in the notepad app, it will prompt appropriate message.
 * 
 * @author Marko
 *
 */
public class SaveAsAction extends LocalizableAction {

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
	public SaveAsAction(String key, ILocalizationProvider provider,
						DefaultMultipleDocumentModel documents,
						JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.frame = frame;
		this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
		this.putValue(SHORT_DESCRIPTION, provider.getString("saveAsDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		
		SingleDocumentModel currentDoc = documents.getCurrentDocument();
		if(currentDoc == null) {
			JOptionPane.showMessageDialog(frame, provider.getString("noFiles"));
			return;
		}
		
		if(fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File file = fileChooser.getSelectedFile();
		Path path = file.toPath();
		if(file.exists()) {
			String[] options = {provider.getString("yes"), provider.getString("no")};
			int value = JOptionPane.showOptionDialog(frame, provider.getString("documentExist"),
												     "", JOptionPane.DEFAULT_OPTION,
												     JOptionPane.INFORMATION_MESSAGE,
												     null, options, options[0]);
			if(value == 1) {
				return;
			}
			else {
				documents.saveDocument(currentDoc, path);
			}
		}
		else {
			documents.saveDocument(currentDoc, path);
		}
		
		
	}
	
	

}
