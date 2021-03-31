package hr.fer.zemris.java.gui.calc.elements;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.actions.CalcAction;
import hr.fer.zemris.java.gui.calc.actions.binary.ToPowerAction;
import hr.fer.zemris.java.gui.calc.actions.unary.CosAction;
import hr.fer.zemris.java.gui.calc.actions.unary.CtgAction;
import hr.fer.zemris.java.gui.calc.actions.unary.LnAction;
import hr.fer.zemris.java.gui.calc.actions.unary.LogAction;
import hr.fer.zemris.java.gui.calc.actions.unary.ReverseAction;
import hr.fer.zemris.java.gui.calc.actions.unary.SinusAction;
import hr.fer.zemris.java.gui.calc.actions.unary.TanAction;
import hr.fer.zemris.java.gui.layouts.RCPosition;


/**
 * Class that implements the buttons that perform some unary operations
 * with calculator values.
 * 
 * @author Marko
 *
 */
public class FunctionPad extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> functionButtons = new LinkedList<JButton>();
	private CalcModelImpl calc;
	private RCPosition startingPosition;
	private JCheckBox checkBox;
	private String[] inverted = {"1/x", "arcsin", "10^x", "arccos", "e^x", "arctan",
								"x^(1/n)", "arcctg"};
	private String[] uninverted = {"1/x", "sin", "log", "cos", "ln", "tan", "x^n", "ctg"};
	
	/**
	 * This flag indicates which type of functions are going to be written in
	 * button text. If it is false then active functions are uninverted else active
	 * functions are inverted
	 * 
	 */
	private boolean currentFunctions = false;
	
	
	/**
	 * Constructor.Takes two args, first is the starting position
	 * of the first function button.Second is reference to the
	 * calculator model.
	 * 
	 * @param startingPosition
	 * @param calc
	 */
	public FunctionPad(RCPosition startingPosition, CalcModelImpl calc) {
		this.calc = calc;
		this.startingPosition = startingPosition;
		setUpButtons();
	}
	
	
	/**
	 * Method that adds the buttons to the given container.
	 * 
	 * @param cp
	 */
	public void addToContainer(Container cp) {
		Map<String, RCPosition> buttonPositions = new HashMap<>();
		RCPosition inverse = startingPosition;
		RCPosition sin = new RCPosition(startingPosition.getRowIndex(), startingPosition.getColumnIndex() + 1);
		RCPosition log = new RCPosition(startingPosition.getRowIndex() + 1, startingPosition.getColumnIndex());
		RCPosition cos = new RCPosition(startingPosition.getRowIndex() + 1, startingPosition.getColumnIndex() + 1);
		RCPosition ln = new RCPosition(startingPosition.getRowIndex() + 2, startingPosition.getColumnIndex());
		RCPosition tan = new RCPosition(startingPosition.getRowIndex() + 2, startingPosition.getColumnIndex() + 1);
		RCPosition toThePower = new RCPosition(startingPosition.getRowIndex() + 3, startingPosition.getColumnIndex());
		RCPosition ctg = new RCPosition(startingPosition.getRowIndex() + 3, startingPosition.getColumnIndex() + 1);
		
		buttonPositions.put("1/x", inverse);
		buttonPositions.put("sin", sin);
		buttonPositions.put("log", log);
		buttonPositions.put("cos", cos);
		buttonPositions.put("ln", ln);
		buttonPositions.put("tan", tan);
		buttonPositions.put("x^n", toThePower);
		buttonPositions.put("ctg", ctg);
		
		for(JButton button : functionButtons) {
			cp.add(button, buttonPositions.get(button.getName()));
		}
	}
	
	@Override
	public void addActionListener(ActionListener l) {
		Map<String,CalcAction> actions = new HashMap<>();
		actions.put("sin", new SinusAction(calc, checkBox));
		actions.put("cos", new CosAction(calc, checkBox));
		actions.put("tan", new TanAction(calc, checkBox));
		actions.put("ctg", new CtgAction(calc, checkBox));
		actions.put("log", new LogAction(calc, checkBox));
		actions.put("ln", new LnAction(calc, checkBox));
		actions.put("1/x", new ReverseAction(calc));
		actions.put("x^n", new ToPowerAction(calc, checkBox));
		
		for(JButton button : functionButtons) {
			button.addActionListener(actions.get(button.getName()));
		}
	}
	
	
	/**
	 * Setts the checkbox so the functions know if they should 
	 * inverse the operation.
	 * 
	 * @param checkBox
	 */
	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}
	

	
	/**
	 * Method for inital set up of the function buttons.
	 */
	private void setUpButtons() {
		JButton reversed = new JButton(uninverted[0]);
		reversed.setName(uninverted[0]);
		
		JButton sin = new JButton(uninverted[1]);
		sin.setName(uninverted[1]);
		
		JButton log = new JButton(uninverted[2]);
		log.setName(uninverted[2]);
		
		JButton cos = new JButton(uninverted[3]);
		cos.setName(uninverted[3]);
		
		JButton ln = new JButton(uninverted[4]);
		ln.setName(uninverted[4]);
		
		JButton tan = new JButton(uninverted[5]);
		tan.setName(uninverted[5]);
		
		JButton power = new JButton(uninverted[6]);
		power.setName(uninverted[6]);
		
		JButton ctg = new JButton(uninverted[7]);
		ctg.setName(uninverted[7]);
		functionButtons.add(reversed);
		functionButtons.add(sin);
		functionButtons.add(log);
		functionButtons.add(cos);
		functionButtons.add(ln);
		functionButtons.add(tan);
		functionButtons.add(power);
		functionButtons.add(ctg);
		
		for(JButton button : functionButtons) {
			button.setBackground(Calculator.buttonColor);
			button.setFont(button.getFont().deriveFont(15f));
		}
	}
	
	
	/**
	 * Method that will be used to reverse the text of the function
	 * buttons.
	 * @param checkBoxSelected if the checkbox is selected
	 */
	public void reverseTheFunctions(boolean checkBoxSelected) {
		
		if(checkBoxSelected && checkBoxSelected != currentFunctions) {
			for(int i = 0; i < functionButtons.size(); i++) {
				functionButtons.get(i).setText(inverted[i]);
			}
			currentFunctions = checkBoxSelected;
		}
		
		else if(checkBoxSelected != currentFunctions) {
			for(int i = 0; i < functionButtons.size(); i++) {
				functionButtons.get(i).setText(uninverted[i]);
			}
			currentFunctions = checkBoxSelected;
		}
		
	}

}
