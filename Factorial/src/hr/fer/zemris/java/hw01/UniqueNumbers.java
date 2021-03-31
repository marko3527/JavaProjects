package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program simulira strukturu uređenog binarnog stabla
 * 
 * @author Marko
 */

public class UniqueNumbers {
	static TreeNode glava = null;
	
	
	/**
	 * Glavna metoda od koje počinje izvođenje programa
	 * 
	 * @param args argumenti uneseni preko naredbenog retka, u ovom programu nisu predviđeni
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Unesite broj > ");
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine().trim();
			
			if(line.toLowerCase().equals("kraj")) {
				krajPrograma();
			}
			
			else {
				
				dodajBrojUStablo(line);
				
			}
			System.out.print("Unesite broj > ");
			
		}
		sc.close();
	}
	
	
	/**
	 * razred koji simulira strukturu jednog čvora u binarnom stablu
	 * 
	 * @author Marko
	 *
	 */
	public static class TreeNode{
		
		TreeNode left;
		TreeNode right;
		int value;
		
	}
	
	
	/**
	 * Metoda koja se brine o pogreškama koje nastaju prilikom dodavanja čvora u stablo te o ispisu 
	 * o nastalim događajima
	 * 
	 * @param line vrijednost koju je korisnik unio preko standardnog inputa
	 */
	public static void dodajBrojUStablo(String line) {

		try {
			
			if(containsValue(glava, Integer.parseInt(line))) {
				System.out.println("Broj već postoji. Preskačem.");
			}
			else {
				glava = addNode(glava, Integer.parseInt(line));
				System.out.println("Dodano.");
			}
			
		}catch(NumberFormatException ex) {
			System.out.println("'" + line + "' nije cijeli broj.");
		}
		
	}
		
	
	/**
	 * Metoda koja ispisuje sortirane brojeve, prvo od najmanjeg pa zatim u novi red od najvećeg 
	 * te prekida rad programa
	 * 
	 */
	public static void krajPrograma() {
		
		System.out.print("\nIspis od najmanjeg: ");
		sortBySmallest(glava);
		System.out.print("\nIspis od najvećeg: ");
		sortByHighest(glava);
		System.exit(0);
		
	}
	
	
	/**
	 * Rekurzivna metoda koja dodaje vrijednost u binarno stablo 
	 * 
	 * @param glava pocetni cvor traženja mjesta za ubacivanje vrijednosti
	 * @param value vrijdnost koju treba ubaciti u stablo
	 * @return vraca početni čvor traženja
	 */
	public static TreeNode addNode(TreeNode glava,int value) {
		
		if(glava == null) {
			return napraviNoviCvor(value);
		}
		
		else {
			if(!containsValue(glava, value)) {
				
				if(value > glava.value) {
					if(glava.right == null) {
						glava.right = addNode(glava.right, value);
					}
					else {
						addNode(glava.right, value);
					}
				}
				
				else {
					if(glava.left == null) {
						glava.left = addNode(glava.left, value);
					}
					else {
						addNode(glava.left, value);
					}
				}
				
			}
		}
		return glava;
	}
	
	
	/**
	 * Metoda koja stvara novi čvor
	 * 
	 * @param value vrijednost koju novi čvor treba imati
	 * @return vraća referencu na novo napravljeni čvor
	 */
	public static TreeNode napraviNoviCvor(int value) {
		
		TreeNode noviCvor = new TreeNode();
		noviCvor.value = value;
		noviCvor.right = null;
		noviCvor.left = null;
		return noviCvor;
		
	}

	
	/**
	 * Metoda koja provjerava postoji li vrijednost u stablu
	 * 
	 * @param glava početni čvor stabla
	 * @param value vrijednost za koju provjeravamo postoji li u stablu
	 * @return vrijednost postoji li parametar value u stablu
	 */
	public static boolean containsValue(TreeNode glava, int value) {
		
		if(glava == null) {
			return false;
		}
		else {
			if(glava.value == value) {
				return true;
			}
			else {
				boolean contains = containsValue(glava.left, value);
				if(!contains) {
					contains = containsValue(glava.right, value);
				}
				return contains;
			}
		}
		
	}
	
	
	/**
	 * Metoda koja ispisuje vrijednosti stabla sortirane od najmanje
	 * 
	 * @param glava pocetni cvor stabla
	 */
	public static void sortBySmallest(TreeNode glava) {
		
		if(glava == null) {
			return;
		}
		sortBySmallest(glava.left);
		System.out.print(glava.value + "  ");
		sortBySmallest(glava.right);
		
	}
	
	
	/**
	 * Metoda koja ispisuje vrijednosti stabla sortirane od najvise 
	 * 
	 * @param glava pocetni cvor stabla
	 */
	public static void sortByHighest(TreeNode glava) {
		
		if(glava == null) {
			return;
		}
		sortByHighest(glava.right);
		System.out.print(glava.value + "  ");
		sortByHighest(glava.left);
		
	}
	
	
	/**
	 * Metoda prolazi po stablu i broji koliko čvorova postoji u stablu
	 * 
	 * @param glava početni čvor stabla 
	 * @return broj čvorova u stablu
	 */
	public static int treeSize(TreeNode glava) {
		
		int sizeOfTree = 0;
		if(glava != null) {
			sizeOfTree++;
			sizeOfTree += treeSize(glava.left);
			sizeOfTree += treeSize(glava.right);
		}
		return sizeOfTree;
		

	}
	
}
