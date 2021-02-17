package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.AttributesOfRuleModel;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class RulesResponseModel {
    private String id;
    private String name;
    private int order;
    private String level;
    private String type;
    private String merchantId;
    private String status;
    private AttributesOfRuleModel[] attributes;
}
