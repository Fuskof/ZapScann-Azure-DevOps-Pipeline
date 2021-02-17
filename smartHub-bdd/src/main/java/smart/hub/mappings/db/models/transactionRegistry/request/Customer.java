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
public class Customer extends BaseDBModel {

    private String persona;
    private String billingAddress;
    private String shippingAddress;

}
