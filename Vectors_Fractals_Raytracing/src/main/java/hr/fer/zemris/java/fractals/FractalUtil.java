package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;

/**
 * Class that offers method for user inputing the roots of the fractal
 * 
 * @author Marko
 *
 */
public class FractalUtil {
	
	/**
	 * Method that asks user to input the roots and loads them into a list.
	 * 
	 * @param sc {@code Scanner}
	 * @return {@code List} list of roots
	 */
	public static List<Complex> readRoots(Scanner sc){
		String line = "";
		List<Complex> roots = new LinkedList<>();
		
		System.out.print("Root 1>");
		while(!(line = sc.nextLine().trim()).startsWith("done")) {
			
			line = line.replace("\\s+", " ");
			if(line.isEmpty()) {
				continue;
			}
			String[] splitLine = line.split(" ");
			try {
				switch(splitLine.length) {
				case(1):
					if(splitLine[0].contains("i")) {
						String number = splitLine[0].replace("i", "");
						if(number.isEmpty()) {
							roots.add(new Complex(0, 1));
						}
						else if(number.equals("-")) {
							roots.add(new Complex(0, -1));
						}
						else {
							roots.add(new Complex(0, Double.parseDouble(number)));
						}
						
					}
					else {
						roots.add(new Complex(Double.parseDouble(splitLine[0]), 0));
					}
					break;
					
				case(2):
					String rp = splitLine[0];
					String imp = splitLine[1].replace("\\+", "").replace("i", "");
					roots.add(new Complex(Double.parseDouble(rp), Double.parseDouble(imp)));
					break;
					
				case(3):
					String realPart = splitLine[0];
					String imaginaryPart = splitLine[1] + splitLine[2].replace("i", "");
					if(imaginaryPart.equals("+") || imaginaryPart.equals("-")) {
						imaginaryPart += 1;
						imaginaryPart = imaginaryPart.replace("+", "");
					} 
					roots.add(new Complex(Double.parseDouble(realPart), Double.parseDouble(imaginaryPart)));
					break;
				}
			}catch (NumberFormatException ex) {
				System.out.print("Root not properly provided. Please enter again!");
				System.out.print("\nRoot " + (roots.size() + 1) + ">");
				continue;
			}
			System.out.print("Root " + (roots.size() + 1) + ">");
		}
		
		return roots;
	}


}
