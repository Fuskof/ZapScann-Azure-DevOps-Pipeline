package smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ProductType;

@Getter
@Setter
public class PaymentInstrumentRequest {

    @GenerateValue(type = ProductType.class)
    private Object type;

    @GenerateValue(value = "{ALPHANUMERIC:5,12}")
    private Object merchantPaymentInstrumentId;

    @GenerateValue(type = PaymentPI.class)
    private Object payment;


}
