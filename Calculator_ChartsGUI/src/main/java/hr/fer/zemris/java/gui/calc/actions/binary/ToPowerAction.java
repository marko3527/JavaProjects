package hr.fer.zemris.java.gui.calc.actions.binary;

import java.awt.event.ActionEvent;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that takes the number the raises it to the power of n
 * or calculates the nth root of the number in calculator.
 * 
 * @author Marko
 *
 */
public class ToPowerAction extends BinaryOperationAction{

	private JCheckBox invertButton;
	private DoubleBinaryOperator operator;
	
	/**
	 * Constructor.
	 * 
	 * @param calc
	 * @param invertButton
	 */
	public ToPowerAction(CalcModelImpl calc, JCheckBox invertButton) {
		super(calc);
		this.invertButton = invertButton;
		
	}
	
	
	/**
	 * Method performs the pending operation if the calculator already 
	 * has a operation waiting to be performed and then sets the new pending
	 * operation and operand. Based on the invert check box this method will 
	 * apply operator either of power to n or n-th root from value
	 * {@inheritDoc}
	 * 
	 * @throws CalculatorInputException if the user asks this action to be
	 * performed multiple times in a row without inputing new digit
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(calc.hasFrozenValue()) {
			throw new CalculatorInputException("Can't input new operation!");
		}
		if(!this.invertButton.isSelected()) {
			this.operator = (x,y) -> Math.pow(x, y);
		}
		else {
			this.operator = (x,y) -> Math.round(Math.pow(x, 1/y));
		}
		if(calc.getPendingBinaryOperation() != null) {
			calc.setValue(calc.getPendingBinaryOperation().
							applyAsDouble(calc.getActiveOperand(), calc.getValue()));
		}
		calc.setActiveOperand(calc.getValue());
		calc.setPendingBinaryOperation(this.operator);
		calc.clear();
	}

}
