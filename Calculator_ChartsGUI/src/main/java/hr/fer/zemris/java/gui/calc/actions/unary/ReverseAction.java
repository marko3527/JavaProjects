package hr.fer.zemris.java.gui.calc.actions.unary;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that implements action that will take existing
 * number in calculator and reverse it (1/that existing number).
 * 
 * @author Marko
 *
 */
public class ReverseAction extends UnaryOperationAction{

	
	/**
	 * Constructor.
	 * 
	 * @param calc
	 */
	public ReverseAction(CalcModelImpl calc) {
		super(calc);
	}
	
	
	/**
	 * Method performs action on the inputed angle
	 * if calculator does not have frozen value. Action performed
	 * will calculate 1/value inputed 
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws CalculatorInputException if the calculator has frozen value
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(calc.hasFrozenValue()) {
			throw new CalculatorInputException("Can't input new operation!");
		}
		double value = calc.getValue();
		value = 1/value;
		this.calc.setValue(value);
	}

}
