package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji izračunava faktorijel broja
 * 
 * @author Marko
 *
 */

public class Factorial {
	
	
	/**
	 * Metoda iz koje počinje izvođenje programa
	 * 
	 * @param args argumenti zadani preko naredbenog retka
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNextLine()) {
			String brojZaIzracun = sc.nextLine().trim();
			
			//uvjet za kraj programa je kada korisnik unese kraj
			if(brojZaIzracun.toLowerCase().equals("kraj")) {
				System.out.println("\nDoviđenja.");
				System.exit(0);
			}
			
			else {
				
				try {
					double rezultat = izracunajFaktorijel(Integer.parseInt(brojZaIzracun));
					System.out.printf("%s! = %.0f%n", brojZaIzracun, rezultat);
					
				}catch (NumberFormatException e) {
					System.out.println("'" + brojZaIzracun + "' nije cijeli broj.");
					
				}catch(IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
				
			}
		}
		sc.close();
	}
	
	
	/**
	 * Metoda racuna faktorijel broja unesenog preko naredbenog retka
	 * 
	 * @param brojZaIzracun broj ciji faktorijel se treba izracunati
	 * @return vraca izracunati faktorijel
	 */
	public static double izracunajFaktorijel(int brojZaIzracun) {
		
		if(brojZaIzracun < 3 || brojZaIzracun > 20) {
			throw new IllegalArgumentException("'" + brojZaIzracun + "' nije u dozvoljenom rasponu.");
		}
		
		double rezultat = 1;
		
		while(brojZaIzracun > 0) {
			rezultat = rezultat * brojZaIzracun;
			brojZaIzracun--;
		}
		
		return rezultat;
	}

}
