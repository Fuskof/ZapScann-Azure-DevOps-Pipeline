package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplatesModel;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedRuleTemplateResponseModel {

    private List<RuleTemplatesModel> ruleTemplates;
    private boolean isSuccess;
}
