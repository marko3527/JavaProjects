package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.NotepadUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;


/**
 * Action that is descend sorting the selected text. If 
 * the selected text does not exist this action won't be
 * able to be performed by user beacuse it will be
 * disabled in menu.
 * 
 * @author Marko
 *
 */
public class DescendingAction extends LocalizableAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel documents;
	
	/**
	 * Comparator to compare the lines based on the currently selected language
	 */
	private final Comparator<String> descendingComparator;
	

	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 * @param documents {@code DefaultMultipleDocumentModel} documents opened in app
	 */
	public DescendingAction(String key, ILocalizationProvider provider, DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		descendingComparator = new Comparator<>() {

			@Override
			public int compare(String o1, String o2) {
				Locale locale = new Locale(provider.getCurrentLanguage());
				Collator hrCollator = Collator.getInstance(locale);
				return hrCollator.compare(o2, o1);
			}
		};
		this.putValue(SHORT_DESCRIPTION, provider.getString("descendingDescription"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JTextArea textArea = documents.getCurrentDocument().getTextComponent();
		String selectedText = textArea.getSelectedText();
		documents.getCurrentDocument().getTextComponent();
		
		if(selectedText != null) {
			String[] linesToSort = selectedText.split("\n");
			
			selectedText = NotepadUtil.checkUserSelection(textArea, selectedText);
			linesToSort = selectedText.split("\n");
			
			Collections.sort(Arrays.asList(linesToSort), descendingComparator);
			StringBuilder sb = new StringBuilder();
			
			
			for(String sortedLine : linesToSort) {
				sb.append("\n" + sortedLine);
			}
			
			
			String newText = textArea.getText().
			 		 		 replace(selectedText, sb.toString().replaceFirst("\n", ""));
			textArea.setText(newText);
		}
	}

}
