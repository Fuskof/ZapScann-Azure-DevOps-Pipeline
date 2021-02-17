package smart.hub.mappings.serviceBus.models.rules;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.serviceBus.enums.rulePipeline.PipelineLevel;
import smart.hub.mappings.serviceBus.enums.rulePipeline.PipelineType;

@Getter
@Setter
public class Rule {

    @GenerateValue("{regex:[0-9a-f]{24}}")
    private Object id;

    @GenerateValue
    private Object name;

    @GenerateValue(type = Integer.class, value = "{INT:1..10}")
    private Object order;

    @GenerateValue(type = PipelineLevel.class)
    private Object level;

    @GenerateValue(type = PipelineType.class)
    private Object type;

    @GenerateValue("{INT:8s}")
    private Object merchantId;

}
