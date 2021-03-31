package hr.fer.zemris.java.gui.calc.actions.unary;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.actions.CalcAction;

/**
 * Abstract class for unary operations. Unary operations
 * must have reference to the calculator to collect
 * the values from it.
 * 
 * @author Marko
 *
 */
public abstract class UnaryOperationAction implements CalcAction{
	
	protected CalcModelImpl calc;
	
	public UnaryOperationAction(CalcModelImpl calc) {
		this.calc = calc;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
