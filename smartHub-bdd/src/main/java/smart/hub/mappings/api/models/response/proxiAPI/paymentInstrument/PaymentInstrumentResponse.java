package smart.hub.mappings.api.models.response.proxiAPI.paymentInstrument;

import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.PaymentInstrumentType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ProductType;


@Getter
@Setter
public class PaymentInstrumentResponse  {

    private AttributesPI attributes;
    private Boolean encrypted;
    private String recurringConsent;
    private Long createdAt;
    private String merchantPaymentInstrumentId;
    private Long modifiedAt;
    private ProductType origin;
    private String paymentInstrumentId;
    private Boolean recurring;
    private PaymentInstrumentType type;


}
