package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Interface that offers methods for notifying
 * the multiple document model if there was new documents added,
 * removed and whether the currently shown documents has
 * been changed.
 * 
 * @author Marko
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method that is being called when the currently selected 
	 * document is changed. Arguments can be null but not at the same time.
	 * 
	 * @param previousModel {@code SingleDocumentModel} document before the change
	 * @param currentModel {@code SingleDocumentModel} current document
	 */
	public void currentDocumentChanged(SingleDocumentModel previousModel, 
									   SingleDocumentModel currentModel);
	
	
	/**
	 * Method that is being called when the new document has been
	 * added to the list of documents into multiple document model.
	 * 
	 * @param model {@code SingleDocumentModel} model of the added document
	 */
	public void documentAdded(SingleDocumentModel model);
	
	
	/**
	 * Method that is being called when the document
	 * should be removed from the list of documents held in
	 * multiple document model.
	 * 
	 * @param model {@code SingleDocumentModel} model of the removed document
	 */
	public void documentRemoved(SingleDocumentModel model);
	
}
