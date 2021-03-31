package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class LinkedListIndexedCollectionTest {

	@Test
	public void defaultContructorTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void constructorTest() {
		ArrayIndexedCollection otherCol = new ArrayIndexedCollection();
		otherCol.add("NY");
		otherCol.add("SF");
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(otherCol);
		assertEquals("NY",collection.get(0));
		assertEquals("SF", collection.get(1));
		assertEquals(2, collection.size());
	}
	
	@Test
	public void addMethodTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Marko");
		assertEquals(1, collection.size());
		assertEquals("Marko", collection.toArray()[0]);
	}
	
	@Test
	public void getMethodTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kozice");
		collection.add("Riza");
		assertEquals("Kozice", collection.get(0));
		assertEquals("Riza", collection.get(1));
	}
	
	@Test
	public void clearMethodTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kozice");
		collection.add("Riza");
		collection.add("Vino");
		assertEquals(3, collection.size());
		collection.clear();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void insertMethodTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kozice");
		collection.add("Riza");
		collection.add("Vino");
		/*
		 * we insert a new item to our collection and check if all other list nodes
		 * have shifted to right from that index of insertion
		 */
		assertEquals(1,collection.indexOf("Riza"));
		collection.insert("Ulje", 1);
		assertEquals(2, collection.indexOf("Riza"));
		assertEquals(1, collection.indexOf("Ulje"));
	}
	
	@Test
	public void indexOfMethodTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("crvena");
		collection.add("zelena");
		collection.add("plava");
		assertEquals(0, collection.indexOf("crvena"));
		assertEquals(2, collection.indexOf("plava"));
	}
	
	@Test
	public void removeMethodTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		/*we have 3 items in out collection then we remove one and check if size 
		  has changed and if list nodes have shifted to left 
		*/
		assertEquals(3, collection.size());
		collection.remove(1);
		assertEquals(2, collection.size());
		assertEquals(3, collection.get(1));
	}

}
