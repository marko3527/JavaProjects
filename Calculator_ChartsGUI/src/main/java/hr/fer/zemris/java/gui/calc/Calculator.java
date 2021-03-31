package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.actions.InsertDecPointAction;
import hr.fer.zemris.java.gui.calc.actions.PopAction;
import hr.fer.zemris.java.gui.calc.actions.PushAction;
import hr.fer.zemris.java.gui.calc.actions.SwapAction;
import hr.fer.zemris.java.gui.calc.elements.BinaryOperatorsPad;
import hr.fer.zemris.java.gui.calc.elements.FunctionPad;
import hr.fer.zemris.java.gui.calc.elements.NumPad;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that implements the gui for the calculator.
 * 
 * @author Marko
 *
 */
public class Calculator extends JFrame{
	
	public static Color buttonColor = new Color(80,150,180);
	private Stack<Double> stackOfNumbers;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalcModelImpl calc;
	
	
	/**
	 * Constructor.
	 */
	public Calculator() {
		setTitle("Java calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		stackOfNumbers = new Stack<Double>();
		initGUI();
	}
	
	/**
	 * Method that constructs the graphical user interface
	 * 
	 */
	private void initGUI() {
		calc = new CalcModelImpl();
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		JLabel display = initDisplay();
		cp.add(display, new RCPosition(1,1));
		
		JCheckBox invert = new JCheckBox("Inv");
		cp.add(invert, new RCPosition(5,7));
		
		NumPad numpad = new NumPad(new RCPosition(2,3), calc);
		numpad.addToContainer(cp);
		numpad.addActionListener(null);
		
		FunctionPad functionButtons = new FunctionPad(new RCPosition(2,1), calc);
		functionButtons.setCheckBox(invert);
		functionButtons.addToContainer(cp);
		functionButtons.addActionListener(null);
		
		invert.addActionListener(a -> {
			functionButtons.reverseTheFunctions(invert.isSelected());
		});
		
		BinaryOperatorsPad binaryButtons = new BinaryOperatorsPad(new RCPosition(1,6), calc);
		binaryButtons.addToContainer(cp);
		binaryButtons.addActionListener(null);
		
		JButton swapSign = new JButton("+/-");
		swapSign.setBackground(buttonColor);
		swapSign.setFont(swapSign.getFont().deriveFont(20f));
		swapSign.addActionListener(new SwapAction(calc));
		cp.add(swapSign, new RCPosition(5,4));
		
		JButton decimalPoint = new JButton(".");
		decimalPoint.setBackground(buttonColor);
		decimalPoint.setFont(decimalPoint.getFont().deriveFont(20f));
		decimalPoint.addActionListener(new InsertDecPointAction(calc));
		cp.add(decimalPoint, new RCPosition(5,5));
		
		JButton popButton = new JButton("pop");
		popButton.setBackground(buttonColor);
		popButton.setFont(popButton.getFont().deriveFont(20f));
		popButton.addActionListener(new PopAction(stackOfNumbers, calc));
		cp.add(popButton, new RCPosition(4,7));
		
		JButton pushButton = new JButton("push");
		pushButton.setBackground(buttonColor);
		pushButton.setFont(popButton.getFont().deriveFont(20f));
		pushButton.addActionListener(new PushAction(stackOfNumbers, calc));
		cp.add(pushButton, new RCPosition(3,7));
		
		JButton clear = new JButton("clear");
		clear.setBackground(buttonColor);
		clear.setFont(clear.getFont().deriveFont(20f));
		clear.addActionListener(a -> {
			calc.clear();
			display.setText("0");
		});
		cp.add(clear, new RCPosition(1,7));
		
		JButton reset = new JButton("reset");
		reset.setBackground(buttonColor);
		reset.setFont(reset.getFont().deriveFont(20f));
		reset.addActionListener(a -> {
			calc.clearAll();
			stackOfNumbers = new Stack<Double>();
			display.setText(calc.toString());
		});
		cp.add(reset, new RCPosition(2,7));
	}
	
	
	/**
	 * Method that initializes the display label
	 * 
	 * @return {@code JLabel} display for calculator
	 */
	private JLabel initDisplay() {
		JLabel display = new JLabel(calc.toString(),SwingConstants.RIGHT);
		DisplayListener listener = new DisplayListener(display);
		calc.addCalcValueListener(listener);
		display.setBackground(new Color(255, 215, 0));
		display.setOpaque(true);
		display.setFont(display.getFont().deriveFont(30f));
		display.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		return display;
	}
	
	/**
	 * Main method from which the program starts.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new Calculator().setVisible(true);
		});
	}

}
