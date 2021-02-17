package smart.hub.mappings.api.models.request.adminAPI.integrationManager;

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

    @GenerateValue()
    private Object key;

    @GenerateValue()
    private Object value;

    @GenerateValue()
    private Object type;


}
