package hr.fer.zemris.java.gui.calc.elements;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.actions.NumberAction;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that extends the JButton and implements the
 * gui interface for the calculators numpad.
 * 
 * @author Marko
 *
 */
public class NumPad extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RCPosition startingPosition;
	private CalcModelImpl calc;
	
	private List<JButton> numberButtons = new LinkedList<>();
	
	/**
	 * Constructor that takes two arguments. First position where 
	 * the numpad starts and second where the numpad ends.
	 * 
	 * @param startingPosition
	 * @param endPosition
	 */
	public NumPad(RCPosition startingPosition, CalcModelImpl calc) {
		this.startingPosition = startingPosition;
		this.calc = calc;
		setUpButtons();
	}
	
	@Override
	public void addActionListener(ActionListener l) {
		for(JButton button : numberButtons) {
			NumberAction action = new NumberAction(calc,
					Integer.parseInt(button.getText()));
			button.addActionListener(action);
		}
	}
	
	
	/**
	 * Set up the button names for numpad.
	 */
	private void setUpButtons() {
		for(int i = 0; i < 10; i++) {
			JButton button = new JButton("" + i);
			button.setBackground(Calculator.buttonColor);
			button.setFont(button.getFont().deriveFont(30f));
			numberButtons.add(button);
		}
	}
	
	/**
	 * Method that adds the buttons to the given container.
	 * 
	 * @param cp
	 */
	public void addToContainer(Container cp) {
		Map<String, RCPosition> buttonMap = new HashMap<>();
		RCPosition number7 = startingPosition;
		RCPosition number8 = new RCPosition(startingPosition.getRowIndex(), startingPosition.getColumnIndex() + 1);
		RCPosition number9 = new RCPosition(startingPosition.getRowIndex(), startingPosition.getColumnIndex() + 2);
		RCPosition number4 = new RCPosition(startingPosition.getRowIndex() + 1, startingPosition.getColumnIndex());
		RCPosition number5 = new RCPosition(startingPosition.getRowIndex() + 1, startingPosition.getColumnIndex() + 1);
		RCPosition number6 = new RCPosition(startingPosition.getRowIndex() + 1, startingPosition.getColumnIndex() + 2);
		RCPosition number1 = new RCPosition(startingPosition.getRowIndex() + 2, startingPosition.getColumnIndex());
		RCPosition number2 = new RCPosition(startingPosition.getRowIndex() + 2, startingPosition.getColumnIndex() + 1);
		RCPosition number3 = new RCPosition(startingPosition.getRowIndex() + 2, startingPosition.getColumnIndex() + 2);
		RCPosition number0 = new RCPosition(startingPosition.getRowIndex() + 3, startingPosition.getColumnIndex());
		buttonMap.put("1", number1);
		buttonMap.put("2", number2);
		buttonMap.put("3", number3);
		buttonMap.put("4", number4);
		buttonMap.put("5", number5);
		buttonMap.put("6", number6);
		buttonMap.put("7", number7);
		buttonMap.put("8", number8);
		buttonMap.put("9", number9);
		buttonMap.put("0", number0);
		
		for(JButton button : numberButtons) {
			cp.add(button, buttonMap.get(button.getText()));
		}
		
	}
	
	

}
