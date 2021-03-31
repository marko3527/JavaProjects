package hr.fer.zemris.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Class that reperesents one entity of blog comment stored in table
 * 'blog_comments'. Every blog comment has generated value (id) which 
 * is primary key. Then it has reference to {@code BlogEntry} for which this comment
 * is made. One blog entry can have many blog comments. It also 
 * stores information about usersEmail, Date that it has been posted on
 * and message of the comment.
 * 
 * @author Marko
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	private Long id;
	private BlogEntry blogEntry;
	private String usersEMail;
	private String message;
	private Date postedOn;
	
	
	/**
	 * Getter. Id is generated value, should not be changed.
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
	 * Getter. Returns the blog entry for which comment has been made.
	 * One blog can have more comments so this is bidirectional connection
	 * to table 'blog_entries' (FOREIGN KEY)
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	
	/**
	 * Setter. Sets the blog Entry for this comment.
	 * 
	 * @param blogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	
	/**
	 * Getter. Users mail is column in table 'blog_comments', it's length 
	 * shouldn't be longer than 100
	 * 
	 * @return
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	
	/**
	 * Setter for users mail.
	 * 
	 * @param usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	
	/** 
	 * Getter for message of the comment. It is column 
	 * in table and shouldn't be longer than 4096
	 * 
	 * @return
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	
	/**
	 * Setter for comment message.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * Getter. posted on is column in table blog-comments and
	 * stores information about when the comment was made.
	 * 
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	
	/**
	 * Setter for postedOn attribute.
	 * 
	 * @param postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	/**
	 * Comparison of blog comments needed to be altered to compare their
	 * id's.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
