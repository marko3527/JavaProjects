package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Interface for single document listener. It defines methods
 * for updating document status and document path changes.
 * 
 * @author Marko
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Method that is being called when the status of the document
	 * is changed. Status of the document can be saved/unsaved.
	 * 
	 * @param model {@code SingleDocumentModel} of the document that has changed
	 */
	public void documentModifyStatusUpdated(SingleDocumentModel model);
	
	
	/**
	 * Method that is being called when the file path of the document
	 * has changed.
	 * 
	 * @param model {@code SingleDocumentModel} of the document that has changed
	 */
	public void documentFilePathUpdated(SingleDocumentModel model);

}
