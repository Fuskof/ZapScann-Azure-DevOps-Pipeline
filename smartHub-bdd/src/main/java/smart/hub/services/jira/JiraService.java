package smart.hub.services.jira;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import smart.hub.helpers.PropertyReader;
import smart.hub.services.jira.enums.JiraRequestType;
import smart.hub.services.jira.exceptions.JiraException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Base64;

class JiraService {
    //region PrivateMembers
    private HttpClient client = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build()).build();
    private String url;
    private String userName;
    private String password;
    private HttpUriRequest request;
    private String pathToProperty;

//endregion

    JiraService() {
        URL res = getClass().getClassLoader().getResource("Jira.properties");
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        pathToProperty = file.getAbsolutePath();

        this.url = PropertyReader.Read(pathToProperty, "jira.url");
        this.userName = PropertyReader.Read(pathToProperty, "jira.user");
        this.password = PropertyReader.Read(pathToProperty, "jira.password");
    }
//endregion

    //region Methods
    JiraService createJiraRequest(JiraRequestType requestType, String uri, HttpEntity body) throws JiraException {
        switch (requestType) {
            case GET:
                request = new HttpGet(url + "rest/api/2/issue/" + uri);
                break;
            case POST:
                request = new HttpPost(url + "rest/api/2/issue/");
                ((HttpPost) request).setEntity(body);
                request.addHeader("Content-Type", "application/json");
                break;
            case PUT:
                request = new HttpPut(url + "rest/api/2/issue/" + uri);
                ((HttpPut) request).setEntity(body);
                break;
            default:
                throw new JiraException("Invalid request type");
        }
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        // for basic auth
        String basicToken = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        request.addHeader("Authorization", "Basic " + basicToken);
        return this;
    }


    synchronized HttpResponse sendRequest() throws IOException {
        return client.execute(request);
    }
//endregion
}
