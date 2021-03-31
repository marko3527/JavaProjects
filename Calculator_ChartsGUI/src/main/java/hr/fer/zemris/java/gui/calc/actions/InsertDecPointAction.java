package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class that offers implementation of performing action
 * of inserting decimal point to number.
 * 
 * @author Marko
 *
 */
public class InsertDecPointAction implements CalcAction{
	
	private CalcModelImpl calc;
	
	
	/**
	 * Constructor.
	 * 
	 * @param calc reference to the calculator model.
	 */
	public InsertDecPointAction(CalcModelImpl calc) {
		this.calc = calc;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calc.insertDecimalPoint();
		} catch (CalculatorInputException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
