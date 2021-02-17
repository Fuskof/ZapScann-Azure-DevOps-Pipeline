package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplatesModel {

    private String name;
    private String id;
    private String desc;
    private String type;
    private String ruleXml;
    private String status;
    private String createdOn;
    private String attributes;
}
