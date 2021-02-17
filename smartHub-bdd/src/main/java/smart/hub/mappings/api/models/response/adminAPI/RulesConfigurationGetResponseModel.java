package smart.hub.mappings.api.models.response.adminAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesConfigurationGetResponseModel {

    private String ruleId;
    private String globalId;
    private String merchantId;
    private String body;
}
