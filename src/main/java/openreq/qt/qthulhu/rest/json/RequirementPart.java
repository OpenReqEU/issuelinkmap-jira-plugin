package openreq.qt.qthulhu.rest.json;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * RequirementPart
 * Container for necessary random stuff, in Qt Jira's case contains info on an issue's resolution
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RequirementPart {

	/**
	 * The unique identifier of a RequirementPart
	 * (Required)
	 *
	 */
	private String id;
	/**
	 * The name of the RequirementPart
	 *
	 */
	private String name;
	/**
	 * The textual description of a RequirementPart
	 *
	 */
	private String text;
	/**
	 * Creation timestamp
	 * (Required)
	 *
	 */
	private long created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
}