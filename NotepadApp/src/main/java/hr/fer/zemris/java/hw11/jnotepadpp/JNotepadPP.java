package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseCurrentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenNewDocAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToLowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToUpperCase;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.DefaultMultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultStatusBar;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Notepad++ app. User is 
 * able to edit multiple text files and every text file is 
 * shown in his tab. This class implements the main method and
 * initialization of the graphical user interface.
 * 
 * @author Marko
 *
 */
public class JNotepadPP extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	
	private FormLocalizationProvider flp;
	
	/**
	 * Actions and tools for user 
	 */
	private Action openNewDoc;
	private Action openFile;
	private Action saveAs;
	private Action save;
	private Action exit;
	private Action closeCurrent;
	private Action toUpperCase;
	private Action toLowerCase;
	private Action ascending;
	private Action descending;
	private Action invertCase;
	private Action unique;	
	private Action statsInfo;
	
	private List<Action> actions = new ArrayList<>();


	/**
	 * Constructor for notepad editor. It sets the title, close
	 * operation and packs the size of the frame
	 * 
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setTitle("JNotepad++");
		setLocation(0,0);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				boolean toClose = true;
				for(int i = documents.getNumberOfDocuments() - 1; i >= 0; i--) {
					SingleDocumentModel document = documents.getDocument(i);
					if(NotepadUtil.exitCheck(document, JNotepadPP.this, flp, documents) == 2) {
						toClose = false;
					}
				}
				
				if(toClose) {
					documents.getStatusBar().getWatch().stop();
					dispose();
				}
				
			}
			
		});
		initGUI();
		setSize(600,600);
	}
	
	
	/**
	 * Main method from which the program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
	
	
	/**
	 * Method that initalizes the graphical user interface
	 * 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		

		documents = new DefaultMultipleDocumentModel(this);
		documents.addMultipleDocumentListener(new DefaultMultipleDocumentListener(documents));
		documents.setProvider(flp);
		
		initializeActions();
		createMenu();
		createToolBar(cp);
		
		DefaultStatusBar statusBar = new DefaultStatusBar();
		statusBar.changeStatusBar();
		documents.setStatusBar(statusBar);
		cp.add(statusBar, BorderLayout.PAGE_END);
		
		cp.add(documents , BorderLayout.CENTER);	
	}
	
	
	
	
	
	
	/**
	 * Method that creates the menu bar.
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(openNewDoc);
		fileMenu.add(openFile);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(closeCurrent);
		fileMenu.add(exit);
		
		Action languageMenuAction = new MenuAction("language", flp);
		JMenu languageMenu = new JMenu(languageMenuAction);
		JMenuItem hr = new JMenuItem("Hrvatski");
		JMenuItem de = new JMenuItem("German");
		JMenuItem en = new JMenuItem("English");
		hr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		
		en.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		
		de.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		languageMenu.add(hr);
		languageMenu.add(en);
		languageMenu.add(de);
		
		Action toolsActionMenu = new MenuAction("tools", flp);
		JMenu toolsMenu = new JMenu(toolsActionMenu);
		toolsMenu.setEnabled(false);
		
		Action changeCaseActionMenu = new MenuAction("changeCase", flp);
		JMenu changeCase = new JMenu(changeCaseActionMenu);
		changeCase.add(toUpperCase);
		changeCase.add(toLowerCase);
		changeCase.add(invertCase);
		toolsMenu.add(changeCase);
		
		Action sortActionMenu = new MenuAction("sort", flp);
		JMenu sortMenu = new JMenu(sortActionMenu);
		sortMenu.add(ascending);
		sortMenu.add(descending);
		toolsMenu.add(sortMenu);
		toolsMenu.add(unique);
		
		Action statsMenuInfo = new MenuAction("statistics", flp);
		JMenu statsMenu = new JMenu(statsMenuInfo);
		statsMenu.add(statsInfo);
		
		menuBar.add(fileMenu);
		menuBar.add(languageMenu);
		menuBar.add(toolsMenu);
		menuBar.add(statsMenu);
		
		
		this.setJMenuBar(menuBar);
		
		actions.add(sortActionMenu);
		actions.add(changeCaseActionMenu);
		actions.add(toolsActionMenu);
		actions.add(languageMenuAction);
		actions.add(statsMenuInfo);
		
		for(Action action : actions) {
			flp.addLocalizationListener(((LocalizableAction)action).getListener());
		}
		
	}
	
	
	/**
	 * Method that creates toolbar and adds it to the start 
	 * of the layout.
	 * 
	 * @param cp
	 */
	private void createToolBar(Container cp) {
		JToolBar toolBar = new JToolBar("ToolBar");
		toolBar.setFloatable(true);
		documents.setToolBar(toolBar);
		
		
		toolBar.add(openNewDoc);
		toolBar.add(openFile);
		toolBar.add(save);
		toolBar.add(saveAs);
		toolBar.add(closeCurrent);
		toolBar.addSeparator();
		
		toolBar.add(ascending).setEnabled(false);
		toolBar.add(descending).setEnabled(false);
		toolBar.add(unique).setEnabled(false);
		
		
		cp.add(toolBar, BorderLayout.PAGE_START);
	}
	
	
	/**
	 * Method that initializes the actions, and stores
	 * all the actions into a map for easier goign through them.
	 * 
	 */
	private void initializeActions() {
		openNewDoc = new OpenNewDocAction("new", flp, documents);
		openFile = new OpenFileAction("open", flp, documents, JNotepadPP.this);
		saveAs = new SaveAsAction("saveAs", flp, documents, JNotepadPP.this);
		save = new SaveAction("save", flp, documents, JNotepadPP.this);
		exit = new ExitAction("exit", flp, documents, JNotepadPP.this);
		closeCurrent = new CloseCurrentAction("close", flp, documents, JNotepadPP.this);
		toUpperCase = new ToUpperCase("toUpper", flp, documents);
		toLowerCase = new ToLowerCaseAction("toLower", flp, documents);
		invertCase = new InvertCaseAction("invert", flp, documents);
		ascending = new AscendingAction("ascending", flp, documents);
		descending = new DescendingAction("descending", flp, documents);
		unique = new UniqueAction("unique", flp, documents);
		statsInfo = new StatisticsAction("statistics", flp, documents, JNotepadPP.this);
		
		actions.add(openNewDoc);
		actions.add(openFile);
		actions.add(saveAs);
		actions.add(save);
		actions.add(exit);
		actions.add(closeCurrent);
		actions.add(toUpperCase);
		actions.add(toLowerCase);
		actions.add(invertCase);
		actions.add(ascending);
		actions.add(descending);
		actions.add(unique);
		actions.add(statsInfo);
	}

}