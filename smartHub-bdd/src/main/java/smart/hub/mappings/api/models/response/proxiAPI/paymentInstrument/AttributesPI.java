package smart.hub.mappings.api.models.response.proxiAPI.paymentInstrument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributesPI {

    private String issuerCountry;
    private String expiryMonth;
    private String cardHolder;
    private String expiryYear;
    private String brand;
    private String cardNumber;
}
