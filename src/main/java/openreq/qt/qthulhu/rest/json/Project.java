package openreq.qt.qthulhu.rest.json;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * A Project to store requirements
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Project {

	/**
	 * The unique identifier of a project
	 * (Required)
	 *
	 */
	private String id;
	/**
	 * The name of the project
	 * (Required)
	 *
	 */
	private String name;
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
	/**
	 * The requirements specified in a project
	 *
	 */
	private List<String> specifiedRequirements = null;

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

	public List<String> getSpecifiedRequirements() {
		return specifiedRequirements;
	}

	public void setSpecifiedRequirements(List<String> specifiedRequirements) {
		this.specifiedRequirements = specifiedRequirements;
	}
}