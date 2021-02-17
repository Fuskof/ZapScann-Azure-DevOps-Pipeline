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
public class Card extends BaseDBModel{

    private String paymentTokenId;
    private String cardNumber;
    private String verificationNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cardHolder;
    private CofContract cofContract;
}
