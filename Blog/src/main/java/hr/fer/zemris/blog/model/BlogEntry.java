package hr.fer.zemris.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Class that represents on blog entry in table 'blog_entries'. One blog entry 
 * has it's primary key id. It also holds informations about when it was created,
 * when it was last modified, text and title. It also has 
 * list of blog comments which has bidirectional reference to 'blog_comments'
 * and has information about creator which is reference to 'blog_users'.
 * 
 * @author Marko
 *
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	private Long id;
	private List<BlogComment> comments = new ArrayList<>();
	private Date createdAt;
	private Date lastModifiedAt;
	private String title;
	private String text;
	private BlogUser creator;
	
	
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
	 * Getter for user that created this entry. Each entry can have 
	 * just one creator. This is foreign key to table 'blog_users'.
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	
	/**
	 * Getter for comments stored in this blog entry. 
	 * This is foregin key to table 'blog_comments'. One blog 
	 * entry can have more comments.
	 * 
	 * @return
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	
	/**
	 * Setter.
	 * 
	 * @param comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	
	/**
	 * Getter. Created at is column in table blog-entries and
	 * stores information about when the entry was made.
	 * 
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	
	/**
	 * Setter.
	 * 
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	/**
	 * Getter. last modified at is column in table blog-entries and
	 * stores information about when the entry was last modified.
	 * 
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	
	/**
	 * Setter.
	 * 
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	
	/**
	 * Getter for title of the blog entry. Title is 
	 * column in table 'blog_entries' and stores 
	 * title of the entry. It's length shouldn't be
	 * more than 200.
	 * 
	 * @return
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	
	/**
	 * Setter.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	/**
	 * Getter for text of the blog entry. Text is column
	 * in table 'blog_entries' and it's length can't be
	 * more than 4096.
	 * 
	 * @return
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	
	/**
	 * Setter.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	/**
	 * Two BlogEntry entitys are the same if they have equal id.
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
		BlogEntry other = (BlogEntry) obj;
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
