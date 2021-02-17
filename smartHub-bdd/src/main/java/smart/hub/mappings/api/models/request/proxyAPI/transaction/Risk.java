
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class Risk {

    @GenerateValue(type = CustomerAccount.class)
    private Object customerAccount;

    @GenerateValue(type = ShippingInfo.class)
    private Object shippingInfo;

    @GenerateValue(type = OrderInfo.class)
    private Object orderInfo;

}
