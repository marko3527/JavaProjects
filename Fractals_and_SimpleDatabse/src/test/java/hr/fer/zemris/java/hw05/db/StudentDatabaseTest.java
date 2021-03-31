package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void forJMBAGTest() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database/database.txt"),
												StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(lines);
		List<StudentRecord> allLines = database.filter(record -> {
			return true;
		});
		List<StudentRecord> noLines = database.filter(record -> {
			return false;
		});
		assertEquals(lines.size(), allLines.size());
		assertEquals(0, noLines.size());
	}

}
