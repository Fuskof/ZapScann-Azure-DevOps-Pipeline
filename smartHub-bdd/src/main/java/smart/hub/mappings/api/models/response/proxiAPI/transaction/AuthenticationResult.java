
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResult {

    private String authenticationValue;
    private ChallengeData challengeData;
    private String eci;
    private ErrorDetails errorDetails;
    private String redirectUrl;
    private String status;
    private String transactionId;
    private String version;

}
