package smart.hub.mappings.api.models.request.proxyAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ChannelType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ProductType;

@Getter
@Setter
public class DebitPreauthRequestModel extends BaseTransactionRequestModel {

    @GenerateValue(type = ProductType.class)
    private Object product;

    @GenerateValue(type = Async.class)
    private Object async;

    @GenerateValue("Statement description {ALPHANUMERIC:8,20l}")
    private Object statementDescription;

    @GenerateValue(value = "{ALPHANUMERIC:8,15U}", isOptional = true)
    private Object customer;

    @GenerateValue(value = "{ALPHANUMERIC:8,15U}", isOptional = true)
    private Object persona;

    @GenerateValue(value = "{FAKER:ADDRESS.FULLADDRESS}", isOptional = true)
    private Object billingAddress;

    @GenerateValue(value = "{FAKER:ADDRESS.FULLADDRESS}", isOptional = true)
    private Object shippingAddress;

    @GenerateValue(value = "{FAKER:INTERNET.IPV4ADDRESS}")
    private Object ipAddress;

    @GenerateValue(type = ChannelType.class)
    private Object channel;

    @GenerateValue(type = Payment.class)
    private Object payment;

    @GenerateValue(type = DeviceIdent.class, isOptional = true)
    private Object deviceIdent;

    @GenerateValue(type = Tracking[].class, isOptional = true)
    private Object tracking;

    @GenerateValue(type = Risk.class, isOptional = true)
    private Object risk;

    //fields that SH doesn't need/convert but these fields can be in the original request
    //we should test that they are returned in the response as initially received
    @GenerateValue(type = Long.class, value = "{NOW:MILLIS}", isOptional = true)
    private Object terms;

    @GenerateValue(type = Long.class, value = "{NOW:MILLIS}", isOptional = true)
    private Object privacy;

    @GenerateValue(isOptional = true)
    private Object merchantOrderId;

    @GenerateValue(isOptional = true)
    private Object source;

    @GenerateValue(value = "en-US", isOptional = true)
    private Object locale;

}
