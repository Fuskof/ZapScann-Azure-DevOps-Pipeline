
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TrackingProvider;

@Getter
@Setter
public class Tracking {

    private TrackingProvider provider;
    private String trackingId;

}
