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
public class ChallengeData extends BaseDBModel {

    private String acsUrl;
    private String base64EncodedChallengeRequest;
    private String challengeWindowSize;
}
