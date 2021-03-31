package hr.fer.zemris.java.gui.calc.actions.binary;

import java.awt.event.ActionEvent;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class implementing the adding action on calculator.
 * 
 * @author Marko
 *
 */
public class AddOperator extends BinaryOperationAction{
	
	private DoubleBinaryOperator operator;

	
	/**
	 * Constructor. Takes one argument, reference to the model calculator.
	 * 
	 * @param calc {@code CalcModelImpl}
	 */
	public AddOperator(CalcModelImpl calc) {
		super(calc);
		this.operator = (x, y) -> x+y;
	}

	
	/**
	 * Method performs the pending operation if the calculator already 
	 * has a operation waiting to be performed and then sets the new pending
	 * operation and operand.
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
		if(calc.getPendingBinaryOperation() != null) {
			calc.setValue(calc.getPendingBinaryOperation().
							applyAsDouble(calc.getActiveOperand(), calc.getValue()));
		}
		calc.setActiveOperand(calc.getValue());
		calc.setPendingBinaryOperation(this.operator);
		calc.clear();
		
	}


}
