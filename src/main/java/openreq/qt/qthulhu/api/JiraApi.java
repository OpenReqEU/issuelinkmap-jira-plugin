package openreq.qt.qthulhu.api;

import com.atlassian.jira.user.ApplicationUser;
import openreq.qt.qthulhu.rest.json.Dependency;
import openreq.qt.qthulhu.rest.json.Requirement;

import java.util.List;

public interface JiraApi
{
    List<Requirement> filterRequirements(List<Requirement> requirements, ApplicationUser user);
    String rightsCheckGetResult(String issueId, ApplicationUser user);
    String setAcceptedInJira(List<Dependency> dependencies, ApplicationUser user);
}