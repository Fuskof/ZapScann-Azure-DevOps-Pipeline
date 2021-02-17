package smart.hub.mappings.api.models.response.adminAPI.pipelines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.RulesResponseModel;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PipeLinesModel {

    private String pipelineId;
    private TypeOfPipelineLevelModel level;
    private TypeOfPipelineModel type;
    private boolean enabled;
    private List<RulesResponseModel> rules;
    private String pipelineFor;

}
