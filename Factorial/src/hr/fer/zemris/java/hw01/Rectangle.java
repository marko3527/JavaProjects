package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji izračunava opseg i površinu pravokutnika
 * 
 * @author Marko
 *
 */

public class Rectangle {
	
	
	/**
	 * Glavna metoda od koje počinje izvođenje programa
	 * 
	 * @param args argumenti uneseni preko naredbenog retka
	 */
	public static void main(String[] args) {
		
		if(args.length == 1 || args.length > 2) {
			System.out.println("Unijeli ste neispravan broj argumenata.");
			System.exit(0);
		}
		
		//u slučaju da smo dobili 2 argumenta odmah računamo i ispisujemo opseg i površinu
		else if(args.length == 2) {
			
			try {
				double sirina = Double.parseDouble(args[0]);
				double visina = Double.parseDouble(args[1]);
				izracunajOpsegIPovrsinu(sirina,visina);
				
			}catch(NumberFormatException ex) {
				System.out.println("Argumenti moraju biti brojevi.");
				System.exit(0);
			}
			
		}
		
		else if(args.length == 0) {
			
			Scanner sc = new Scanner(System.in);
			double sirina = unesiVrijednost(sc, "Unesi širinu > ");
			double visina = unesiVrijednost(sc, "Unesi visinu > ");
			izracunajOpsegIPovrsinu(sirina,visina);
			sc.close();
			
		}
		
	}
	
	
	/**
	 * Metoda koja izracunava povrsinu pravokutnika
	 * 
	 * @param visina visina pravokutnika
	 * @param sirina sirina pravokutnika
	 * @return povrsinu pravokutnika
	 */
	public static double izracunajPovrsinu(double sirina, double visina) {
		
		return visina * sirina;
		
	}
	
	
	/**
	 * Metoda koja izracunava opseg pravokutnika
	 * 
	 * @param visina visina pravokutnika
	 * @param sirina sirina pravokutnika
	 * @return visinu pravokutnika
	 */
	public static double izracunajOpseg(double sirina, double visina) {
		
		return 2*visina + 2*sirina;
		
	}
	
	
	/**
	 * Metoda koja se brine da korisnik unese ispravne argumente pravokutnika
	 * 
	 * @param sc skener koji se omotova oko standardnog inputa
	 * @param porukaKorisniku uputa korisniku koju vrijednost da upiše
	 * @return unesena vrijednost preko standardnog inputa (tipkovnice)
	 */
	public static double unesiVrijednost(Scanner sc, String porukaKorisniku) {
		
		boolean imamVrijednost = false;
		double vrijednost = 0;
		String linija;
		
		//traži od korisnika unos sve dok ne dobijemo zadovoljavajući broj
		while(!imamVrijednost) {
			System.out.print(porukaKorisniku);
			linija = sc.nextLine().trim();
			
			try {
				vrijednost = Double.parseDouble(linija);
				if(vrijednost < 0) {
					System.out.println("Unijeli ste negativan broj.");
				}
				else {
					imamVrijednost = true;
				}
				
			}catch(NumberFormatException ex) {
				System.out.println("'" + linija + "' se ne može protumačiti kao broj.");
			}
		}
		
		return vrijednost;
		
	}
	
	
	/**
	 * Metoda koja brine o izracunu opsega i povrsine i radi ispis 
	 * 
	 * @param sirina sirina pravokutnika
	 * @param visina visina pravokutnika
	 */
	public static void izracunajOpsegIPovrsinu(double sirina, double visina) {
		
		double povrsina = izracunajPovrsinu(sirina, visina);
		double opseg = izracunajOpseg(sirina, visina);
		System.out.printf("\nPravokutnik širine %.1f i visine %.1f ima površinu %.2f te opseg %.2f." ,
							sirina, visina, povrsina, opseg);
		
	}

}
