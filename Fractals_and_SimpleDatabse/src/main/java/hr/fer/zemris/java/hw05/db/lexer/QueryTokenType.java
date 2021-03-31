package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Type of tokens that could be find in one query.
 * SYMBOL = symbols for comparing string literals
 * LITERAL = string literal
 * KEY_WORD = words used for combining filters in a query(AND,...)
 * RECORD_FIELD = words that are equal to the fields in 
 * StudentRecord(jmbag,finalGrade,firstName,lastName).
 * 
 * @author Marko
 *
 */
public enum QueryTokenType {
	LITERAL, SYMBOL, KEY_WORD, RECORD_FIELD, EOF
}
