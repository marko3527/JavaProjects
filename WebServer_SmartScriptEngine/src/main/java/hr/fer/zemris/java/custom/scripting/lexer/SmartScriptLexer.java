package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Class that is generating tokens of text by analysing every character of it.
 * It has one constructor that accepts text which should be tokenized, and
 * provides methods for working with tokens.
 * 
 * @author Marko
 *
 */
public class SmartScriptLexer {

	
	private char[] data;
	private SmartToken token;
	private int currentIndex;
	
	/**
	 * flag used to know if we should include blanks into word or not. 
	 * If the lexer is in tag we won't be including blanks
	 */
	private boolean underTag = false;
	
	
	/**
	 * Constructor.
	 * 
	 * @param text {@code String}, text that should be tokenized.
	 * @throws NullPointerException if argument text is null
	 */
	public SmartScriptLexer(String text) {
		if(text == null) {
			throw new NullPointerException("Can't accept a null reference to text! ");
		}
		this.data = text.toCharArray();
	}
	
	/**
	 * Method that generates and returns the next token.
	 * 
	 * @return {@code SmartToken} generated token.
	 * @throws LexerException if user asks for tokens and there is no more
	 */
	public SmartToken nextToken() {
		if(token != null && token.getType() == SmartTokenType.EOF) {
			throw new LexerException("There is no more tokens! ");
		}
		findNewToken();
		return getToken();
	}
	
	
	/**
	 * Method that reads char by char in a string and finds new token.
	 * 
	 */
	private void findNewToken() {
		
		if(currentIndex >= data.length) {
			token = new SmartToken(SmartTokenType.EOF, null);
			currentIndex++;
			return;
		}
		if(!underTag && data[currentIndex] == ' ') {
			String word = composeOutOfQoutation();
			token = new SmartToken(SmartTokenType.WORD, word);
			return;
		}
		else if(underTag && data[currentIndex] == ' '){
			skipBlanks();
		}
		
		//skipBlanks();
		
		if(("" + data[currentIndex]).matches("[\\t\\r\\n]")) {
			token = new SmartToken(SmartTokenType.WHITESPACE, returnWhites());
		}
		else if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			String value = "" + data[currentIndex++] + data[currentIndex++]; 
			token = new SmartToken(SmartTokenType.TAG_START,value);
			underTag = true;
		}
		else if(data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			String value = "" + data[currentIndex++] + data[currentIndex++]; 
			token = new SmartToken(SmartTokenType.TAG_END,value);
			underTag = false;
		}
		else if(token != null && ((
			    token.getType() == SmartTokenType.WORD && token.getValue().toString().equals("FOR")) ||
			    token.getType() == SmartTokenType.SYMBOL && token.getValue().toString().equals("@"))){
			String mixedCharacterWord = composeVariable();
			token = new SmartToken(SmartTokenType.WORD, mixedCharacterWord.trim());
		}
		else if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String word = composeOutOfQoutation();
			token = new SmartToken(SmartTokenType.WORD, word);
		}
		else if(currentIndex < data.length - 1 &&
			   ("" + data[currentIndex] + data[currentIndex + 1]).matches("[+-]?[0-9]+\\.?") ||
			   ("" + data[currentIndex]).matches("[+-]?[0-9]+\\.?[0-9]*")) {
			double number = composeNumber();
			token = new SmartToken(SmartTokenType.NUMBER, number);
		}
		else if(data[currentIndex] == '"') {
			String word = "" + data[currentIndex++];
			word += composeWordUnderQuotationMarks();
			token = new SmartToken(SmartTokenType.STRING_WORD, word);
		}
		else {
			token = new SmartToken(SmartTokenType.SYMBOL, data[currentIndex++]);
		}
		
	}
	
	/**
	 * Method that composes a word that could have characters, numbers and underscore in itself.
	 * Those kind of words will be variables or functions. 
	 * 
	 * @return {@code String} composed word
	 * @throws SmartLexerException if the word doesn't obey the given rules of defining variables and functions
	 */
	private String composeVariable() {
		String word = ""  + data[currentIndex];
		if(word.equals("_") || word.matches("[0-9]")) {
			throw new SmartLexerException("Name of the function or variable must start with letter!");
		}
		while(word.matches("\\w+_?[0-9]*\\w*") || data[currentIndex] == '$') {
			currentIndex++;
			word += data[currentIndex];
		}
		return word += data[currentIndex];
	}
	
	/**
	 * Method that composes word under quotation mark.
	 * It accepts escaping quotation and backslash characters.
	 * 
	 * @return {@code String} composed word
	 */
	public String composeWordUnderQuotationMarks() {
		return composeWord(true);
	}
	
	
	/**
	 * Method that calls method that composes a word without quotations.
	 * It accepts escaping curly brackets and backslash characters.
	 * 
	 * @return {@code String} composed word 
	 */
	private String composeOutOfQoutation() {
		return composeWord(false);
	}
	
	/**
	 * Method that composes a word.Word can consist of only letters and underscore.
	 * 
	 * @param underQuotation {@code boolean} flag that tells whether the word is under quotations
	 * @return {@code String} composed word
	 */
	private String composeWord(boolean underQuotation) {
		StringBuilder string = new StringBuilder();
		char currentChar;
		int wordStart = currentIndex;
	
		while(currentIndex < data.length) {
			currentChar = data[currentIndex];
			if(currentChar == '\\') {
				string.append(new String(data, wordStart, currentIndex - wordStart));
				escapeCharacter(string,underQuotation);
				wordStart = currentIndex;
				continue;
			}
			else if(currentChar == '_') {
				currentIndex++;
			}
			else if(underQuotation) {
				if(currentChar == '"') {
					currentIndex++;
					break;
				}
			}
			else if((currentChar == ' ' || Character.isDigit(currentChar) ||
					currentChar != '{') && 
					!underTag) {
				currentIndex++;
				continue;
			}
			else if(!Character.isLetter(currentChar)){
				break;
			}
			currentIndex++;
		}
		int wordEnd = currentIndex;
		String word = new String(data, wordStart, wordEnd - wordStart);
		string.append(word);
		String result = string.toString().replace("\\r\\n", "\r\n");
		return result;
		
	}
	
	
	/**
	 * Method that handles escape characters depending on the situation where it is.
	 * 
	 * @param string {@code StringBuilder},  used for building composed word
	 * @param underQuotation {@code boolean}, used to determine whether escape is under quotations or not
	 * @throws SmartLexerException if escaped character can't be escaped in given situation
	 */
	private void escapeCharacter(StringBuilder string, boolean underQuotation) {
		if(currentIndex == data.length - 1) {
			throw new SmartLexerException("The escape is not used properly");
		}
		if(!underQuotation) {
			if(data[currentIndex] != '{' && data[currentIndex] != '\\') {
				throw new SmartLexerException("You can't escape nothing else beside '{' and '\\' in a text! ");
			}
		}
		else {
			if(data[currentIndex] != '"' && data[currentIndex] != '\\') {
				throw new SmartLexerException("You can't escape nothing else beside \" and '\\' in a string text! ");
			}
		}
		if(data[currentIndex] == '"') {
			string.append("\\" + data[currentIndex]);
		}
		else {
			string.append(data[currentIndex]);
		}
		currentIndex++;
	}

	
	/**
	 * Method that composes a number and checks whether that composed number
	 * is too big for type {@code double}.
	 * 
	 * @return {@code double} parsed number
	 * @throws SmartLexerException if number is too big to be stored in a variable of type double
	 */
	public double composeNumber() {
		String numberToParse = "";
		double number;
		char currentChar = data[currentIndex++];
		numberToParse += currentChar;
		String nextStep = numberToParse + data[currentIndex++];
		while(nextStep.matches("[+-]?[0-9]+\\.?[0-9]*") || nextStep.matches("[+-]?")) {
			numberToParse = nextStep;
			if(currentIndex >= data.length) {
				break;
			}
			nextStep = numberToParse + data[currentIndex++];
		}
		currentIndex--;
		try {
			number = Double.parseDouble(numberToParse);
		}catch (NumberFormatException e) {
			throw new SmartLexerException("Number is too big! ");
		}
		return number;
		
	}
	
	
	/**
	 * Method that returns last generated token.It can be called more times,
	 * it doesn't generate new token.	
	 * 
	 * @return {@code SmartToken} last token.
	 */
	public SmartToken getToken() {
		return token;
	}
	
	
	/**
	 * Method that takes all white spaces in a row.
	 * @return {@code String} all white spaces between words
	 */
	private String returnWhites() {
		String whiteSpaces = "";
		while(currentIndex < data.length) {
			char currentChar = data[currentIndex];
			if(currentChar == '\t' || currentChar == '\r' || currentChar == '\n') {
				whiteSpaces += currentChar;
				currentIndex++;
			}
			else {
				break;
			}
		}
		return whiteSpaces;
	}
	
	/**
	 * Skips all the blanks.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char currentChar = data[currentIndex];
			if(currentChar == ' ') {
				currentIndex++;
			}
			else {
				break;
			}
		}
	}
	
}
