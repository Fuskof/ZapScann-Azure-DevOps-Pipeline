package smart.hub.mappings.api.models.response.adminAPI.merchants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetExternalMerchantsResponseModel {

    private String externalId;
    private String name;
}
