package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class that inputs the given number into calculator
 * 
 * @author Marko
 *
 */
public class NumberAction implements CalcAction{
	
	private CalcModelImpl calculatorModel;
	private int digit;
	
	
	/**
	 * Constructor. It takes the implementation of calculator model
	 * and given digit to insert into model.
	 * 
	 * @param calculatorModel {@code CalcModelImpl} implementation od model.
	 * @param digit {@code int} digit to insert
	 */
	public NumberAction(CalcModelImpl calculatorModel, int digit) {
		if(calculatorModel != null) {
			this.calculatorModel = calculatorModel;
			this.digit = digit;
		}
		else {
			throw new NullPointerException("You provided null reference!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calculatorModel.insertDigit(digit);
		}catch(CalculatorInputException | IllegalArgumentException ex ) {
			System.out.println(ex.getMessage());
		}
		
	}

	/**
	 * Setter.
	 * @param digit
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}
	


}
