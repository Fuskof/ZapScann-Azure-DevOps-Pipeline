package smart.hub.services.jira.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    private String summary;
    private String description;
    private Project project;
    private IssueType issuetype;
}
