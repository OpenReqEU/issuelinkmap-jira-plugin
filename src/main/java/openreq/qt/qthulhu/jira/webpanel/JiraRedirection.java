package openreq.qt.qthulhu.jira.webpanel;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;

import java.util.HashMap;
import java.util.Map;

public class JiraRedirection extends AbstractJiraContextProvider
{
    @Override
    public Map<String, String> getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper)
    {
        Map contextMap = new HashMap();
        Issue issue = (Issue) jiraHelper.getContextParams().get("issue");
        contextMap.put("key", issue.getKey());
        return contextMap;
    }
}