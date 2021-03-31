package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Demo program that shows the usage of using list model 
 * to show the elements of the list in the swing model
 * of list.
 * 
 * @author Marko
 *
 */
public class PrimDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lastPrimeNumber = 0;
	
	
	/**
	 * Constructor.
	 * 
	 */
	public PrimDemo() {
		setSize(500,500);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	
	/**
	 * Method that initializes the graphical user interface for this demo.
	 * It has two lists that are stored in one jpanel with grid layout, and 
	 * it has one button at the bottom of the container that is stored within
	 * another panel with flow layout.
	 * 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel listPanel = new JPanel(new GridLayout(1,0));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		PrimListModel primeNumberModel = new PrimListModel();
 		
		JList<Integer> leftList = new JList<>(primeNumberModel);
		JList<Integer> rigthList = new JList<>(primeNumberModel);
		
		listPanel.add(new JScrollPane(leftList));
		listPanel.add(new JScrollPane(rigthList));
		
		JButton button = new JButton("NEXT");
		buttonPanel.add(button);
		
		button.addActionListener(a -> {
			lastPrimeNumber = primeNumberModel.next();
			primeNumberModel.add(lastPrimeNumber);
		});
		
		cp.add(listPanel, BorderLayout.CENTER);
		cp.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	
	/**
	 * Main method from which the program starts.
	 * 
	 * @param args command line aruments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
	
	
	

}
