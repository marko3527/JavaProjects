package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class that implements the action for performing an binary
 * action on two numbers. This action will be attached to
 * button equals '='.
 * 
 * @author Marko
 *
 */
public class ResultAction implements CalcAction{
	
	private CalcModelImpl calc;
	
	
	/**
	 * Constructor.
	 * 
	 * @param calc reference to calc model.
	 */
	public ResultAction(CalcModelImpl calc) {
		this.calc = calc;
	}

	
	/**
	 * Method that performs the pending operation and displays the 
	 * value calculated.
	 * {@inheritDoc}
	 * 
	 * @throws CalculatorInputException if the user asks this action to be
	 * performed multiple times in a row without inputing new digit
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			double secondOperand = calc.getValue();
			double result = calc.getPendingBinaryOperation().
							applyAsDouble(calc.getActiveOperand(), secondOperand);
			calc.setValue(result);
			calc.clearActiveOperand();
			calc.setPendingBinaryOperation(null);
		} catch (CalculatorInputException | IllegalStateException e2) {
			System.out.println(e2.getMessage());
		}
		
	}

}
