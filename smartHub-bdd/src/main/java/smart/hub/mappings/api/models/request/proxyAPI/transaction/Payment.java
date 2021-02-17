
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class Payment {

    @GenerateValue(value = "{ALPHANUMERIC:8,20}", isOptional = true)
    private Object paymentInstrumentId;
    
    //@GenerateValue("{REGEX:[0-9]{12,19}}")
    @GenerateValue("413761000000")
    private Object cardNumber;

    @GenerateValue("{REGEX:[0-9]{4}}")
    private Object verification;

    @GenerateValue("{REGEX:0[1-9]|1[0-2]}")
    private Object expiryMonth;

    @GenerateValue("{REGEX:2[5-9]|[3-9][0-9]}")
    private Object expiryYear;

    @GenerateValue(value = "{FAKER:NAME.FULLNAME}")
    private Object cardHolder;

    @GenerateValue(type = CofContract.class)
    private Object cofContract;

    //fields that SH doesn't need/convert but these fields can be in the original request
    //we should test that they are returned in the response as initially received
    @GenerateValue(isOptional = true)
    private Object bankName;

    @GenerateValue(value = "{REGEX:DE[0-9]{20}}", isOptional = true)
    private Object iban;

    @GenerateValue(value = "{REGEX:[A-Z]{4}DE[0-9]{2}[A-Z0-9]{3}}", isOptional = true)
    private Object bic;

    @GenerateValue(value = "{FAKER:NAME.FULLNAME}", isOptional = true)
    private Object accountHolder;

    @GenerateValue(type = Boolean.class, value = "{FALSE}", isOptional = true)
    private Object deferred;

    @GenerateValue(type = Integer.class, value = "{INT:3..36}", isOptional = true)
    private Object numberOfRates;

    @GenerateValue(type = Integer.class, value = "{INT:500..1500}", isOptional = true)
    private Object rate;

    @GenerateValue(type = Integer.class, value = "{INT:500..1500}", isOptional = true)
    private Object lastRate;

    @GenerateValue(type = Integer.class, value = "{INT:1..100}", isOptional = true)
    private Object interestRate;

    @GenerateValue(type = Integer.class, value = "{INT:500..1500}", isOptional = true)
    private Object totalAmount;

    @GenerateValue(type = Boolean.class, value = "{FALSE}", isOptional = true)
    private Object initializeCustomerTokenization;

    @GenerateValue(type = Mandate.class, isOptional = true)
    private Object mandate;

    @GenerateValue(isOptional = true)
    private Object riskIdentId;

    @GenerateValue(value = "DE", isOptional = true)
    private Object countryCode;

    @GenerateValue(type = Boolean.class, value = "{FALSE}", isOptional = true)
    private Object createRecurringBillingAgreement;

}
