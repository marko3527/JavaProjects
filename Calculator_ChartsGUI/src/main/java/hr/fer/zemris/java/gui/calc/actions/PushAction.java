package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;


/**
 * Class that offers implementation of performing action
 * of pushing number to stack.
 * 
 * @author Marko
 *
 */
public class PushAction implements CalcAction{
	
	private Stack<Double> numberStack;
	private CalcModelImpl calc;
	
	
	/**
	 * Constructor.
	 * 
	 * @param numberStack stack of numbers
	 * @param calc reference to calc model
	 */
	public PushAction(Stack<Double> numberStack, CalcModelImpl calc) {
		this.numberStack = numberStack;
		this.calc = calc;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		numberStack.push(calc.getValue());
	}

}
