package smart.hub.mappings.db.models.transactionRegistry.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest extends BaseDBModel {

//    private ObjectId _id;
    private String serializedRequest;
    private Integer amount;
    private String currency;
    private String paymentMethod;
    private String initialDescriptor;
    private String transactionRequestTimestamp;
    private String ipAddress;
    private String channel;
    private Customer customer;
    private Card card;
    private BinService binService;

}
