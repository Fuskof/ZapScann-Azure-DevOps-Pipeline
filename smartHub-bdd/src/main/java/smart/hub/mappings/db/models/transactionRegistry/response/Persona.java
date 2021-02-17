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
public class Persona extends BaseDBModel {

    private Integer birthday;
    private Integer createdAt;
    private String fax;
    private String firstName;
    private String lastName;
    private String gender;
    private String mobile;
    private Integer modifiedAt;
    private String personaId;
    private String phone;
    private String title;
}
