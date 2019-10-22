/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openreq.qt.qthulhu.rest.json;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author ttlaurin
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MillaResponse {

    private List<Project> projects;

    private List<Requirement> requirements;

    private List<Dependency> dependencies;

    private Map<Integer, List<String>> layers;

    private Integer numberOfOutpointingRelations;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Map<Integer, List<String>> getLayers() {
        return layers;
    }

    public void setLayers(Map<Integer, List<String>> layers) {
        this.layers = layers;
    }

    public Integer getNumberOfOutpointingRelations() {
        return numberOfOutpointingRelations;
    }

    public void setNumberOfOutpointingRelations(Integer numberOfOutpointingRelations) {
        this.numberOfOutpointingRelations = numberOfOutpointingRelations;
    }

}