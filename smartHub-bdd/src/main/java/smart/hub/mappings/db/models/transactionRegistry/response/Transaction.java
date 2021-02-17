package smart.hub.mappings.db.models.transactionRegistry.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.mappings.db.models.transactionRegistry.request.BaseDBModel;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction extends BaseDBModel {

    private String transactionId;
    private String type;
    private String meta;
    private Integer canceledAmount;
    private Integer capturedAmount;
    private Integer createdAt;
    private String currency;
    private String description;
    private String descriptor;
    private Long initialAmount;
    private Integer modifiedAt;
    private String order;
    private String parentTransaction;
    private Long refundedAmount;
    private String status;
    private Transaction transactions;
    private Tracking tracking;
}
