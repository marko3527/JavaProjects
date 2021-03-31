package hr.fer.zemris.java.gui.calc.actions.unary;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Action for performing cotangens on given number.
 * Number is expected to be in degrees.
 * 
 * @author Marko
 *
 */
public class CtgAction extends UnaryOperationAction{
	
	private JCheckBox invertButton;
	
	
	/**
	 * Constructor.
	 * 
	 * @param calc
	 * @param invertButton button to inverse the function
	 */
	public CtgAction(CalcModelImpl calc, JCheckBox invertButton) {
		super(calc);
		this.invertButton = invertButton;
	}
	
	
	/**
	 * Method performs action on the inputed angle
	 * if calculator does not have frozen value. Action is based 
	 * on the invert button, if this button is checked then this action 
	 * will calculate acctg of the angle, else it will calculate
	 * ctg of the angle.
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
		double value = this.calc.getValue();
		if(invertButton.isSelected()) {
			value = Math.toDegrees(Math.tan(value));
		}
		else {
			value = (double)1/Math.tan(Math.toRadians(value));
		}
		this.calc.setValue(value);
	}

}
