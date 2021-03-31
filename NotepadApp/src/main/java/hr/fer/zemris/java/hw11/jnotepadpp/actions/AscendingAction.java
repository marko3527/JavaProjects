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
 * Action that is ascend sorting the selected text. If 
 * the selected text does not exist this action won't be
 * able to be performed by user beacuse it will be
 * disabled in menu.
 * 
 * @author Marko
 *
 */
public class AscendingAction extends LocalizableAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	
	/**
	 * Comparator to compare the lines based on the currently selected language
	 */
	private final Comparator<String> ascendingComparator;
	

	/**
	 * Constructor.
	 * 
	 * @param key {@code String} key for translation value
	 * @param provider {@code LocalizationProvider} object to provide translations
	 * @param documents {@code DefaultMultipleDocumentModel} documents opened in app
	 */
	public AscendingAction(String key, ILocalizationProvider provider, DefaultMultipleDocumentModel documents) {
		super(key, provider);
		this.documents = documents;
		
		ascendingComparator = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				//ovaj treba koristiti trenutno postavljeni jezik a ne hrvatski
				Locale locale = new Locale(provider.getCurrentLanguage());
				Collator hrCollator = Collator.getInstance(locale);
				return hrCollator.compare(o1, o2);
			}
		};
		this.putValue(SHORT_DESCRIPTION, provider.getString("ascendingDescription"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JTextArea textArea = documents.getCurrentDocument().getTextComponent();
		String selectedText = textArea.getSelectedText();
		
		if(selectedText != null) {
			String[] linesToSort = selectedText.split("\n");
			
			selectedText = NotepadUtil.checkUserSelection(textArea, selectedText);
			
			linesToSort = selectedText.split("\n");
			Collections.sort(Arrays.asList(linesToSort), ascendingComparator);
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
