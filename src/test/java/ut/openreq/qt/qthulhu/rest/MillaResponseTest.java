package ut.openreq.qt.qthulhu.rest;

import openreq.qt.qthulhu.rest.json.Dependency;
import openreq.qt.qthulhu.rest.json.MillaResponse;
import openreq.qt.qthulhu.rest.json.Project;
import openreq.qt.qthulhu.rest.json.Requirement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MillaResponseTest
{
    @Test
    public void testProjects()
    {
        MillaResponse millaResponse = new MillaResponse();
        Project testProject = new Project();
        assertEquals(millaResponse.getProjects(), null);
        List<Project> projectList = new ArrayList<>();
        millaResponse.setProjects(projectList);
        assertEquals(millaResponse.getProjects(), projectList);
        projectList.add(testProject);
        millaResponse.setProjects(projectList);
        assertEquals(millaResponse.getProjects(), projectList);
    }

    @Test
    public void testRequirements()
    {
        MillaResponse millaResponse = new MillaResponse();
        Requirement testRequirement = new Requirement();
        assertEquals(millaResponse.getRequirements(), null);
        List<Requirement> RequirementList = new ArrayList<>();
        millaResponse.setRequirements(RequirementList);
        assertEquals(millaResponse.getRequirements(), RequirementList);
        RequirementList.add(testRequirement);
        millaResponse.setRequirements(RequirementList);
        assertEquals(millaResponse.getRequirements(), RequirementList);
    }

    @Test
    public void testDependencies()
    {
        MillaResponse millaResponse = new MillaResponse();
        Dependency testDependency = new Dependency();
        assertEquals(millaResponse.getDependencies(), null);
        List<Dependency> dependencyList = new ArrayList<>();
        millaResponse.setDependencies(dependencyList);
        assertEquals(millaResponse.getDependencies(), dependencyList);
        dependencyList.add(testDependency);
        millaResponse.setDependencies(dependencyList);
        assertEquals(millaResponse.getDependencies(), dependencyList);
    }

    @Test
    public void testLayers()
    {
        MillaResponse millaResponse = new MillaResponse();
        assertEquals(millaResponse.getLayers(), null);
        List<String> layer = new ArrayList<>();
        layer.add("testLayer");
        Map testLayer = new HashMap<Integer, List<String>>();
        millaResponse.setLayers(testLayer);
        assertEquals(millaResponse.getLayers(), testLayer);
        testLayer.put(1, layer);
        millaResponse.setLayers(testLayer);
        assertEquals(millaResponse.getLayers(), testLayer);
    }

    @Test
    public void testNumberOfOutpointingRelations()
    {
        MillaResponse millaResponse = new MillaResponse();
        assertEquals(millaResponse.getNumberOfOutpointingRelations(), 0);
        int numOfRelations = 3;
        millaResponse.setNumberOfOutpointingRelations(numOfRelations);
        assertEquals(millaResponse.getNumberOfOutpointingRelations(), 3);
    }
}
