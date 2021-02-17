
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceIdent {

    private String city;
    private Long confidencelevel;
    private Long confidencelevelhistory;
    private String continent;
    private String countrycode;
    private String countryname;
    private String deviceType;
    private String exactid;
    private String exactidCreated;
    private Long fraudscore;
    private Long fraudscoreRaw;
    private String fraudscoreRulematches;
    private String ip;
    private String languages;
    private Long latitude;
    private Long longitude;
    private String regioncode;
    private String regionname;
    private String ruleScores;
    private String smartid;
    private String smartidCreated;
    private String token;
    private String verifiedOs;

}
