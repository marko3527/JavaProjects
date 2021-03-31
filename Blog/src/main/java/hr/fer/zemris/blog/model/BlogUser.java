package hr.fer.zemris.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


/**
 * Class that represents one blog user who will be stored into table 'blog_users'.
 * Every blog_user has id which is primary key and has generated value. It also
 * has informations about first name, last name, nickname of the user, its email,
 * list of blog entries that user has made and its passwordHash.
 * 
 * @author Marko
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser implements Comparable<BlogUser>{
	
	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private List<BlogEntry> blogEntries = new ArrayList<>();
	private String passwordHash;
	
	
	/**
	 * Getter. Returns id of the user which was generated.
	 * This id should not be changed.
	 * 
	 * @return
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Getter for blog entries which user made. One user 
	 * can have more entries and this is also foreign key to 
	 * the table 'blog_entries'.
	 * 
	 * @return
	 */
	@OneToMany(mappedBy="creator")
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param blogEntries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	
	/**
	 * Getter for users email, email is column in table and
	 * should not have length longer than 200.
	 * 
	 * @return
	 */
	@Column(length = 200, nullable = false)
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * Setter for users email.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * Getter for users firstName. Users first name
	 * is column in table and it's length is 50.
	 * 
	 * @return
	 */
	@Column(length = 50, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	/**
	 * Getter for users last name. Users last name 
	 * is column in table and shouldn't be longer than 50.
	 * 
	 * @return
	 */
	@Column(length = 50, nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	/**
	 * Getter for users nickname. Users nickname is 
	 * column in table and shouldn't have length more
	 * than 30. Two users can't have same nickname
	 * 
	 * @return
	 */
	@Column(length = 30, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
	/**
	 * Getter for password hash. Hash is column in
	 * table and has length of 40.
	 * 
	 * @return
	 */
	@Column(length = 40, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	
	/**
	 * {@inheritDoc}
	 * Two users are the same if they have same nickname.
	 * 
	 */
	@Override
	public int compareTo(BlogUser o) {
		if(this.nick==null) {
			if(o.nick==null) {
				return 0;
			}
			return -1;
		} else if(o.nick==null) {
			return 1;
		}
		return this.nick.compareTo(o.nick);
	}
	

}
