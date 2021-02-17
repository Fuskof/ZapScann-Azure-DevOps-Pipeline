package smart.hub.mappings.serviceBus.models.rules;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.serviceBus.enums.rulePipeline.PipelineLevel;
import smart.hub.mappings.serviceBus.enums.rulePipeline.PipelineType;

@Getter
@Setter
public class RulePipelineModel {

    @GenerateValue("{regex:[0-9a-f]{24}}")
    private Object pipelineId;

    @GenerateValue(type = PipelineLevel.class)
    private Object level;

    @GenerateValue(type = PipelineType.class)
    private Object type;

    @GenerateValue(type = Boolean.class)
    private Object enabled;

    @GenerateValue(type = Rule[].class)
    private Object rules;

    @GenerateValue
    private Object pipelineFor;

}
