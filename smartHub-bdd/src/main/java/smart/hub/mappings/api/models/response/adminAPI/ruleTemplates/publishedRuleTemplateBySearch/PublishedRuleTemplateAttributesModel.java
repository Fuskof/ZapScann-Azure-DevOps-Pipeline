package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.publishedRuleTemplateBySearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PublishedRuleTemplateAttributesModel {
    private String key;
    private String description;
    private String value;
    private String uiControl;
    private String valueKind;
    private List<String> arrayOfValues;
    private String sourceKey;
}
