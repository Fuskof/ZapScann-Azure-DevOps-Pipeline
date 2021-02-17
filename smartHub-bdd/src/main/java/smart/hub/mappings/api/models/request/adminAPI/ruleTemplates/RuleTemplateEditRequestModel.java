package smart.hub.mappings.api.models.request.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleTemplateEditRequestModel {

    @GenerateValue()
    private Object name;

    @GenerateValue()
    private Object description;

    @GenerateValue()
    private Object type;

    @GenerateValue()
    private Object ruleXml;

    @GenerateValue()
    private Object id;

}
