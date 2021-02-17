package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionErrorElement {

    private Integer code;
    private String field;
    private String message;

}
