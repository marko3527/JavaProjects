package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.operators.ComparisonsOperators;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

class ComparisonsOperatorsTest {

	@Test
	public void likeTest() {
		IComparisonOperator oper = ComparisonsOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	public void lessTest() {
		IComparisonOperator oper = ComparisonsOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void lessOrEqualsTest() {
		IComparisonOperator oper = ComparisonsOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Marko", "Marko"));
		assertEquals(true, oper.satisfied("Ana", "Marko"));
	}
	
	@Test
	public void greaterTest() {
		IComparisonOperator oper = ComparisonsOperators.GREATER;
		assertEquals(true, oper.satisfied("Marko", "Ana"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Ana", "Marko"));
	}
	
	@Test
	public void equalsTest() {
		IComparisonOperator oper = ComparisonsOperators.EQUALS;
		assertEquals(true, oper.satisfied("Marko", "Marko"));
		assertEquals(false, oper.satisfied("Maro", "Marko"));
	}
	
	@Test
	public void notEqualsTest() {
		IComparisonOperator oper = ComparisonsOperators.NOT_EQUALS;
		assertEquals(false, oper.satisfied("Marko", "Marko"));
		assertEquals(true, oper.satisfied("Ana", "Marko"));
	}

}
