
package smart.hub.mappings.api.models.request.proxyAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class Async {

    @GenerateValue("http://placeholder.succes.url")
    private Object successUrl;

    @GenerateValue("http://placeholder.fail.url")
    private Object failureUrl;

    @GenerateValue("http://placeholder.cancel.url")
    private Object cancelUrl;

    @GenerateValue(type = Notification[].class, isOptional = true)
    private Object notifications;

}
