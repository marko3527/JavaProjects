package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that is implementing main method for running the program for encryption 
 * and decryption of files and checking the SHA digest.
 * 
 * @author Marko
 *
 */
public class Crypto {
	

	/**
	 * Main method from which the program starts running. By running a program 
	 * user must input, what does he want from program to do, as command line arguments.
	 * There are 3 cases of arguments. First user wants to check the SHA digest, then he needs 
	 * to provide file for digestion and then the program will ask it for the digest to compare to.
	 * Second case and third case, user wants a encryption/decryption of a file, then he needs to 
	 * provide one path to file to encrypt/decrypt and one path to newly generated
	 * encrypted/decrpyted file.
	 * 
	 * @param args command line arguments
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		
		if(args.length > 3 || args.length <= 1) {
			System.out.println("Command line arguments number is not valid. There can "
					+ "be 2 arguments \nwhen digest SHA, or 3 arguments when encrypting/decrypting!");
			System.exit(1);
		}
		else {
			String operation = args[0];
			Scanner sc = new Scanner(System.in);
			
			switch(operation.toLowerCase()) {
				case "checksha":
					args[1] = "src\\main\\resources\\" + args[1];
					performDigest(args[1], sc);
					break;
				case "encrypt":
					args[1] = "src\\main\\resources\\" + args[1];
					args[2] = "src\\main\\resources\\" + args[2];
					performCiphering(args[1], args[2], sc, true);
					break;
				case "decrypt":
					args[1] = "src\\main\\resources\\" + args[1];
					args[2] = "src\\main\\resources\\" + args[2];
					performCiphering(args[1], args[2], sc, false);
					break;
			}
			sc.close();
		}
		
	}
		
	/**
	 * Method that takes the path to file and calculates its digested value and 
	 * then compares with the user input value.
	 * 
	 * @param pathToDigest {@code String} path to file that has to be digested
	 * @param sc {@code Scanner} 
	 * @throws NoSuchAlgorithmException
	 */
	private static void performDigest(String pathToDigest, Scanner sc) throws NoSuchAlgorithmException {
		MessageDigest fileDigest = MessageDigest.getInstance("SHA-256");
		
		System.out.print("Please provide expected sha-256 digest for " + pathToDigest);
		System.out.print("\n>");
		String expectedShaKey = sc.nextLine().trim();
		Path path = Paths.get(pathToDigest);
		
		try(InputStream is = Files.newInputStream(path)) {
			byte[] buff = new byte[1024];
			int bufferLength = buff.length;
			while(true) {
				buff = is.readNBytes(bufferLength);
				if(buff.length == 0) {
					break;
				}
				fileDigest.update(buff);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		byte[] calculatedDigestion = fileDigest.digest();
		String shaKey = Util.byteToHex(calculatedDigestion);
		
		pathToDigest = pathToDigest.replace("src\\main\\resources\\", "");
		System.out.print("Digestion completed. Digest of " + pathToDigest);
		if(expectedShaKey.equals(shaKey)) {
			System.out.print(" matches expected digest.");
		}
		else {
			System.out.print(" does not match expected digest. \nDigest was: " + shaKey);
		}
		
	
	}
	
	
	/**
	 * Method that takes 2 paths, one to file that has to be encrypted/decrypted and
	 * second path is the path for newly generated encrypted/decrypted file, or the original file.
	 * 
	 * @param pathToDecrypt {@code String} path to file to encrypt/decrypt
	 * @param decryptedPath {@code String} path to newly generated encrypt/decrypted file
	 * @param sc {@code Scanner}
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	private static void performCiphering(String pathToCipher, String cipheredPath, Scanner sc,
										 boolean encrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

		String[] passAndVector = readPassAndVector(sc);
		String password = passAndVector[0];
		String initVector = passAndVector[1];
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(initVector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		try(InputStream is = Files.newInputStream(Paths.get(pathToCipher));  
			OutputStream os = Files.newOutputStream(Paths.get(cipheredPath))){
			byte[] buff = new byte[1024];
			int length = buff.length;
			while(true) {
				buff = is.readNBytes(length);
				if(buff.length == 0) {
					break;
				}
				byte[] cipheredBytes = cipher.update(buff);
				os.write(cipheredBytes);
			}
			cipher.doFinal();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		cipheredPath = cipheredPath.replace("src\\main\\resources\\", "");
		pathToCipher = pathToCipher.replace("src\\main\\resources\\", "");
		System.out.print((encrypt ? "Encryption completed." : "Decrpytion completed."));
		System.out.print("Generated file " + cipheredPath + " based"
				+ " on file " + pathToCipher + ".");
	}
	
	
	/**
	 * Method that asks user to input the password and initialization vector
	 * that is being used in encrypting/decrypting the file
	 * 
	 * @param sc {@code Scanner}
	 * @return array of strings where the first string is user provided password  and
	 * second string is user provided initialization vector
	 */
	private static String[] readPassAndVector(Scanner sc) {
		System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("\n>");
		String password = sc.nextLine().trim();
		
		System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("\n>");
		String initializationVector = sc.nextLine().trim();
		return new String[] {password, initializationVector};
	}

}
