package smart.hub.mappings.api.models.request.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PublishPipelineRequestModel {

    private String merchantId;
    private String level;
}
