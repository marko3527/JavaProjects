package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class that offers implementation of performing action
 * of swaping signs on number.
 * 
 * @author Marko
 *
 */
public class SwapAction implements CalcAction{
	
	private CalcModelImpl calc;
	
	/**
	 * Constructor.
	 * 
	 * @param calc reference to the calculator model.
	 */
	public SwapAction(CalcModelImpl calc) {
		this.calc = calc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calc.swapSign();
		}catch(CalculatorInputException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
