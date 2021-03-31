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
import hr.fer.zemris.java.gui.calc.actions.CalcAction;
import hr.fer.zemris.java.gui.calc.actions.ResultAction;
import hr.fer.zemris.java.gui.calc.actions.binary.AddOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.DivideOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.MultiplyOperator;
import hr.fer.zemris.java.gui.calc.actions.binary.SubOperator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that implements the buttons that perform binary operations
 * on calculator values.
 * 
 * @author Marko
 *
 */
public class BinaryOperatorsPad extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> operatorsButton = new LinkedList<>();
	private RCPosition startingPosition;
	private CalcModelImpl calc;
	
	
	
	/**
	 * Constructor.Takes two args, first is the starting position
	 * of the first operator button.Second is reference to the
	 * calculator model.
	 * 
	 * @param startingPosition
	 * @param calc
	 */
	public BinaryOperatorsPad(RCPosition startingPosition, CalcModelImpl calc) {
		this.startingPosition = startingPosition;
		this.calc = calc;
		setupButtons();
	}
	
	
	@Override
	public void addActionListener(ActionListener l) {
		Map<String, CalcAction> actions = new HashMap<>();
		actions.put("=", new ResultAction(calc));
		actions.put("/", new DivideOperator(calc));
		actions.put("*", new MultiplyOperator(calc));
		actions.put("-", new SubOperator(calc));
		actions.put("+", new AddOperator(calc));
		
		for(JButton button : operatorsButton) {
			button.addActionListener(actions.get(button.getText()));
		}
	}
	
	/**
	 * Method that adds the buttons to the given container.
	 * 
	 * @param cp
	 */
	public void addToContainer(Container cp) {
		Map<String, RCPosition> buttonPositions = new HashMap<>();
		RCPosition equalsPosition = startingPosition;
		RCPosition dividePosition = new RCPosition(startingPosition.getRowIndex() + 1,startingPosition.getColumnIndex());
		RCPosition multiplyPos = new RCPosition(startingPosition.getRowIndex() + 2,startingPosition.getColumnIndex());
		RCPosition subPosition = new RCPosition(startingPosition.getRowIndex() + 3,startingPosition.getColumnIndex());
		RCPosition addPosition = new RCPosition(startingPosition.getRowIndex() + 4,startingPosition.getColumnIndex());
		
		buttonPositions.put("=", equalsPosition);
		buttonPositions.put("/", dividePosition);
		buttonPositions.put("*", multiplyPos);
		buttonPositions.put("-", subPosition);
		buttonPositions.put("+", addPosition);
		
		for(JButton button : operatorsButton) {
			cp.add(button, buttonPositions.get(button.getText()));
		}
	}
	
	
	/**
	 * Method for inital set up of the function buttons.
	 */
	private void setupButtons() {
		operatorsButton.add(new JButton("="));
		operatorsButton.add(new JButton("/"));
		operatorsButton.add(new JButton("*"));
		operatorsButton.add(new JButton("-"));
		operatorsButton.add(new JButton("+"));
		
		for(JButton button : operatorsButton) {
			button.setBackground(Calculator.buttonColor);
			button.setFont(button.getFont().deriveFont(30f));
		}
	}

}
