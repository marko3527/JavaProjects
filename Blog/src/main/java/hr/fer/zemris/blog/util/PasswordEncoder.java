package hr.fer.zemris.blog.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * Util class that has one method for encoding a String password
 * with SHA-1 digest and returns it's digested bytes as hex 
 * encoded string.
 * 
 * @author Marko
 *
 */
public class PasswordEncoder {
	
	
	/**
	 * Method that takes String password and performs digestion with SHA-1
	 * algorithm and returns hex encoded string.
	 * 
	 * @param password {@code String}
	 * @return {@code String} hex encoded string
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encodePassword(String password) throws NoSuchAlgorithmException,
													   UnsupportedEncodingException {
		
		MessageDigest passwordDigest = MessageDigest.getInstance("SHA-1");
		
		passwordDigest.update(password.getBytes("UTF-8"));
		return DatatypeConverter.printHexBinary(passwordDigest.digest());
	}
}
