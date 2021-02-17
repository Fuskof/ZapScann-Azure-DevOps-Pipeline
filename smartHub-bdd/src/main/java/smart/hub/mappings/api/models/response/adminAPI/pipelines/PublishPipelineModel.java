package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PublishPipelineModel {

    private String level;
    private String merchantId;

}
