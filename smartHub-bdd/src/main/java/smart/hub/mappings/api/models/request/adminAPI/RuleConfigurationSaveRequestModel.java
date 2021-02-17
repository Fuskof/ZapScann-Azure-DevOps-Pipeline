package smart.hub.mappings.api.models.request.adminAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.models.request.adminAPI.integrationManager.ConfigurationItem;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleConfigurationSaveRequestModel {

    @GenerateValue()
    private Object ruleId;

    @GenerateValue()
    private Object globalId;

    @GenerateValue()
    private Object merchantId;

    @GenerateValue()
    private Object body;
}
