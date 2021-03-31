package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.NotepadUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;

public class StatisticsAction extends LocalizableAction{

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
	 * @param frame to display the message on
	 */
	public StatisticsAction(String key, ILocalizationProvider provider,
							DefaultMultipleDocumentModel documents,
							JNotepadPP frame) {
		super(key, provider);
		this.documents = documents;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String message = NotepadUtil.calculateStats(provider, documents);
		
		JOptionPane.showOptionDialog(frame,
		message, "", JOptionPane.DEFAULT_OPTION,
		JOptionPane.INFORMATION_MESSAGE,
		null, null, null);
	}
	
	

}
