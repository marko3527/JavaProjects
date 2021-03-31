package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class SimpleHashTableTest {
	
	SimpleHashTable<Integer, String> highScoreTable = new SimpleHashTable<>(4);

	@Test
	public void puttingTest() {
		highScoreTable.put(1, "30450");
		assertEquals("[1=30450]", highScoreTable.toString());
	}
	
	@Test
	public void sizeTest() {
		highScoreTable.put(2, "30400");
		assertEquals(1, highScoreTable.size());
		highScoreTable.put(3, "29780");
		assertEquals(2, highScoreTable.size());
	}
	
	@Test
	public void owerwritingTest() {
		highScoreTable.put(2, "30400");
		assertEquals("30400", highScoreTable.get(2));
		highScoreTable.put(2, "32000");
		assertEquals(1, highScoreTable.size());
		assertEquals("32000", highScoreTable.get(2));
	}
	
	@Test
	public void clearTest() {
		highScoreTable.put(2, "30400");
		highScoreTable.put(3, "28000");
		highScoreTable.put(4, "22000");
		assertEquals(3, highScoreTable.size());
		highScoreTable.clear();
		assertEquals(0, highScoreTable.size());
		assertEquals(false, highScoreTable.containsKey(4));
	}
	
	@Test
	public void example1Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		assertEquals(5, examMarks.get("Kristina"));
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void example2Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		StringBuilder iteratorSb = new StringBuilder();
		StringBuilder mockedSb = new StringBuilder();
		mockedSb.append("Ante=>2Ivana=>5Jasna=>2Kristina=>5");
		for(SimpleHashTable.TableEntry<String, Integer> pair : examMarks) {
			iteratorSb.append(String.format("%s=>%d", pair.getKey(), pair.getValue()));
		}
		assertEquals(mockedSb.toString(), iteratorSb.toString());
	}
	
	@Test
	public void example3Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		StringBuilder iteratorSb = new StringBuilder();
		StringBuilder mockedSb = new StringBuilder();
		mockedSb.append("(Ante=>2)-(Ante=>2)\r\n" + 
				"(Ante=>2)-(Ivana=>5)\r\n" + 
				"(Ante=>2)-(Kristina=>5)\r\n" + 
				"(Ivana=>5)-(Ante=>2)\r\n" + 
				"(Ivana=>5)-(Ivana=>5)\r\n" + 
				"(Ivana=>5)-(Kristina=>5)\r\n" + 
				"(Kristina=>5)-(Ante=>2)\r\n" + 
				"(Kristina=>5)-(Ivana=>5)\r\n" + 
				"(Kristina=>5)-(Kristina=>5)\r\n");
		for(SimpleHashTable.TableEntry<String, Integer> pair1 : examMarks) {
			for(SimpleHashTable.TableEntry<String, Integer> pair2 : examMarks) {
				iteratorSb.append(String.format("(%s=>%d)-(%s=>%d)%n", pair1.getKey(), pair1.getValue(),
														 pair2.getKey(), pair2.getValue()));
			}
		}
		assertEquals(mockedSb.toString(), iteratorSb.toString());
	}
	
	@Test
	public void example4Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashTable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while(iterator.hasNext()) {
			SimpleHashTable.TableEntry<String, Integer> pair = iterator.next();
			if(pair.getKey().equals("Ivana")) {
				iterator.remove();
			}
		}
		assertEquals(false, examMarks.containsKey("Ivana"));
	}
	
	@Test
	public void example5Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashTable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while(iterator.hasNext()) {
			SimpleHashTable.TableEntry<String, Integer> pair = iterator.next();
			if(pair.getKey().equals("Ivana")) {
				iterator.remove();
				assertThrows(IllegalStateException.class, () -> {iterator.remove();});
			}
		}
	}
	
	@Test
	public void example6Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashTable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		assertThrows(ConcurrentModificationException.class, () -> {
			while(iterator.hasNext()) {
				SimpleHashTable.TableEntry<String, Integer> pair = iterator.next();
				if(pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		});
	}
	
	@Test
	public void example7Test() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", null);
		
		StringBuilder mockedSb = new StringBuilder();
		StringBuilder iteratorSb = new StringBuilder();
		mockedSb.append("Ante=>2\r\n" + 
				"Ivana=>null\r\n" + 
				"Jasna=>2\r\n" + 
				"Kristina=>5\r\n");
		Iterator<SimpleHashTable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashTable.TableEntry<String, Integer> pair = iter.next();
			iteratorSb.append(String.format("%s=>%d%n", pair.getKey(), pair.getValue()));
			iter.remove();
		}
		assertEquals(mockedSb.toString(), iteratorSb.toString());
		assertEquals(0, examMarks.size());
	}
	
	

}
