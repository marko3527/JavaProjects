package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.NotepadUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that removes duplicate lines and keeps the first 
 * occurance of that line.
 *
 * @author Marko
 *
 */
public class UniqueAction extends LocalizableAction{

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
	public UniqueAction(String key, ILocalizationProvider provider, DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		this.putValue(SHORT_DESCRIPTION, provider.getString("uniqueDescription"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JTextArea textArea = documents.getCurrentDocument().getTextComponent();
		String selectedText = textArea.getSelectedText();
		
		selectedText = NotepadUtil.checkUserSelection(textArea, selectedText);
		
		String[] selectedLine = selectedText.split("\n");
		Set<String> uniqueLines = new HashSet<String>();
		
		for(String line : selectedLine) {
			uniqueLines.add(line);
		}
		
		StringBuilder sb = new StringBuilder();
		for(String line : uniqueLines) {
			sb.append("\n" + line);
		}
		
		
		String newText = textArea.getText().
 		 		 replace(selectedText, sb.toString().replaceFirst("\n", ""));
		textArea.setText(newText);
	}
	
	

}
