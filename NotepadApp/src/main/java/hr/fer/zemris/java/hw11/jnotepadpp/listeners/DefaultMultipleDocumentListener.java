package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultSingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.StatusBarInfoModel;

/**
 * Class implementing MutlipleDocumentListener. It offers the implementation of methods
 * that are adding and removing tabs to the tabbed pane
 * 
 * @author Marko
 *
 */
public class DefaultMultipleDocumentListener implements MultipleDocumentListener{
	
	private DefaultMultipleDocumentModel tabbedPane;
	
	
	/**
	 * Constructor.
	 * 
	 * @param tabbedPane {@code DefaultMultipleDocumentModel} tabbed pane model
	 */
	public DefaultMultipleDocumentListener(DefaultMultipleDocumentModel tabbedPane) {
		this.tabbedPane = tabbedPane;
	}


	/**
	 * {@inheritDoc}
	 * Disconnects the status bar from the previous document and connects it to
	 * new one.
	 * 
	 */
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(previousModel != null) {
			DefaultSingleDocumentModel prevModel = (DefaultSingleDocumentModel) previousModel;
			StatusBarInfoModel infoModel = prevModel.getStatusBarListener();
			infoModel.removeStatusBar();
		}
		
		
		tabbedPane.setSelectedIndex(tabbedPane.getDocumentModels().indexOf(currentModel));
		
		DefaultSingleDocumentModel currModel = (DefaultSingleDocumentModel) currentModel;
		StatusBarInfoModel infoModel = currModel.getStatusBarListener();
		infoModel.registerStatusBar(tabbedPane.getStatusBar());
		
		
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * Adds the textArea to a tab Pane as a scroll pane
	 */
	@Override
	public void documentAdded(SingleDocumentModel model) {
		if(model != null) {
			JScrollPane scrollPane = new JScrollPane(model.getTextComponent());
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			String nameOfFile = model.getFilePath().getFileName().toString();
			if(nameOfFile.isEmpty()) {
				nameOfFile = "(unnamed)";
			}
			
			tabbedPane.add(nameOfFile, scrollPane);
			int index = tabbedPane.indexOfComponent(scrollPane);
			tabbedPane.setSelectedIndex(index);
			tabbedPane.setToolTipTextAt(index, model.getFilePath().toString());
			
		}
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		if(model != null) {
			
			List<DefaultSingleDocumentModel> documents = tabbedPane.getDocumentModels();
			int index = documents.indexOf(model);
			tabbedPane.remove(index);
			
		}
	}

}
