package smart.hub.mappings.serviceBus.models.rules;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class MerchantRulesModel {

    @GenerateValue("{INT:8s}")
    private Object pipelineKey;

    @GenerateValue("{REGEX:([A-Za-z0-9+\\/]{4})*[A-Za-z0-9+\\/]{2}==\\|[A-Za-z0-9+\\/]{3}=}")
    private Object merchantSet;

}
