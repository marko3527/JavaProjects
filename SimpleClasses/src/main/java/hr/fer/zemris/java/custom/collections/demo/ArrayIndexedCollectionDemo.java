package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

public class ArrayIndexedCollectionDemo {
	
	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("NY");
		col.add("SF");
		System.out.println(col.contains("NY"));
		col.remove(1);
		System.out.println(col.get(1));
		System.out.println(col.size());
		col.add("LA");
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		
		class P extends Processor{
			@Override
			public void process(Object value) throws NullPointerException {
				System.out.println(value);
			}
		}
		
		System.out.println("col elements");
		col.forEach(new P());
		
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		
		System.out.println("col2 elements:");
		col2.forEach(new P());
		
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		
		System.out.println(col.contains(col2.get(1)));
		System.out.println(col2.contains(col.get(1)));

		col.remove(Integer.valueOf(20));
	}

}
