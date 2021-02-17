package smart.hub.mappings.db.models.transactionRegistry.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import smart.hub.mappings.db.models.transactionRegistry.request.BaseDBModel;
import smart.hub.mappings.db.models.transactionRegistry.request.CofContract;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends BaseDBModel {

    private Integer createdAt;
    private String merchantPaymentToken;
    private Integer modifiedAt;
    private String origin;
    private String paymentToken;
    private Boolean recurring;
    private String type;
    private CofContract cofContract ;

}
