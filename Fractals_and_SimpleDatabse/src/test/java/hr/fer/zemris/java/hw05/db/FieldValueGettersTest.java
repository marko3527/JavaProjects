package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.getters.FieldValueGetters;

class FieldValueGettersTest {

	@Test
	public void fieldValueGetters() {
		StudentRecord record = new StudentRecord("0000000001", "Ivo",
												 "Ivić", 5);
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
		assertEquals("Ivo", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Ivić", FieldValueGetters.LAST_NAME.get(record));
	}

}
