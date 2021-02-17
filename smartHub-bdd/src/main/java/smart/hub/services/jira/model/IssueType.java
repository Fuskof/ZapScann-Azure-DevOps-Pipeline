package smart.hub.services.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.services.jira.enums.IssueTypeKeyEnum;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType {
    @Getter(AccessLevel.NONE)
    private String id;
    private String name;
    private String description;

    public void setId(IssueTypeKeyEnum type) {
        this.id = type.getKey();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
