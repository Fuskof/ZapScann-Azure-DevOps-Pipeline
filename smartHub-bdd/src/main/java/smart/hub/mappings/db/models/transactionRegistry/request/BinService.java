package smart.hub.mappings.db.models.transactionRegistry.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinService extends BaseDBModel {

    private String cardBrand;
    private String cardLevel;
    private String cardType;
    private String issuingBank;
    private String issuingCountry;
}
