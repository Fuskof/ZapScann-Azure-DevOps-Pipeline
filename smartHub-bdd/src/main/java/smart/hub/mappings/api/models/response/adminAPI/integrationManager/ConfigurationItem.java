package smart.hub.mappings.api.models.response.adminAPI.integrationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationItem {

    private String key;
    private String value;
    private String type;
}
