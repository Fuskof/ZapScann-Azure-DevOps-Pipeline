package smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;


@Getter
@Setter
public class Mandate {

    @GenerateValue("{ALPHANUMERIC:8,15}")
    private Object mandateId;

    @GenerateValue(type = Long.class, value = "{NOW:MILLIS}")
    private Object createdDateTime;

    @GenerateValue()
    private Object mandateText;

    @GenerateValue("FIRST")
    private Object directDebitType;
}
