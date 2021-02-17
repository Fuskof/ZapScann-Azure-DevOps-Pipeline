package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelCaptureRefundResponseModel extends TransactionResponseModel {

    private String type;
    private String transactionId;
    private TransactionResponseModel order;
    private Transaction parentTransaction;
    private String descriptor;
    private Challange challange;
}
