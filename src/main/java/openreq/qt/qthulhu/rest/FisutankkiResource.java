package openreq.qt.qthulhu.rest;

import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import openreq.qt.qthulhu.rest.json.Dependency;
import openreq.qt.qthulhu.rest.json.MillaResponse;
import openreq.qt.qthulhu.service.MillaService;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;

import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import openreq.qt.qthulhu.data.NodeEdgeSetBuilder;

@Path("")
public class FisutankkiResource {

    @Inject
    private MillaService millaService;

    @GET
    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTransitiveClosureOfRequirement")
    public Response getTransitiveClosure(@QueryParam("requirementId") List<String> requirementId, @QueryParam("layerCount")
            Integer layerCount) throws JqlParseException, SearchException, IOException, JSONException {

        if (layerCount == null) {
            layerCount = 5;
        }

        String reqIdsString = "";
        String issue = "";

        for (String id : requirementId) {
            reqIdsString = reqIdsString + "&requirementId=" + id;
            //usually the list contains only one issue
            issue = id;
        }

        String urlTail = "/getTransitiveClosureOfRequirement?layerCount=" + layerCount + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);

        if (response==null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error connecting to Milla\"}").build();
        }

        Gson gson = new Gson();
        JsonObject responseJSON = gson.fromJson(response, JsonElement.class).getAsJsonObject();

        JsonObject nodeEdgeSet = NodeEdgeSetBuilder.buildNodeEdgeSet(responseJSON, issue, false);
        String nodeEdgeString = nodeEdgeSet.toString();

        MillaResponse closure = null;

        try {
            // Parse the response JSON string to MillaResponse
            ObjectMapper mapper = new ObjectMapper();
//            closure = mapper.readValue(nodeEdgeString, MillaResponse.class);
            closure = mapper.readValue(response, MillaResponse.class);
        } catch (IOException e) {
            JSONObject error = new JSONObject();
            error.put("error", nodeEdgeString);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }
        return Response.ok(nodeEdgeString).build();
//        return Response.ok(closure).build();
    }

    @GET
    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTopProposedDependenciesOfRequirement")
    public Response topProposed(@QueryParam("requirementId") List<String> requirementId, @QueryParam("maxResults")
            Integer maxResults) throws IOException, JqlParseException, SearchException, JSONException {

        String reqIdsString = "";

        if (maxResults==null) {
            maxResults = 0;
        }

        for (String id : requirementId) {
            reqIdsString = reqIdsString + "&requirementId=" + id;
        }

        String urlTail = "/getTopProposedDependenciesOfRequirement?maxResults=" + maxResults + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);

        if (response==null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error connecting to Milla\"}").build();
        }

        MillaResponse closure = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            closure = mapper.readValue(response, MillaResponse.class);
        } catch (IOException e) {
            JSONObject error = new JSONObject();
            error.put("error", response);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }

        return Response.ok(closure).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/updateProposedDependencies")
    public Response sendUpdatedDependencies(List<Dependency> dependencies) throws JqlParseException, SearchException, CreateException, IOException {

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
            Integer layerCount, @QueryParam("timeOut")
                                             Integer timeOut) throws IOException {

        if (layerCount == null) {
            layerCount = 5;
        }
        if (timeOut == null) {
            timeOut = 0;
        }

        String reqIdsString = "";

        for (String id : requirementId) {
            reqIdsString = reqIdsString + "&requirementId=" + id;
        }

        String urlTail = "/getConsistencyCheckForRequirement?layerCount=" + layerCount + "&timeOut=" + timeOut + reqIdsString;

        // Forward the call to OpenReq services in localhost
        String response = millaService.getResponseFromMilla(urlTail, "", false);

        return checkNull(response);
    }

    private Response checkNull(String responseString) {
        if (responseString==null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to Milla").build();
        }
        return Response.ok(responseString).build();
    }
}