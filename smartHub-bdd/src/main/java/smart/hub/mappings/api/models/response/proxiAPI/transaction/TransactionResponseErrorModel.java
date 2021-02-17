package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseErrorModel {

    private Integer status;
    private String message;
    private Integer code;
    private String globalTrackId;
    private TransactionErrorElement[] errors;

}
