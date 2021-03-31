package hr.fer.zemris.java.gui.calc.actions;

import java.awt.event.ActionEvent;
import java.util.EmptyStackException;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Class that offers implementation of performing action
 * of poping number from stack.
 * 
 * @author Marko
 *
 */
public class PopAction implements CalcAction{

	private CalcModelImpl calc;
	private Stack<Double> numberStack;
	
	/**
	 * Constructor.
	 * 
	 * @param numberStack stack of numbers
	 * @param calc reference to calc model
	 */
	public PopAction(Stack<Double> numberStack, CalcModelImpl calc) {
		this.calc = calc;
		this.numberStack = numberStack;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calc.setValue(numberStack.pop());
		} catch (EmptyStackException ex) {
			System.out.println("No numbers on stack!");
		}
	}
}
