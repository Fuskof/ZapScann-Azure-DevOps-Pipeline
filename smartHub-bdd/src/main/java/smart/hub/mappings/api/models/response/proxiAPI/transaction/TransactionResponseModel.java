package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.*;

@Getter
@Setter
public class TransactionResponseModel {

    private ProductType[] allowedProducts;
    private Basket[] basket;
    private Address billingAddress;
    private Long canceledAmount;
    private Long capturedAmount;
    private ChannelType channel;
    private Long createdAt;
    private Currency currency;
    private Customer customer;
    private String description;
    private DeviceIdent deviceIdent;
    private Long initialAmount;
    private String ipAddress;
    private String lastOpereation;
    private String merchantOrderId;
    private Meta meta;
    private Long modifiedAt;
    private String orderId;
    private PaymentInstrument paymentInstrument;
    private String paymentProviderTransactionId;
    private Persona persona;
    private Long preauthorizedAmount;
    private Long privacy;
    private ProductType product;
    private String redirectUrl;
    private Long refundedAmount;
    private Boolean settled;
    private String settlementDate;
    private Address shippingAddress;
    private String source;
    private String statementDescription;
    private OrderStatus status;
    private Long terms;
    private Transaction[] transactions;
    private TransactionType transactionType;


}
