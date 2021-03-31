package hr.fer.zemris.java.gui.calc;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class that implements the CalcModel interface and offers the
 * implementation of methods to properly work with calculator.
 * It has three flags, one offers the infos whether the number inputed 
 * is positive, and other  if the user can input new values into 
 * the calculator and third that tells whether the active operand is set.
 * 
 * @author Marko
 *
 */
public class CalcModelImpl implements CalcModel{
	
	private List<CalcValueListener> listeners;
	private boolean editable;
	private boolean positiveNumber;
	private double number;
	private String frozenValue;
	
	/**
	 * String where calculator remembers the inputed values
	 */
	private String inputValue;
	
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean isActiveOperandSet;
	
	
	/**
	 * Constructor.
	 * 
	 */
	public CalcModelImpl() {
		listeners = new LinkedList<CalcValueListener>();
		editable = true;
		frozenValue = null;
		inputValue = "";
		number = 0;
		positiveNumber = true;
		isActiveOperandSet = false;
		pendingOperation = null;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("You provided null reference!");
		}
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("You provided null reference!");
		}
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return number;
	}

	@Override
	public void setValue(double value) {
		if(value == Double.NEGATIVE_INFINITY) {
			inputValue = "-Infinity";
		}
		else if(value == Double.POSITIVE_INFINITY) {
			inputValue = "Infinity";
		}
		else {
			inputValue = "" + value;
		}
		number = value;
		editable = false;
		freezeValue(null);
		messageToListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		number = 0;
		inputValue = "";
		editable = true;
		positiveNumber = true;
		messageToListeners();
	}

	@Override
	public void clearAll() {
		number = 0;
		inputValue = "";
		freezeValue(null);
		editable = true;
		activeOperand = 0;
		positiveNumber = true;
		isActiveOperandSet = false;
		pendingOperation = null;
		messageToListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) {
			throw new CalculatorInputException("The number is not editable!");
		}
		positiveNumber = !positiveNumber;
		if(!positiveNumber) {
			inputValue = "-" + inputValue;
			if(inputValue.equals("-")) {
				inputValue = "-0";
				number = Double.parseDouble(inputValue);
			}
			else {
				number = Double.parseDouble(inputValue);
			}
		}
		else {
			inputValue = inputValue.replace("-", "");
			
			if(inputValue.equals("")) {
				number = Double.parseDouble("0");
			}
			else {
				number = Double.parseDouble(inputValue);
			}
		}
		messageToListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(inputValue.contains(".") || !editable) {
			throw new CalculatorInputException("The decimal point already exist!");
		}
		else if(inputValue.equals("") || inputValue.equals("-0")) {
			throw new CalculatorInputException("No digits were inputed!");
		}
		else {
			inputValue += ".";
			messageToListeners();
		}
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) {
			throw new CalculatorInputException("Can't input new digit!");
		}
		else if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit must be in interval [0, 9]");
		}
		else {
			if(hasFrozenValue()) {
				freezeValue(null);
			}
			if((number == 0 || number == -0) && digit == 0 && !inputValue.contains(".")) {
				inputValue = positiveNumber? "0" : "-0";
				messageToListeners();
				return;
			}
			try {
				String test = inputValue + digit;
				number = Double.parseDouble(test);
				if(!Double.isInfinite(number)) {
					inputValue += digit;
				}
				else {
					number = Double.parseDouble(inputValue);
					throw new CalculatorInputException("Number too big!");
				}
				
			} catch (NumberFormatException ex) {
				throw new CalculatorInputException("Can't parse inputed number!");
			}
		}
		messageToListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet) {
			throw new IllegalStateException("Active operand is not set!");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;
		
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		isActiveOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if(op == null) {
			freezeValue(null);
		}
		else {
			freezeValue(inputValue);
		}
		this.pendingOperation = op;
	}
	
	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return frozenValue;
		}
		else if(inputValue.isEmpty()) {
			return positiveNumber ? "0" : "-0";
		}
		else {
			if(positiveNumber) {
				if(!inputValue.contains(".") && inputValue.length() > 1
						&& inputValue.charAt(0) == '0') {
						inputValue = inputValue.substring(1);
						return inputValue;
				}
			}
			else {
				if(!inputValue.contains(".") && inputValue.length() > 2
					 && inputValue.substring(0,2).equals("-0")) {
					inputValue = "-" + inputValue.substring(2);
					return inputValue;
				}
			}
			if(positiveNumber && inputValue.charAt(0) == '0') {
				inputValue = inputValue.replaceFirst("00+\\.", "0.");
				inputValue = inputValue.replaceFirst("00+", "0");
			}
			if(!positiveNumber && inputValue.charAt(0) == '0') {
				inputValue = inputValue.replaceFirst("-00+\\.", "-0.");
				inputValue = inputValue.replaceFirst("-00+", "-0");
			}
		}
		
		
		return inputValue;
		
	}
	
	/**
	 * Freezes the given value and stores it into frozenValue 
	 * attribute.
	 * 
	 * @param value {@code String}
	 */
	public void freezeValue(String value) {
		this.frozenValue = value;
	}
	
	
	/**
	 * Method that checks whether the calculator has frozen value.
	 * 
	 * @return {@code true} if calculator has frozen value
	 * 		   {@code false} if calculator doesn't have frozen value
	 */
	public boolean hasFrozenValue() {
		return frozenValue == null? false : true;
	}
	
	/**
	 * Method that send the message to every listener that 
	 * value in calculator has been changed.
	 */
	private void messageToListeners() {
		for(CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

}
