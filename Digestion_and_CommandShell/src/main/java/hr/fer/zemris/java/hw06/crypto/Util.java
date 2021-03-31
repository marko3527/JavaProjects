package hr.fer.zemris.java.hw06.crypto;

import java.util.ArrayList;

/**
 * Class that offers implementation of two methods, converting bytes to hex coded string, 
 * and vice versa.
 * 
 * @author Marko
 *
 */
public class Util {
	
	
	/**
	 * Method that takes array of bytes and returns the hex coded string. This method 
	 * works with lowercase letters. 
	 * 
	 * @param byteArray {@code byte[]} array of bytes to be converted
	 * @return {@code String} hex coded string
	 */
	public static String byteToHex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for(byte bajt : byteArray) {

			int firstHalf = bajt & 0x0F;
			int secondHalf = bajt & 0xF0;
			sb.append(convertToHex(secondHalf));
			sb.append(convertToHex(firstHalf));
		}
		return sb.toString();
		
	}
	
	
	/**
	 * Method that takes hex coded string and returns the array of bytes. This method
	 * works both with lowercase and uppercase letters.
	 * 
	 * @param keyText {@code String} text to be converted to bytes
	 * @return {@code byte[]} array of bytes 
	 */
	public static byte[] hexToByte(String keyText) {
		int[] arrayOfDecText = convertToDecimal(keyText);
		
		byte[] arrayOfBytes = new byte[arrayOfDecText.length];
		for(int i = 0; i < arrayOfDecText.length; i++) {
			arrayOfBytes[i] = (byte)(arrayOfDecText[i]);
		}
		return arrayOfBytes;
	}
	
	
	/**
	 * Method takes the text represnted in hex format. 
	 * Method takes two chars at a time, representing one byte 
	 * and then calculating the decimal number of that two chars.
	 * 
	 * @param text {@code String} number represented in hex format
	 * @return {@code int[]} array of decimal numbers
	 */
	private static int[] convertToDecimal(String text) {
		int[] arrayOfDecNumbers = new int[text.length()/2];
		for(int i = 0; i < text.length(); i += 2) {
			arrayOfDecNumbers[i/2] = hexIndex(text.charAt(i+1)) * 1 +
								   hexIndex(text.charAt(i)) * 16;
		}
		return arrayOfDecNumbers;
	}
	
	
	/**
	 * Method that finds the position, in the array of hex 
	 * digits, that corresponds to given digit in the argument. 
	 * 
	 * @param hexDigit {@code char} digit in hex code to be found the position of in array of digits
	 * @return {@code int} position of given digit in array
	 */
	private static int hexIndex(char hexDigit) {
		ArrayList<String> hexChars = new ArrayList<>();
		char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				  'a', 'b', 'c', 'd', 'e', 'f'};
		for(char hexChar : hex) {
			hexChars.add("" + hexChar);
		}
		return hexChars.indexOf(("" + hexDigit).toLowerCase());
	}
	
	
	/**
	 * Method that takes decimal number and converts it into
	 * a number in hex format. If the number is zero then it just 
	 * returns 0, and else it returns a number without the zero 
	 * in front of it.
	 * 
	 * @param number {@code int} in decimal format to convert 
	 * @return {@code String} number ih hex format
	 */
	private static String convertToHex(int number) {
		char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					  'a', 'b', 'c', 'd', 'e', 'f'};
		String hexOutput = "";
		if(number == 0) {
			return hexOutput + number;
		}
		while(number > 0) {
			int hexRemainder = number % 16;
			hexOutput += hex[hexRemainder];
			number = number / 16;
		}
		
		return hexOutput.replaceFirst("0","");
	}

}
