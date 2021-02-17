package smart.hub.mappings.api.models.response.adminAPI.merchants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllExternalMerchantsResponseModel {
    private String id;
    private String externalId;
    private String parentId;
    private String name;
    private String source;
    private int mcc;
    private String branch;
    private String country;
    private String path;
    private String acquiringContract;
    private String mainAddrStreet;
    private String mainAddrCountry;
    private String mainAddrPostalCode;
    private String mainAddrCity;
    private String mainAddrPhoneNumber;
    private String mainAddrEmail;
    private String status;
    private String onboardedOn;
    private boolean hasChildren;
    private List<SchemeMids> schemeMids;
    private String processor;
    private String peMerchantId;
    private String apiKey;
    private String merchantType;
    private String type;
}
