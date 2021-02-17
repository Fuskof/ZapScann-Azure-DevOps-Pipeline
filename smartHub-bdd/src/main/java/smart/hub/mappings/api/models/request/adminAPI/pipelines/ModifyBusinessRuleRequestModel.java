package smart.hub.mappings.api.models.request.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class ModifyBusinessRuleRequestModel {

    @GenerateValue()
    private Object ruleId;

    @GenerateValue()
    private Object ruleName;

    @GenerateValue()
    private Object level;

    @GenerateValue(type = AttributesOfModifyRuleModel[].class, isOptional = true)
    private Object attributes;

}
