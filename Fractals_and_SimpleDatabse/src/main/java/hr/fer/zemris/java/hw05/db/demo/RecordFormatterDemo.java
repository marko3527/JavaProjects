package hr.fer.zemris.java.hw05.db.demo;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.RecordFormatter;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demo that shows the format of the outputed records. The output table is scalable and 
 * is scaled by seraching for biggest value in that column.
 * 
 * @author Marko
 *
 */
public class RecordFormatterDemo {
	
	public static void main(String[] args) {
		
		List<StudentRecord> records = new LinkedList<>();
		
		StudentRecord record1 = new StudentRecord("0000000001","Marko", "Markovic", 5);
		StudentRecord record2 = new StudentRecord("0000000004","Marko", "Ismail Markovic", 2);
		StudentRecord record3 = new StudentRecord("0000000003","Marko", "Mrkovic", 3);
		StudentRecord record4 = new StudentRecord("0000000002","Marko", "Markov", 1);
		
		records.add(record1);
		records.add(record2);
		records.add(record3);
		records.add(record4);
		
		RecordFormatter formatter = new RecordFormatter();
		formatter.format(records).stream().forEach(line -> {
			System.out.println(line);
		});;
		
	}

}
