
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class Meta {

    @GenerateValue(type = ThreeDsData.class)
    private Object threeDsData;

    //fields that SH doesn't need/convert but these fields can be in the original request
    //we should test that they are returned in the response as initially received
    @GenerateValue(type = Invoicing.class, isOptional = true)
    private Object invoicing;

    @GenerateValue(value = "en", isOptional = true)
    private Object preferredLanguage;

    @GenerateValue(value = "{ALPHANUMERIC:8,15}", isOptional = true)
    private Object referenceId;

    @GenerateValue(type = Boolean.class, value = "{FALSE}", isOptional = true)
    private Object addressOverride;

    @GenerateValue(value = "{ALPHANUMERIC:8,15}", isOptional = true)
    private Object tan;

    @GenerateValue(type = Boolean.class, value = "{FALSE}", isOptional = true)
    private Object mobileView;

}
