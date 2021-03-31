package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	public void addingNullValue() {
		
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.add(value2);
		
		assertEquals(0, value1.getValue());
		assertEquals(null, value2.getValue());
		
	}
	
	
	@Test
	public void addingDoubles() {
		
		ValueWrapper value1 = new ValueWrapper(1.73);
		ValueWrapper value2 = new ValueWrapper(2.07);
		
		value1.add(value2.getValue());
		
		assertEquals(3.80, value1.getValue());
		assertEquals(2.07, value2.getValue());
	}
	
	
	@Test
	public void addingParsableStrings() {
		
		ValueWrapper value1 = new ValueWrapper("1.2E1");
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(1));
		
		value1.add(value2.getValue());
		
		assertEquals(13.0, value1.getValue());
		assertEquals(1, value2.getValue());
	}
	
	
	@Test
	public void stringNotNumber() {
		
		ValueWrapper value1 = new ValueWrapper("Ankica");
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			value1.add(value2.getValue());
		});
	}
}
