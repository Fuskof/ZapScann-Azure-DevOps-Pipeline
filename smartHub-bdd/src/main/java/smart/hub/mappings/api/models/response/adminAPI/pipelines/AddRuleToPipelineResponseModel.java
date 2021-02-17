package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class AddRuleToPipelineResponseModel {

    private String merchantId;

    private String ruleName;

    private String ruleTemplateId;

    private String level;

    private List<AttributesOfRuleResponseModel> attributes;

}
