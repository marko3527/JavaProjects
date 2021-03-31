package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class that is generating tokens of query by analysing every character of it.
 * It has one constructor that accepts text which should be tokenized, and
 * provides methods for working with tokens.
 * 
 * @author Marko
 *
 */
public class QueryLexer {
	
	private char[] data;
	private QueryToken currentToken;
	private int currentIndex;
	
	/**
	 * Constructor.
	 * 
	 * @param text {@code String}, text that should be tokenized.
	 * @throws NullPointerException if argument text is null
	 */
	public QueryLexer(String text) {
		if(text == null) {
			throw new NullPointerException("Given text is null! Lexer can't tokenize it!");
		}
		
		data = text.toCharArray();
	}
	
	/**
	 * Method that returns last generated token.It can be called more times,
	 * it doesn't generate new token.	
	 * 
	 * @return {@code QueryToken} last token.
	 */
	public QueryToken getToken() {
		return currentToken;
	}
	
	/**
	 * Method that generates and returns the next token.
	 * 
	 * @return {@code QueryToken} generated token.
	 * @throws QueryLexerException if user asks for tokens and there is no more
	 */
	public QueryToken nextToken() {
		if(currentToken != null && currentToken.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("There is no more tokens!");
		}
		
		findNewToken();
		return getToken();
	}
	
	
	/**
	 * Method that ready char by char and determines a token type
	 * based on the read chars.
	 * 
	 */
	private void findNewToken() {
		skipWhites();
		
		if(currentIndex >= data.length) {
			currentToken = new QueryToken(QueryTokenType.EOF, null);
			currentIndex++;
		}
		else if(Character.isLetter(data[currentIndex])) {
			char currentChar = data[currentIndex];
			
			if(currentIndex == 0) {
				if(currentChar != 'q') {
					throw new IllegalStateException("There is no query keyword at the begining!");
				}
				else {
					for(int i = 0; i < "query".length(); i++) {
						currentIndex++;
					}
					skipWhites();
				}
			}
			
			composeWord();
		}
		else if(data[currentIndex] == '"') {
			composeStringLiteral();
		}
		else {
			currentToken = new QueryToken(QueryTokenType.SYMBOL, "" + data[currentIndex++]);
		}
	}
	
	
	/**
	 * Method that composes a word written  under quotation marks. Here
	 * the composed word can be anything, so the composition finishes when
	 * method finds the second quotation mark.
	 * @throws IllegalArgumentException if user inputed more wildcards in one string literal
	 */
	private void composeStringLiteral() {
		String composedLiteral = "";
		int numberOfWildCards = 0;
		currentIndex++;
		char currentChar;
		
		while(currentIndex < data.length) {
			currentChar = data[currentIndex];
			if(currentChar == '*') {
				numberOfWildCards++;
				if(numberOfWildCards > 1) {
					throw new IllegalArgumentException("String literal can contain just one wildcard!");
				}
			}
			if(currentChar == '"') {
				currentIndex++;
				break;
			}
			
			composedLiteral += currentChar;
			currentIndex++;
		}
		
		currentToken = new QueryToken(QueryTokenType.LITERAL, composedLiteral);
		
	}

	/**
	 * Method that composes a word that is not a string literal, so here 
	 * generated token can be of type KEY_WORD or RECORD_FIELD
	 * @throws QueryLexerException if the user uses regex symbol in a non string literal
	 * 		   or if there is unexpected word in a query
	 * @throws IllegalArgumentException if user didn't pay attention to case sensitive words
	 */
	private void composeWord() {
		char currentChar;
		String composedWord = "";
		
		while(currentIndex < data.length) {
			currentChar = data[currentIndex];
			if(Character.isLetter(currentChar)) {
				composedWord += currentChar;
				currentIndex++;
			}
			else if(currentChar == '*') {
				throw new QueryLexerException("You can't use regex symbol in a non string literal!");
			}
			else {
				break;
			}
		}
		if(composedWord.toUpperCase().equals("AND")) {
			currentToken = new QueryToken(QueryTokenType.KEY_WORD, composedWord);
		}
		else if(composedWord.toLowerCase().equals("like")) {
			if(!composedWord.equals("LIKE")) {
				throw new IllegalArgumentException("Operator LIKE must be written in upper case!");
			}
			else {
				currentToken = new QueryToken(QueryTokenType.KEY_WORD, composedWord);
			}
		}
		else if(composedWord.toLowerCase().equals("jmbag") ||
				composedWord.toLowerCase().equals("firstname") ||
				composedWord.toLowerCase().equals("lastname")){
			if(composedWord.equals("jmbag") || composedWord.equals("firstName") ||
			   composedWord.equals("lastName")) {
				currentToken = new QueryToken(QueryTokenType.RECORD_FIELD, composedWord);
			}
			else {
				throw new IllegalArgumentException("Attribute names are case senstive!");
			}
		}
		else {
			throw new QueryLexerException("Unexpected word in a query!");
		}
	}
	
	
	/**
	 * Method that skips all whites in query.
	 */
	private void skipWhites() {
		while(currentIndex < data.length) {
			char  currentChar = data[currentIndex];
			if(currentChar == ' ' || currentChar == '\t' || currentChar == '\n' ||
			   currentChar == '\r') {
				currentIndex++;
			}
			else {
				break;
			}
		}
	}

}
