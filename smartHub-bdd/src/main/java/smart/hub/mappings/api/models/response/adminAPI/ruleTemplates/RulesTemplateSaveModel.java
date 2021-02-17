package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class RulesTemplateSaveModel {
    private boolean isRuleEmpty;
    private boolean isRuleValid;
    private String output;
    private String clientInvalidData;
}