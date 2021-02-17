package smart.hub.mappings.db.models.transactionRegistry.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.mappings.db.models.transactionRegistry.request.BaseDBModel;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResult extends BaseDBModel {

    private String authenticationValue;
    private ChallengeData challengeData;
    private String eci;
    private String redirectUrl;
    private String status;
    private String transactionId;
    private String version;
}
