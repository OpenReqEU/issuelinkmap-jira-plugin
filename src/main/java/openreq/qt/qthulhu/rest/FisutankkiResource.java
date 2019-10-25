package openreq.qt.qthulhu.rest;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import openreq.qt.qthulhu.rest.json.Dependency;

import openreq.qt.qthulhu.rest.json.Requirement;
import openreq.qt.qthulhu.rest.json.MillaResponse;
import openreq.qt.qthulhu.service.JiraService;
import openreq.qt.qthulhu.service.MillaService;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import openreq.qt.qthulhu.data.NodeEdgeSetBuilder;

@Path("")
public class FisutankkiResource
{

    @Inject
    private MillaService millaService;

    @Inject
    private JiraService jiraService;


    @GET
    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTransitiveClosureOfRequirement")
    public Response getTransitiveClosure(@QueryParam("requirementId") List<String> requirementId, @QueryParam("layerCount")
            Integer layerCount) throws JqlParseException, SearchException, IOException, JSONException
    {

        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();

        if (layerCount == null)
        {
            layerCount = 5;
        }

        String reqIdsString = "";
        String issue = "";

        for (String id : requirementId)
        {
            reqIdsString = reqIdsString + "&requirementId=" + id;
            //usually the list contains only one issue
            issue = id;
        }

        String urlTail = "/getTransitiveClosureOfRequirement?layerCount=" + layerCount + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);
        
        if (response == null)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error connecting to Milla\"}").build();
        }

        MillaResponse closure;

        try
        {
            // Parse the response JSON string to MillaResponse
            closure = mapper.readValue(response, MillaResponse.class);
        }
        catch (IOException e)
        {
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }
        List<Requirement> filtered = jiraService.filterRequirements(closure.getRequirements(), ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
        closure.setRequirements(filtered);

        JsonObject responseJSON = gson.toJsonTree(closure).getAsJsonObject();    

        JsonObject nodeEdgeSet = NodeEdgeSetBuilder.buildNodeEdgeSet(responseJSON, issue, false);
        String nodeEdgeString = nodeEdgeSet.toString();

        return Response.ok(nodeEdgeString).build();
    }

    @GET
    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTopProposedDependenciesOfRequirement")
    public Response topProposed(@QueryParam("requirementId") List<String> requirementId, @QueryParam("maxResults")
            Integer maxResults) throws IOException, JqlParseException, SearchException, JSONException
    {

        Gson gson = new Gson();
        String reqIdsString = "";
        String issue = "";

        if (maxResults == null)
        {
            maxResults = 0;
        }

        for (String id : requirementId)
        {
            reqIdsString = reqIdsString + "&requirementId=" + id;
            //usually the list contains only one issue
            issue = id;
        }

        String urlTail = "/getTopProposedDependenciesOfRequirement?maxResults=" + maxResults + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);

        if (response == null)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error connecting to Milla\"}").build();
        }

        MillaResponse proposedDependencies;

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            proposedDependencies = mapper.readValue(response, MillaResponse.class);
        }
        catch (IOException e)
        {
            JSONObject error = new JSONObject();
            error.put("error", response);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }

        List<Requirement> filtered = jiraService.filterRequirements(proposedDependencies.getRequirements(), ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
        proposedDependencies.setRequirements(filtered);

        JsonObject responseJSON = gson.toJsonTree(proposedDependencies).getAsJsonObject();

        JsonObject proposedNodeEdgeSet = NodeEdgeSetBuilder.buildNodeEdgeSet(responseJSON, issue, true);
        String proposedNodeEdgeString = proposedNodeEdgeSet.toString();

        return Response.ok(proposedNodeEdgeString).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/updateProposedDependencies")
    public Response sendUpdatedDependencies(List<Dependency> dependencies) throws JqlParseException, SearchException, CreateException, IOException
    {

        String urlTail = "/updateProposedDependencies";

        ObjectMapper mapper = new ObjectMapper();
        String dependencyJson = mapper.writeValueAsString(dependencies);

        // Forward the call to OpenReq services in localhost
        String response = "\n\nMilla response:\n\n" + millaService.getResponseFromMilla(urlTail, dependencyJson, true);

        return Response.ok(response).build();
    }

    @GET
    @AnonymousAllowed
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getConsistencyCheckForRequirement")
    public Response consistencyCheck(@QueryParam("requirementId") List<String> requirementId, @QueryParam("layerCount")
            Integer layerCount, @QueryParam("timeOut") Integer timeOut, @QueryParam("omitCrossProject") Boolean omitCrossProject, @QueryParam("analysisOnly") Boolean analysisOnly) throws IOException
    {

        if (layerCount == null)
        {
            layerCount = 5;
        }
        if (timeOut == null)
        {
            timeOut = 0;
        }
        //currently crossProject does not work
        if (omitCrossProject == null)
        {
            omitCrossProject = true;
        }
        if (analysisOnly == null)
        {
            analysisOnly = true;
        }

        String reqIdsString = "";

        for (String id : requirementId)
        {
            reqIdsString = reqIdsString + "&requirementId=" + id;
        }

        String urlTail = "/getConsistencyCheckForRequirement?layerCount=" + layerCount + "&analysisOnly=" + analysisOnly + "&omitCrossProject=" + omitCrossProject + "&timeOut=" + timeOut + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);

        return checkNull(response);
    }

    private Response checkNull(String responseString)
    {
        if (responseString == null)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to Milla").build();
        }
        return Response.ok(responseString).build();
    }
}