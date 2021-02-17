
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ChannelType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.CofContractType;

@Getter
@Setter
public class CofContract {

    private ChannelType channel;
    private String id;
    private String recurringExpiry;
    private Long recurringFrequency;
    private CofContractType type;

}
