package hr.fer.zemris.java.gui.calc;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;


/**
 * Calculator value listener that will display on the label
 * everytime when the value changes.
 * 
 * @author Marko
 *
 */
public class DisplayListener implements CalcValueListener{
	
	private JLabel display;
	
	
	/**
	 * Constructor.
	 * 
	 * @param display
	 */
	public DisplayListener(JLabel display) {
		this.display = display;
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		display.setText(model.toString());
	}

}
