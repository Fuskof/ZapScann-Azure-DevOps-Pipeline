package smart.hub.services.jira;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import smart.hub.services.jira.enums.IssueTypeKeyEnum;
import smart.hub.services.jira.enums.JiraRequestType;
import smart.hub.services.jira.enums.ProjectKeyEnum;
import smart.hub.services.jira.exceptions.JiraException;
import smart.hub.services.jira.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class JiraApi {

    @Autowired
    private Environment env;

    private JiraService service;
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Create a new JiraApi object based on config keys
     */
    public JiraApi() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        service = new JiraService();
    }
    /**
     * Get an issue based on the provided Issue
     *
     * @param issueKey
     * @return
     * @throws JiraException
     * @throws IOException
     */
    public Issue getIssue(String issueKey) throws JiraException, IOException {
        IssueBean bean = getResponse(JiraRequestType.GET, IssueBean.class, issueKey, null);
        return bean.getFields();
    }

    /**
     * Create new issue
     *
     * @param issue, projectKey
     * @return CreatedIssue
     */
    public CreatedIssue createIssue(Issue issue, ProjectKeyEnum projectKey) throws IOException, JiraException {
        CreateIssueBody body = new CreateIssueBody();
        Project project = new Project();
        IssueType issueType = new IssueType();
        project.setId(projectKey);
        issueType.setId(IssueTypeKeyEnum.BUG);
        issue.setProject(project);
        issue.setIssuetype(issueType);
        body.setFields(issue);
        return getResponse(JiraRequestType.POST, CreatedIssue.class, null, body);
    }

    /**
     * Update existing issue
     *
     * @param issueKey, issue
     */
    public void updateIssue(Issue issue, String issueKey ) throws IOException, JiraException {
        UpdateIssueBody body = new UpdateIssueBody();
        body.setFields(issue);
        getResponse(JiraRequestType.PUT, String.class, issueKey, body);
    }

    /**
     * Get jira response
     *
     * @param requestType
     * @param type
     * @param uri
     * @param <T>
     * @return
     * @throws JiraException
     * @throws IOException
     */
    private <T> T getResponse(JiraRequestType requestType, Class<T> type, String uri, Object body) throws JiraException, IOException {
        HttpResponse response = service
                .createJiraRequest(requestType, uri, new StringEntity(mapper.writeValueAsString(body)))
                .sendRequest();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
            HttpEntity responseBody = response.getEntity();
            if(responseBody != null) {
                String json = EntityUtils.toString(responseBody);
                return mapper.readValue(json, type);
            }
            return null;
        }
        throw new JiraException(response.getStatusLine().getStatusCode()+response.getStatusLine().getReasonPhrase());

    }

}
