
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class DeviceIdent {

    @GenerateValue("{UUID}")
    private Object deviceIdentToken;

}
