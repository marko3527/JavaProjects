package hr.fer.zemris.blog.servlets;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.blog.model.BlogUser;
import hr.fer.zemris.blog.util.PasswordEncoder;


/**
 * BlogUserForm is class that has all attributes as Entity BlogUser but
 * all attributes are String. It knows how each attribute should look like and
 * can detect mistakes.
 * 
 * @author Marko
 *
 */
public class BlogUserForm {
	
	
	private String id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;
	
	private Map<String, String> mistakes = new HashMap<>();
	
	
	/**
	 * Getter. Returns all mistakes detected during filling
	 * the form.
	 * 
	 * @param name
	 * @return
	 */
	public String getMistake(String name) {
		return mistakes.get(name);
	}
	
	
	/**
	 * Method provides information if there are any mistakes.
	 * 
	 * @return {@code true} if there are mistakes and {@code false} if the map mistakes are empty
	 */
	public boolean hasMistakes() {
		return !mistakes.isEmpty();
	}
	
	
	/**
	 * Getter. Returns the id of blog user.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Setter. Sets the id of the user.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * Getter. Returns first name of the user.
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * Setter. Sets first name of the user.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	/**
	 * Getter. Returns last name of the user.
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	
	/**
	 * Setter. Sets the last name of the user.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	/**
	 * Getter. Returns nickname of the user.
	 * 
	 * @return
	 */
	public String getNick() {
		return nick;
	}
	
	
	/**
	 * Setter. Sets the nickname of the user.
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
	/**
	 * Getter. Returns email of the user.
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * Setter. Sets the email of the user.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * Method that fills the form from HTTPServletRequest
	 * by getting parameters stored in http request body.
	 * 
	 * @param req {@code HttpServletRequest}
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		this.firstName = isNull(req.getParameter("firstName"));
		this.lastName = isNull(req.getParameter("lastName"));
		this.nick = isNull(req.getParameter("nick"));
		this.email = isNull(req.getParameter("email"));
		try {
			this.passwordHash = isNull(PasswordEncoder.encodePassword(req.getParameter("password")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			
		}
	}
	
	
	/**
	 * Getter. Returns password hash of the user.
	 * 
	 * @return
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter. Sets password hash of the user.
	 * 
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	
	/**
	 * Method that fills instance of BlogUser from 
	 * forms attributes. 
	 * 
	 * @param user {@code BlogUser}
	 */
	public void fillUser(BlogUser user) {
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setPasswordHash(passwordHash);
	}
	
	
	/**
	 * Method that validates user form. Last name, first name, password, email and nick
	 * are mandatory. It checks if the email provided meets the requirements of the
	 * email address.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void validate() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		mistakes.clear();
		
		if(this.firstName.isEmpty()) {
			mistakes.put("firstName", "First name is mandatory!");
		}
		
		if(this.lastName.isEmpty()) {
			mistakes.put("lastName", "Last name is mandatory!");
		}
		
		if(this.email.isEmpty()) {
			mistakes.put("email", "EMail is mandatory!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				mistakes.put("email", "EMail is not valid.");
			}
		}
		
		if(this.nick.isEmpty()) {
			mistakes.put("nick", "Nickname is mandatory!");
		}
		
		String emptyHash = PasswordEncoder.encodePassword("");
		System.out.println("Hash of empty string");
		if(this.passwordHash.equals(emptyHash)) {
			mistakes.put("password", "Password is mandatory!");
		}
		
	}
	
	
	/**
	 * Method that checks if the string in argument is null
	 * and it replaces it with empty string.
	 * 
	 * @param s
	 * @return
	 */
	private String isNull(String s) {
		if(s==null) return "";
		return s.trim();
	}
	

}
