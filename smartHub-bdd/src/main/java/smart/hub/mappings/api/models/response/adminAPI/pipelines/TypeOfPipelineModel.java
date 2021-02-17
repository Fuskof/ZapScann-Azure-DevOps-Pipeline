package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public enum TypeOfPipelineModel {
    Routing,
    Validation,
    MidConditions

}
