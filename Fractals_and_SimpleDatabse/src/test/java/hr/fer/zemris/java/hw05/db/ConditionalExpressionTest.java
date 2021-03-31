package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.getters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.ComparisonsOperators;

class ConditionalExpressionTest {

	@Test
	public void test() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME,
									 "Bos*",
									 ComparisonsOperators.LIKE);
		StudentRecord firstRecord = new StudentRecord("00", "Martina", "Boss", 5);
		StudentRecord secondRecord = new StudentRecord("01", "Martina", "Bišćan", 3);
		
		boolean record1Satisfies = expr.getComparisonOperator().satisfied(
								  expr.getFieldGetter().get(firstRecord),
								  expr.getStringLiteral());
		boolean record2Satisfies = expr.getComparisonOperator().satisfied(
								  expr.getFieldGetter().get(secondRecord),
								  expr.getStringLiteral());
		
		assertEquals(true, record1Satisfies);
		assertEquals(false, record2Satisfies);
	}

}
