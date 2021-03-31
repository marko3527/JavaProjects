package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;


public class ArrayIndexedCollectionTest {

	@Test
	public void noArgsTest() {
		ArrayIndexedCollection emptyCollection = new ArrayIndexedCollection();
		assertEquals(0, emptyCollection.toArray().length);
		assertEquals(0, emptyCollection.size());
	}
	
	@Test
	public void capacityArgTest() {
		int capacity = 32;
		ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity);
		assertEquals(0, collection.toArray().length);
	}
	
	@Test
	public void collectionArgTest() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection(5);
		for(int i = 0; i < 5; i++) {
			collectionToAdd.add(i);
		}
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collectionToAdd);
		assertEquals(1,newCollection.get(1));
	}
	
	@Test
	public void collectionAndCapArgTest() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection(5);
		for(int i = 0; i < 5; i++) {
			collectionToAdd.add(i);
		}
		int capacityOfNewCollection = 3;
		//capacity of new collection is smaller than the size of collection to add
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(capacityOfNewCollection,collectionToAdd);
		assertEquals(5, newCollection.toArray().length);
		assertEquals(3, newCollection.get(3));
	}
	
	@Test
	public void addMethodTest() {
		int capacity = 1;
		final ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity);
		collection.add("NY");
		assertEquals("NY", collection.get(0));
		//collection is now full and with next adding it should double the capacity, so 2*1 = 2
		collection.add("BG");
		assertThrows(NullPointerException.class, () -> {
			collection.add(null);
		});
		assertEquals(2*capacity, collection.toArray().length);
	}
	
	@Test
	public void getMethodTest() {
		final ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		collection.add("NY");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(5);
		});
		assertEquals("NY", collection.get(0));
	}
	
	@Test
	public void clearMethodTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) {
			collection.add("NY" + i);
		}
		collection.clear();
		assertEquals(0, collection.toArray().length);
	}
	
	@Test
	public void insertMethodTest() {
		int capacity = 2;
		final ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity);
		collection.add("NY");
		collection.add("SF");
		//now collection looks like : [NY,SF] and we will insert new object at index 0
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.insert("ZG", 5);
		});
		assertThrows(NullPointerException.class, () -> {
			collection.insert(null, 0);
		});
		collection.insert("ZG", 0);
		assertEquals(3, collection.size());
		assertEquals(collection.size(), collection.toArray().length);
		assertEquals("ZG", collection.get(0));
		assertEquals("SF",collection.get(2));
	}
	
	@Test
	public void indexOfMethodTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		addObjects(collection);
		assertEquals(4, collection.indexOf("NY4"));
		assertEquals(-1, collection.indexOf(null));
	}
	
	@Test
	public void removeMethodTest() {
		final ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.remove(2);
		});
		addObjects(collection);
		assertEquals(5, collection.size());
		collection.remove(3);
		assertEquals(4, collection.size());
		
	}
	
	private void addObjects(ArrayIndexedCollection collection) {
		for(int i = 0; i < 5; i++) {
			collection.add("NY" + i);
		}
	}

}
