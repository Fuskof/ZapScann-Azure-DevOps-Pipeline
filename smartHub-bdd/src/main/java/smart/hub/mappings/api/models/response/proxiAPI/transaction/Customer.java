
package smart.hub.mappings.api.models.response.proxiAPI.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private Address[] addresses;
    private String customerId;
    private String email;
    private String merchantCustomerId;
    private PaymentInstrument[] paymentInstruments;
    private Persona[] personas;

}
