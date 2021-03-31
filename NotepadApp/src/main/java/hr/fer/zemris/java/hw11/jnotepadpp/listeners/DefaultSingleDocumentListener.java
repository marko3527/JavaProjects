package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.util.List;

import hr.fer.zemris.java.hw11.jnotepadpp.icons.IconLoader;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultSingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;


/**
 * Class that implements the SingleDocumentListener interface
 * and offers implementation of methods when the path of the file
 * has changed or when the editor text has changed
 * 
 * @author Marko
 *
 */
public class DefaultSingleDocumentListener implements SingleDocumentListener{
	
	private DefaultMultipleDocumentModel tabbedPane;
	private IconLoader iconLoader;
	
	
	/**
	 * Constructor.
	 * 
	 * @param tabbedPane {@code DefaultMultipleDocumentModel} model of tabbed pane
	 */
	public DefaultSingleDocumentListener(DefaultMultipleDocumentModel tabbedPane) {
		this.tabbedPane = tabbedPane;
		iconLoader = new IconLoader();
	}
	

	/**
	 * {@inheritDoc}
	 * If the current document is modified it sets the icon of it 
	 * to the unsaved icon.
	 * 
	 */
	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		List<DefaultSingleDocumentModel> documents = tabbedPane.getDocumentModels();
		
		if(model.isModified()) {
			tabbedPane.setIconAt(documents.indexOf(model), iconLoader.UNSAVED);
		}
		
		else {
			tabbedPane.setIconAt(documents.indexOf(model), iconLoader.SAVED);
		}
	}

	
	/**
	 * {@inheritDoc}
	 * Sets the toolTip to new path and title of the tab to the new 
	 * name of the file.
	 * 
	 */
	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
		int index = tabbedPane.getDocumentModels().indexOf(model);
		tabbedPane.setTitleAt(index, model.getFilePath().getFileName().toString());
		tabbedPane.setToolTipTextAt(index, model.getFilePath().toString());
	}

}
