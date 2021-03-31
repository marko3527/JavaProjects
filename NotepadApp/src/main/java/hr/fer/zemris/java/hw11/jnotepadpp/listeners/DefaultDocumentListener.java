package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultSingleDocumentModel;


/**
 * Implementation of the Document Listener. This is
 * used for setting modified value to true if the 
 * text area in document is being changed.
 * 
 * @author Marko
 *
 */
public class DefaultDocumentListener implements DocumentListener{
	
	private DefaultSingleDocumentModel singleDocumentModel;
	
	
	/**
	 * Constructor.
	 * 
	 * @param singleDocumentModel document to watch if it was modified
	 */
	public DefaultDocumentListener(DefaultSingleDocumentModel singleDocumentModel) {
		this.singleDocumentModel = singleDocumentModel;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		singleDocumentModel.setModified(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		singleDocumentModel.setModified(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		singleDocumentModel.setModified(true);
	}
	
	

}
