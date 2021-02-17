package smart.hub.mappings.api.models.response.adminAPI.integrationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetConfigurationsResponseModel {

    private ConfigurationItem configurationItem;
    private boolean found;
}
