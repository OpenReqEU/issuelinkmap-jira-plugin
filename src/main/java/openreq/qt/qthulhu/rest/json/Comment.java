package openreq.qt.qthulhu.rest.json;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
* Comment
* The comment that will be referenced from one entity
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Comment {

	/**
	* The unique identifier of a comment
	* (Required)
	* 
	*/
	private String id;
	/**
	* The textual description of the comment
	* (Required)
	* 
	*/
	private String text;
	/**
	* Creation timestamp
	* (Required)
	* 
	*/
	private long created_at;
	/**
	* Last modification time
	* 
	*/
	private long modified_at;
	
	/*
	 * Person who did the comment
	 */
	private Person commentDoneBy;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Person getCommentDoneBy() {
		return commentDoneBy;
	}

	public void setCommentDoneBy(Person commentDoneBy) {
		this.commentDoneBy = commentDoneBy;
	}

	public long getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	
	public long getModified_at() {
		return modified_at;
	}
	
	public void setModified_at(long modified_at) {
		this.modified_at = modified_at;
	}
}