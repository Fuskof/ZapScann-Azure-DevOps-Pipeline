package smart.hub.services.jira.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IssueBean {
    private String expand;
    private String id;
    private String key;
    private String self;
    private Issue fields;

   }
