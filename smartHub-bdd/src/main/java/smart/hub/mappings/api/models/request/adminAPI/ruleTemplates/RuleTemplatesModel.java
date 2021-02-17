package smart.hub.mappings.api.models.request.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleTemplatesModel {
    private String id;
    private String name;
    private String type;
}
