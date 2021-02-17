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
public class Address extends BaseDBModel {

    private String additionalDetails;
    private String addressId;
    private String city;
    private String country;
    private Integer createdAt;
    private String fax;
    private String firstName;
    private String lastName;
    private String houseNumber;
    private Integer modifiedAt;
    private String phone;
    private String mobile;
    private String state;
    private String street;
    private String title;
    private String zip;
}
