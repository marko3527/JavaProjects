package hr.fer.zemris.java.gui.calc.actions.binary;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.actions.CalcAction;

/**
 * Action used in calculator for performing 
 * binary operations such as *,/,-,+.Binary operations 
 * have the reference to calculator to fetch the values from
 * it.
 * 
 * @author Marko
 *
 */
public abstract class BinaryOperationAction implements CalcAction{
	
	protected CalcModelImpl calc;
	
	
	/**
	 * Constructor.
	 * 
	 * @param calc {@code CalcModelImpl} implementation of calculator model
	 * @param operator {@code String} operator that should be inputed
	 */
	public BinaryOperationAction(CalcModelImpl calc) {
		this.calc = calc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
