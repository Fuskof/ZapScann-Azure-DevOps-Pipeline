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
public class Customer extends BaseDBModel {

    private Address addresses;
    private BillingAddress billingAddress;
    private String customerId;
    private String email;
    private String merchantCustomerId;
    private Persona persona;
    private ShippingAddress shippingAddress;

}
