package openreq.qt.qthulhu.rest.json;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
* Dependency
* The dependency between requirements
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Dependency {

        /**
	* The unique identifier of a dependency
	* 
	*/
	private String id;

	/**
	* The type of dependency between requirements
	* (Required)
	* 
	*/
	private String dependency_type;
	/**
	* NLP engine's estimation of the reliability of a proposed dependency
	* 
	*/
	private double dependency_score;
	/**
	* Status of dependency that has been detected by NLP and whether or not the dependency has been approved
	* 
	*/
	private String status;
	/**
	* The requirement having a dependency
	* (Required)
	* 
	*/
	private String fromid;
	/**
	* The requirement dependent on another
	* (Required)
	* 
	*/
	private String toid;
	
	private List<String> description;
	
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
	
	public String getDependency_type() {
		return dependency_type;
	}
	
	public void setDependency_type(String dependency_type) {
		this.dependency_type = dependency_type;
	}
	
	public double getDependency_score() {
		return dependency_score;
	}
	
	public void setDependency_score(double dependency_score) {
		this.dependency_score = dependency_score;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFromid() {
		return fromid;
	}
	
	public void setFromid(String fromId) {
		this.fromid = fromId;
	}
	
	public String getToid() {
		return toid;
	}
	
	public void setToid(String toId) {
		this.toid = toId;
	}
	
	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public long getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
}