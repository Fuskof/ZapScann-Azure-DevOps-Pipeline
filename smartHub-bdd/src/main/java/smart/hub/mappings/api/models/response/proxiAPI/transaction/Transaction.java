
package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TransactionStatus;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TransactionType;

@Getter
@Setter
public class Transaction {

    private Long canceledAmount;
    private Long capturedAmount;
    private Challange challange;
    private Long createdAt;
    private String currency;
    private String description;
    private String descriptor;
    private Long initialAmount;
    private Meta meta;
    private Long modifiedAt;
    private String order;
    private String parentTransaction;
    private Long refundedAmount;
    private TransactionStatus status;
    private Tracking[] tracking;
    private String transactionId;
    private String[] transactions;
    private TransactionType type;

}
