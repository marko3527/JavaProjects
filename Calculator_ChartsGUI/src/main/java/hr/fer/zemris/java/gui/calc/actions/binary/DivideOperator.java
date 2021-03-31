package hr.fer.zemris.java.gui.calc.actions.binary;

import java.awt.event.ActionEvent;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class implementing the division action on calculator.
 * 
 * @author Marko
 *
 */
public class DivideOperator extends BinaryOperationAction{
	
	private DoubleBinaryOperator operator;

	
	/**
	 * Constructor. Takes one argument, reference to the model calculator.
	 * 
	 * @param calc {@code CalcModelImpl}
	 */
	public DivideOperator(CalcModelImpl calc) {
		super(calc);
		this.operator = (x, y) -> x/y;
	}

	
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
