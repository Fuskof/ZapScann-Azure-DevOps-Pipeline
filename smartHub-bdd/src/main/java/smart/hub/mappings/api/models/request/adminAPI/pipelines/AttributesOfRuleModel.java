package smart.hub.mappings.api.models.request.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class AttributesOfRuleModel {

    @GenerateValue()
    private Object key;

    @GenerateValue()
    private Object description;

    @GenerateValue()
    private Object value;

    @GenerateValue()
    private Object uiControl;

    @GenerateValue()
    private Object valueKind;

    @GenerateValue()
    private Object sourceKey;

    @GenerateValue(type = String[].class, isOptional = true)
    private Object arrayOfValues;


}
