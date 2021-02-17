package smart.hub.mappings.db.models.transactionRegistry.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.mappings.api.enums.proxyAPI.transaction.*;
import smart.hub.mappings.db.models.transactionRegistry.request.BaseDBModel;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponseModel extends BaseDBModel {

    private String serializedResponse;
    private ProductType[] allowedProducts;
    private Integer amount;
    private Integer canceledAmount;
    private Integer capturedAmount;
    private Card card;
    private String channel;
    private Integer createdAt;
    private String currency;
    private Customer customer;
    private String initialDescriptor;
    private String ipAddress;
    private Meta meta;
    private Integer modifiedAt;
    private String orderId;
    private String paymentMethod;
    private Integer preauthorizedAmount;
    private Integer refundedAmount;
    private Boolean settled;
    private String settlementDate;
    private String status;
    private ThreeDsData threeDSdata;
    private Transaction[] transactions;
    private TransactionType transactionType;


}
