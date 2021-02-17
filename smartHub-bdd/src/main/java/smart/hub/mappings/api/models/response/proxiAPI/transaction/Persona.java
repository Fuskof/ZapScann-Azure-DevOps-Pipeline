
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.Gender;

@Getter
@Setter
public class Persona {

    private Long birthday;
    private Long createdAt;
    private String fax;
    private String firstName;
    private Gender gender;
    private String lastName;
    private String mobile;
    private Long modifiedAt;
    private String personaId;
    private String phone;
    private String title;

}
