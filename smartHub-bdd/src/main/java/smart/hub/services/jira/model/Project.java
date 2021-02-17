package smart.hub.services.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.services.jira.enums.ProjectKeyEnum;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    @Getter(AccessLevel.NONE)
    private String id;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(ProjectKeyEnum projectKey) {
        this.id = projectKey.getKey();
    }

    public void setId(String id) {
        this.id = id;
    }
}
