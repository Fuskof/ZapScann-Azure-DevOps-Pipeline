package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class RulesResponseModel {
    private String id;
    private String name;
    private int order;
    private String level;
    private String type;
    private String merchantId;
}
