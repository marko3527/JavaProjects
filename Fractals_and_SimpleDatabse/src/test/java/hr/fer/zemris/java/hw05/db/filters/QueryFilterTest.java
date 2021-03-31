package hr.fer.zemris.java.hw05.db.filters;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import hr.fer.zemris.java.hw05.db.getters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.ComparisonsOperators;

class QueryFilterTest {

	@Test
	public void filterinTest() {
		StudentRecord record1 = new StudentRecord("0000000001", "Andrea", "Andrić", 5);
		StudentRecord record2 = new StudentRecord("0000000002", "Borna", "Slovenec", 5);
		StudentRecord record3 = new StudentRecord("0000000003", "Marko", "Ilijaš", 5);
		StudentRecord record4 = new StudentRecord("0000000004", "Andrea", "Srbić", 5);
		StudentRecord record5 = new StudentRecord("0000000005", "Kristina", "Andrić", 5);
		StudentRecord record6 = new StudentRecord("0000000006", "Danijela", "Mišković", 5);
		ConditionalExpression firstExpression = new ConditionalExpression(
												FieldValueGetters.FIRST_NAME,
												"A",
												ComparisonsOperators.GREATER
												);
		ConditionalExpression secondExpression = new ConditionalExpression(
												 FieldValueGetters.JMBAG,
												 "0000000002",
												 ComparisonsOperators.GREATER
												 );
		List<ConditionalExpression> expressions = new LinkedList<>();
		LinkedList<StudentRecord> records = new LinkedList<>();
		expressions.add(firstExpression);
		expressions.add(secondExpression);
		records.add(record1);
		records.add(record2);
		records.add(record3);
		records.add(record4);
		records.add(record5);
		records.add(record6);
		
		StudentDatabase database = new StudentDatabase(records);
		List<StudentRecord> accepted = database.filter(new QueryFilter(expressions));
		assertEquals(4, accepted.size());
		assertEquals("Kristina", accepted.get(2).getFirstName());
		
	}

}
