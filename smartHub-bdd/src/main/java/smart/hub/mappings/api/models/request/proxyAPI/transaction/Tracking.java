
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TrackingProvider;

@Getter
@Setter
public class Tracking {

    @GenerateValue("{ALPHANUMERIC:8,15}")
    private Object trackingId;

    @GenerateValue(type = TrackingProvider.class)
    private Object provider;

}
