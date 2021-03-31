package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.filters.QueryFilter;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw05.db.parser.QueryParser;

/**
 * The class that implements a user input interface for writing querys. Program 
 * terminates itself if the user writes 'exit'.And user is allowed to write just querys
 * by using keyword query.
 * 
 * @author Marko
 *
 */
public class StudentDB {
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args command line arguments
	 * @throws IOException if there is problem with reading database text file
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database/database.txt"),
												StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		QueryParser parser = new QueryParser("");
		RecordFormatter formatter = new RecordFormatter();
		System.out.print(">");
		String query = sc.nextLine();
		
		while(!query.equals("exit")) {
			
			try{
				parser = new QueryParser(query);
				LinkedList<StudentRecord> records = new LinkedList<StudentRecord>();
				
				if(parser.isDirectQuery()) {
					StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
					records.add(r);
					formatter.format(records).stream().forEach(System.out :: println);
				}
				
				else {
					for(StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
						records.add(r);
					}
					formatter.format(records).stream().forEach(System.out :: println);
				}
			}catch(IllegalArgumentException | QueryLexerException ex) {
				System.out.println(ex.getMessage());
			}
			
			System.out.print("\n>");
			query = sc.nextLine();
		}
		
		System.out.println("Goodbye!");
		sc.close();
	}

}
