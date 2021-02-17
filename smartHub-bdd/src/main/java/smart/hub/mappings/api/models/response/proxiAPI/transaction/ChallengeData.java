
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ChallengeWindowSize;

@Getter
@Setter
public class ChallengeData {

    private String acsUrl;
    private String base64EncodedChallengeRequest;
    private ChallengeWindowSize challengeWindowSize;

}
