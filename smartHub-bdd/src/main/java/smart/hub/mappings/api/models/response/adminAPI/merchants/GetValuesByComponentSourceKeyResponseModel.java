package smart.hub.mappings.api.models.response.adminAPI.merchants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class GetValuesByComponentSourceKeyResponseModel {
    private boolean hasErrors;
    private String errorCode;
    private List <ValuesByComponent> values;
}
