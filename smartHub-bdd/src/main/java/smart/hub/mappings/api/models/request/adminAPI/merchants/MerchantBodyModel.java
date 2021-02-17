package smart.hub.mappings.api.models.request.adminAPI.merchants;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class MerchantBodyModel {
    @GenerateValue()
    private Object type;
}
