package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.DefaultSingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;


/**
 * Class that implements a model for handling multiple documents.
 * It is implemented as JTabbedPane where each document represents
 * one tab
 * 
 * @author Marko
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane 
										  implements MultipleDocumentModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<DefaultSingleDocumentModel> documents = new ArrayList<>();
	private static DefaultSingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	private JFrame frame;
	
	private DefaultStatusBar statusBar;
	private ILocalizationProvider provider;
	private JToolBar toolBar;
	
	/**
	 * Private class implementing change listener which changes the 
	 * value of current document depending on the tab selection
	 * of the user
	 * 
	 * @author Marko
	 *
	 */
	private static class DefaultChangeListener implements ChangeListener {
		
		private DefaultMultipleDocumentModel tabbedPane;
		
		
		/**
		 * Concturctor.
		 * 
		 * @param tabbedPane reference to tabbed pane
		 */
		public DefaultChangeListener(DefaultMultipleDocumentModel tabbedPane) {
			this.tabbedPane = tabbedPane;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			JTabbedPane source = (JTabbedPane) e.getSource();
			int index = source.getSelectedIndex();
			currentDocument = tabbedPane.getDocumentModels().get(index);
			currentDocument.getStatusBarListener().registerStatusBar(tabbedPane.statusBar);
		}
		
	}
	
	
	/**
	 * Constructor.
	 * 
	 * @param frame reference to frame for title changing
	 */
	public DefaultMultipleDocumentModel(JFrame frame) {
		this.frame = frame;
		this.addChangeListener(new DefaultChangeListener(this));
	}
	
	
	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel newDocument = new DefaultSingleDocumentModel(Paths.get(""), "");

		newDocument.addSingleDocumentListener(new DefaultSingleDocumentListener(this));
		documents.add(newDocument);
		
		notifyAddedDocument(newDocument);
		
		
		notifyCurrentChanged(currentDocument, newDocument);
		currentDocument = newDocument;
		currentDocument.setModified(false);
		return currentDocument;
		
	}
	
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null) {
			throw new NullPointerException("Path for opening document is null!");
		}
		
		else {
			for(DefaultSingleDocumentModel document : documents) {
				if(document.getFilePath().equals(path)) {
					notifyCurrentChanged(currentDocument, document);
					currentDocument = document;
					return currentDocument;
				}
			}
			
			String textContext = "";
			try {
				textContext = readFile(path);
			} catch (IOException e) {
				String[] options = {provider.getString("closeMessage")};
				JOptionPane.showOptionDialog(this.getParent(), provider.getString("fileOpenProblem"),
											 "",
											 JOptionPane.DEFAULT_OPTION,
											 JOptionPane.INFORMATION_MESSAGE, null,
											 options, options[0]);
				return currentDocument;
			}
			DefaultSingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, textContext);
			documents.add(newDocument);
			
			notifyAddedDocument(newDocument);
			
			notifyCurrentChanged(currentDocument, newDocument);
			currentDocument = newDocument;
			
			currentDocument.addSingleDocumentListener(new DefaultSingleDocumentListener(this));
			
			
			
			
			currentDocument.setModified(false);
			return currentDocument;
		}
	}
	
	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null) {
			newPath = model.getFilePath();
		}
		else {
			for(SingleDocumentModel document : documents) {
				if(document.getFilePath().equals(newPath) &&
				   currentDocument.getFilePath() != newPath) {
					String[] options = {provider.getString("closeMessage")};
					JOptionPane.showOptionDialog(this.getParent(), provider.getString("fileAlreadyOpened"),
												 "",
												 JOptionPane.DEFAULT_OPTION,
												 JOptionPane.INFORMATION_MESSAGE, null,
												 options, options[0]);
					return;
				}
			}
		}
		
		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try{
			Files.write(newPath, bytes);
			model.setFilePath(newPath);
			model.setModified(false);
			updateTitle();
		} catch (IOException e) {
			String[] options = {provider.getString("closeMessage")};
			JOptionPane.showOptionDialog(this.getParent(), provider.getString("fileOpenProblem"),
										 "",
										 JOptionPane.DEFAULT_OPTION,
										 JOptionPane.INFORMATION_MESSAGE, null,
										 options, options[0]);
		}
		
	}
	
	
	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(documents.contains(model)) {
			
			if(documents.size() == 1) {
				createNewDocument();
			}

			notifyRemovedDocument(model);
			
			documents.remove(model);
			if(documents.size() > 0) {
				currentDocument = documents.get(documents.size() - 1);
			}
			updateTitle();
			
		}
		
		
	}
	
	
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if(l != null) {
			listeners.add(l);
		}
		
	}
	
	
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if(l != null) {
			listeners.remove(l);
		}
	}
	
	
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	
	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index >= 0 && index < documents.size()) {
			return documents.get(index);
		}
		throw new IllegalArgumentException("Index out of bounds!");
	}
	
	
	/**
	 * Method that reads file's content and returns it as a string.
	 * 
	 * @param path {@code Path} file path
	 * @return content of the file
	 * @throws IOException 
	 */
	private String readFile(Path path) throws IOException {
		
		byte[] bytes = Files.readAllBytes(path);
		
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	
	/**
	 * Getter for documents.
	 * 
	 * @return {@code List} list of documents in a tab
	 */
	public List<DefaultSingleDocumentModel> getDocumentModels(){
		return documents;
	}
	
	
	/**
	 * Method that goes through all listeners and sends 
	 * them notification that the new document has been 
	 * added
	 * 
	 * @param model {@code SingleDocumentModel} new document
	 */
	private void notifyAddedDocument(SingleDocumentModel model) {
		
		for(MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
		updateTitle();
	}
	
	
	/**
	 * Method that goes through listeners and sends them
	 * notification that the document has been removed
	 * 
	 * @param model {@code SingleDocumentModel} removed document
	 */
	private void notifyRemovedDocument(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}

	}
	
	
	/**
	 * Method that goes through all listeners and send them 
	 * notification that the current document has changed
	 * 
	 * @param previousModel {@code SingleDocumentModel} 
	 * @param currentModel {@code SingleDocumentModel} currently selected document
	 */
	private void notifyCurrentChanged(SingleDocumentModel previousModel,
									  SingleDocumentModel currentModel) {
		
		currentModel.getTextComponent().addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				if(e.getMark() - e.getDot() != 0) {
					frame.getJMenuBar().getMenu(2).setEnabled(true);
					for(int i = 5; i < 9; i++) {
						toolBar.getComponent(i).setEnabled(true);
					}
				}
				else {
					frame.getJMenuBar().getMenu(2).setEnabled(false);
					for(int i = 5; i < toolBar.getComponentCount(); i++) {
						toolBar.getComponent(i).setEnabled(false);
					}
				}
				
			}
		});;
		for(MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previousModel, currentModel);
		}
	}
	
	
	/**
	 * Method that is being called when ever the title of the frame
	 * needs to be changed
	 */
	private void updateTitle() {
		String path = currentDocument.getFilePath().toString();
		if(path.isEmpty()) {
			frame.setTitle("(unnamed) - JNotepad++");
		}
		else {
			frame.setTitle(path + " - JNotepad++");
		}
	}
	
	
	/**
	 * Setter. It is used for setting the status bar so it
	 * can be updated depending on the currently selected 
	 * document
	 * 
	 * @param statusBar
	 */
	public void setStatusBar(DefaultStatusBar statusBar) {
		this.statusBar = statusBar;
	}

	
	/**
	 * Getter.
	 * 
	 * @return {@code DefaultStatusBar}
	 */
	public DefaultStatusBar getStatusBar() {
		return statusBar;
	}
	
	
	/**
	 * Setter.Setting the provider which provides translations.
	 * 
	 * @param provider {@code ILocalizationProvider}
	 */
	public void setProvider(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	
	/**
	 * Setter.Setting the tool bar so the buttons can be disabled
	 * when needed.
	 * 
	 * @param toolBar {@code JToolBar}
	 */
	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}
}
