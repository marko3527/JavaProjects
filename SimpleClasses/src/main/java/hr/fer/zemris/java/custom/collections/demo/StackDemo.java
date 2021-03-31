package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class for testing {@code ObjectStack} class.
 * It calculates the math expression based on postfix expression.
 * 
 * @author Marko
 *
 */
public class StackDemo {
	
	
	/**
	 * Main method that parses postfix expression anc calculates the result
	 * 
	 * @param args command line arguments, it can accept just one argument
	 * and it has to be written in postfix
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("I can accept just one argument, not less or more! ");
			System.exit(0);
		}
		String lineInPostfix = args[0];
		String[] elementsInExpression = lineInPostfix.split("\\s");
		
		ObjectStack stack = new ObjectStack();
		for(String element : elementsInExpression) {
			if(checkIfNumber(element)) {
				stack.push(Integer.parseInt(element));
			}
			else {
				int[] numbers = popTwoNumbers(stack);
				if(element.equals("*")) {
					multiply(numbers, stack);
				}
				else if(element.equals("/")) {
					divide(numbers, stack);
				}
				else if(element.equals("+")) {
					add(numbers, stack);
				}
				else if(element.equals("-")) {
					subtract(numbers, stack);
				}
				else if(element.equals("%")) {
					divideWithResidue(numbers, stack);
				}
				
			}
		}
		if(stack.size() != 1) {
			printOutInformations();
		}
		else {
			System.out.println(stack.pop());
		}
	}
	
	/**
	 * method that prints the information about the wrong input and exits the program
	 */
	private static void printOutInformations() {
		System.err.println("The postfix expression was not written correctly! ");
		System.exit(0);
	}
	
	/**
	 * Method that performs division with residue on two numbers and pushes 
	 * the result to stack.
	 * 
	 * @param numbers to perform an operation on
	 * @param stack
	 */
	private static void divideWithResidue(int[] numbers, ObjectStack stack) {
		int result = numbers[1] % numbers[0];
		stack.push(result);
	}

	/**
	 * Method that performs division with residue on two numbers and pushes 
	 * the result to stack.
	 * 
	 * @param numbers to perform an operation on
	 * @param stack
	 */
	private static void subtract(int[] numbers, ObjectStack stack) {
		int result = numbers[1] - numbers[0];
		stack.push(result);
	}

	/**
	 * Method that performs adding on two numbers and pushes 
	 * the result to stack.
	 * 
	 * @param numbers to perform an operation on
	 * @param stack
	 */
	private static void add(int[] numbers, ObjectStack stack) {
		int result = numbers[1] + numbers[0];
		stack.push(result);
	}

	/**
	 * Method that performs dividing on two numbers and pushes 
	 * the result to stack.
	 * 
	 * @param numbers to perform an operation on
	 * @param stack
	 */
	private static void divide(int[] numbers, ObjectStack stack) {
		int result = numbers[1] / numbers[0];
		stack.push(result);
	}

	/**
	 * Method that performs multiplication on two numbers and pushes 
	 * the result to stack.
	 * 
	 * @param numbers to perform an operation on
	 * @param stack
	 */
	private static void multiply(int[] numbers, ObjectStack stack) {
		int result = numbers[1] * numbers[0];
		stack.push(result);
	}

	/**
	 * Method that pops two numbers from stack if they are not null. And either
	 * one of them is null that means that expression is not properly written
	 * 
	 * @param stack collection of elements
	 * @return field of two number
	 * @throws IllegalArgumentException if postfix is not written properly
	 */
	private static int[] popTwoNumbers(ObjectStack stack) {
		int[] fieldToReturn = new int[2];
		if(stack.isEmpty() || stack.peek() == null) {
			printOutInformations();
		}
		else {
			int first = (int) stack.pop();
			fieldToReturn[0] = first;
			if(stack.isEmpty() || stack.peek() == null) {
				printOutInformations();
			}
			else {
				int second = (int) stack.pop();
				fieldToReturn[1] = second;
			}
		}
		return fieldToReturn;
		
	}
	
	/**
	 * Checks whether the element is number
	 * 
	 * @param element of type {@code String} 
	 * @return {@code true} if it can be parsed as number
	 * 		   {@code false} if it can't be parsed as number
	 */
	private static boolean checkIfNumber(String element) {
		if(element == null) {
			return false;
		}
		else {
			try {
				Integer.parseInt(element);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		
	}

}
