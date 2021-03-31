package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	public void addingTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		
		dict.put(2, "Marko");
		dict.put(5, "Ivan");
		assertEquals("Marko", dict.get(2));
		assertEquals("Ivan", dict.get(5));
	}
	
	@Test
	public void clearingTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		
		dict.put(2, "Marko");
		dict.put(5, "Ivan");
		assertEquals(2, dict.size());
		
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	public void isEmptyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(2, "Marko");
		assertEquals(false, dict.isEmpty());
		dict.clear();
		assertEquals(true, dict.isEmpty());
	}

}
