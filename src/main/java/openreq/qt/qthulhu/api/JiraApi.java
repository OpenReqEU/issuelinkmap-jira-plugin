package openreq.qt.qthulhu.api;

import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.user.ApplicationUser;
import openreq.qt.qthulhu.rest.json.Dependency;
import openreq.qt.qthulhu.rest.json.Requirement;

import java.util.List;

public interface JiraApi
{
    List<Requirement> filterRequirements(List<Requirement> requirements, ApplicationUser user) throws JqlParseException, SearchException;
    String rightsCheckGetResult(String issueId, ApplicationUser user) throws JqlParseException, SearchException;
    String setAcceptedInJira(List<Dependency> dependencies, ApplicationUser user) throws JqlParseException, SearchException, CreateException;
}