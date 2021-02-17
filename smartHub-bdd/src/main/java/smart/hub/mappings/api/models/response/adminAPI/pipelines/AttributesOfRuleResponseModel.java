package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder

public class AttributesOfRuleResponseModel {

    private String key;
    private String description;
    private String value;
    private String uiControl;
    private String valueKind;
    private String[] arrayOfValues;
    private String sourceKey;

}
