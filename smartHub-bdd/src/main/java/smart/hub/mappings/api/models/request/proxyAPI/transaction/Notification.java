
package smart.hub.mappings.api.models.request.proxyAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.NotificationState;

@Getter
@Setter
public class Notification {

    @GenerateValue("http://placeholder.callback.urn")
    private Object notificationUrn;

    @GenerateValue(type = NotificationState[].class, arrayLengths = {1})
    private Object notificationState;

}
