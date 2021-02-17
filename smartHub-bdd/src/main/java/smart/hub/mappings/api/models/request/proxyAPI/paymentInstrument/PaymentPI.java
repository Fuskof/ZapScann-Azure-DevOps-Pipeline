package smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.paymentInstrument.Country;

@Getter
@Setter
public class PaymentPI {

    @GenerateValue("{REGEX:[0-9]{12,19}}")
    private Object cardNumber;

    @GenerateValue(type = Country.class)
    private Object country;

    @GenerateValue("EUR")
    private Object currency;

    @GenerateValue(value = "en-US")
    private Object locale;

    @GenerateValue("{REGEX:[0-9]{4}}")
    private Object verification;

    @GenerateValue("{REGEX:0[1-9]|1[0-2]}")
    private Object expiryMonth;

    @GenerateValue("{REGEX:2[5-9]|[3-9][0-9]}")
    private Object expiryYear;

    @GenerateValue(value = "{FAKER:NAME.FULLNAME}")
    private Object cardHolder;

    @GenerateValue(isOptional = true)
    private Object bankName;

    @GenerateValue(value = "{REGEX:DE[0-9]{20}}")
    private Object iban;

    @GenerateValue(value = "{REGEX:[A-Z]{4}DE[0-9]{2}[A-Z0-9]{3}}")
    private Object bic;

    @GenerateValue(value = "{FAKER:NAME.FULLNAME}")
    private Object accountHolder;

    @GenerateValue(value = "{ALPHANUMERIC:8,15U}")
    private Object customer;

    @GenerateValue(type = Mandate.class)
    private Object mandate;

    @GenerateValue(type = BasketPI.class)
    private Object basket;

    @GenerateValue("http://placeholder.return.url")
    private Object returnUrl;

    @GenerateValue("http://placeholder.cancel.url")
    private Object cancelUrl;

    @GenerateValue("http://placeholder.notify.url")
    private Object notifyUrl;

}
