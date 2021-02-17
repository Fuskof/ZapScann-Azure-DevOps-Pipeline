
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {

    private Long providerCode;
    private String providerMessage;

}
