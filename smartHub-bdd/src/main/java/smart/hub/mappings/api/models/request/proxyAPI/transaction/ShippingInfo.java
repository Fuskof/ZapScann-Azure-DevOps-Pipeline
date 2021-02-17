
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.DeliveryTime;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ShippingIndicator;

@Getter
@Setter
public class ShippingInfo {

    @GenerateValue(type = Boolean.class, value = "{TRUE}")
    private Object shippingEqualsBillingAddress;

    @GenerateValue(type = ShippingIndicator.class)
    private Object shippingIndicator;

    @GenerateValue(type = DeliveryTime.class)
    private Object deliveryTime;

    @GenerateValue("{FAKER:INTERNET.EMAILADDRESS}")
    private Object deliveryEmail;

}
