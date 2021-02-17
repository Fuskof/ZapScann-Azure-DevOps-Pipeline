package smart.hub.mappings.api.models.request.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.publishedRuleTemplateBySearch.PublishedRuleTemplateAttributesModel;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class CreateMidConditionModel {

    private String merchantId;

    private String ruleName;

    private String ruleTemplateId;

    private String level;

    private List<PublishedRuleTemplateAttributesModel> attributes;

}
