package hr.fer.zemris.java.hw03.prob1;


/**
 * Class that is generating tokens of text by analysing every character of it.
 * It has one constructor that accepts text which should be tokenized, and
 * provides methods for working with tokens.
 * 
 * @author Marko
 *
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	/**
	 * Constructor.
	 * 
	 * @param text {@code String}, text that should be tokenized.
	 * @throws NullPointerException if argument text is null
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Can't accept a null reference to text! ");
		}
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Setter of the working state of lexer.
	 * 
	 * @param state {@code LexerState} one of the enums
	 * @throws NullPointerException if lexer state is null
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Can't determine the 'null' state of lexer! ");
		}
		this.state = state;
	}
	
	/**
	 * Method that generates and returns the next token.
	 * 
	 * @return {@code Token} generated token.
	 * @throws LexerException if the user asks for more tokens while there isn't any
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("There is no more tokens! ");
		}
		else{
			findNewToken();
			return getToken();
		}
		
	}
	
	/**
	 * Method that returns last generated token.It can be called more times,
	 * it doesn't generate new token.	
	 * 
	 * @return {@code Token} last token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method that determines in which state lexer is and then calls appropriate
	 * methods.
	 * 
	 */
	private void findNewToken() {
		if(state == LexerState.BASIC) {
			basicFunction();
		}
		else {
			extendedFunction();
		}
		
		
		
	}
	
	/**
	 * Method that reads char by char in a string and finds new token and 
	 * works in extended state of lexer. This method can compute just words, it doesn't
	 * recognize symbols, other than '#', and numbers.
	 * 
	 */
	private void extendedFunction() {
		skipWhiteSpaces();
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return;
		}
		char currentChar = data[currentIndex];
		if(currentChar == '#') {
			token = new Token(TokenType.SYMBOL, currentChar);
			currentIndex++;
			setState(LexerState.BASIC);
		}
		else {
			int wordStart = currentIndex;
			while(currentIndex < data.length) {
				if(currentChar == '#') {
					currentIndex--;
					break;
				}
				currentChar = data[currentIndex];
				if(currentChar == ' ' || currentChar == '\t' ||
				   currentChar == '\r' || currentChar == '\n') {
					break;
				}
				currentIndex++;
			}
			int wordEnd = currentIndex;
			token = new Token(TokenType.WORD, new String(data, wordStart, wordEnd - wordStart));
		}
	}
	
	/**
	 * Method that reads char by char in a string and finds new token and 
	 * works in basic state of lexer.
	 * 
	 */
	private void basicFunction() {
		skipWhiteSpaces();
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
		}
		else if(Character.isLetter(data[currentIndex]) || 
				data[currentIndex] == '\\'){
			String word = composeWord();
			token = new Token(TokenType.WORD, word);
		}
		else if(("" + data[currentIndex]).matches("[+-]?[0-9]+")) {
			Long number = composeNumber();
			token = new Token(TokenType.NUMBER, number);
			currentIndex++;
		}
		else if(data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			setState(LexerState.EXTENDED);
		}
		else {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}

	}
	
	/**
	 * Method that composes a number and checks whether that composed number
	 * is too big for type {@code Long}.
	 * 
	 * @return {@code Long} parsed number
	 * @throws LexerException if the number is too big to be stored in a variable of type {@code Long}
	 */
	private Long composeNumber() {
		String numberToParse = "";
		char currentChar = data[currentIndex];
		while(currentIndex < data.length ) {
			if(!isLongParsable("" + currentChar)) {
				currentIndex--;
				break;
			}
			if(isLongParsable(numberToParse + currentChar)) {
				numberToParse += currentChar;
				if(currentIndex == data.length - 1) {
					break;
				}
				else {
					currentChar = data[++currentIndex];
				}
				
			}
			else {
				throw new LexerException("Number is too big for type Long");
			}
		}
		return Long.parseLong(numberToParse);
	}
	
	/**
	 * Method that composes a word with escapes, so for every char that is 
	 * letter it adds it to final word.
	 * 
	 * @return {@code String} composed word 
	 */
	private String composeWord() {
		StringBuilder string = new StringBuilder();
		char currentChar = data[currentIndex];
		int wordStart = currentIndex;
	
		while(currentIndex < data.length) {
			currentChar = data[currentIndex];
			if(currentChar == '\\') {
				string.append(new String(data, wordStart, currentIndex - wordStart));
				escapeCharacter(string);
				wordStart = currentIndex;
			}
			else if(!Character.isLetter(currentChar)) {
				break;
			}
			else {
				currentIndex++;
			}
		}
		int wordEnd = currentIndex;
		String word = new String(data, wordStart, wordEnd - wordStart);
		string.append(word);
		return string.toString();
	}
	
	
	/**
	 * Method that handles escape character '\' which is used to mark numbers as words.
	 * 
	 * @param string {@code StringBuilder}, used for building composed word
	 * @throws LexerException if user tried to escape something that is not allowed
	 */
	private void escapeCharacter(StringBuilder string) {
		if(currentIndex == data.length - 1) {
			throw new LexerException("The escape is not used properly");
		}
		else if(Character.isLetter(data[currentIndex + 1])) {
			throw new LexerException("The escape is not used properly");
		}
		else{
			currentIndex++;
			string.append(data[currentIndex]);
			currentIndex++;
		}
	}
	
	/**
	 * Method that skips all the white spaces.
	 * 
	 */
	private void skipWhiteSpaces() {
		while(currentIndex < data.length) {
			char currentChar = data[currentIndex];
			if(currentChar == ' ' || currentChar == '\t' ||
			   currentChar == '\r' || currentChar == '\n') {
				currentIndex++;
			}
			else {
				break;
			}
		}
	}
	
	/**
	 * Method that checks whether the provided string is number that 
	 * can be represented in type long.
	 * 
	 * @param token that should be checked
	 * @return {@code true} if it can be parsed
	 * 		   {@code false} if it can't be parsed
	 */
	private boolean isLongParsable(String token) {
		try {
			Long.parseLong(token);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
