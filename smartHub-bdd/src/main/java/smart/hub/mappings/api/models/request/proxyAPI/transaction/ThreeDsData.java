
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class ThreeDsData {

    @GenerateValue(value = "{ALPHANUMERIC:8,15}")
    private Object threeDsAuthenticationId;

    @GenerateValue("{ALPHANUMERIC:8,15}")
    private Object browserInfoId;

    @GenerateValue(value = "{ALPHANUMERIC:5}")
    private Object threeDsVersionId;

    @GenerateValue(value = "2.0")
    private Object version;

    @GenerateValue(value = "{UUID}")
    private Object transactionId;

    @GenerateValue(value = "{REGEX:[A-Za-z]{27}=}")
    private Object authenticationValue;

    @GenerateValue(value = "02")
    private Object eci;

}
