
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {

    private String acsReferenceNumber;
    private String acsSignedContent;
    private String acsTransactionId;
    private CofContract cofContract;
    private Boolean deferred;
    private String emailSubject;
    private Boolean mobileView;
    private String preferredLanguage;
    private String receiverEmail;
    private String receiverId;
    private String receiverPhone;
    private String receiverType;
    private ThreeDsData threeDsData;
    private String tanSchema;
    private Boolean tanSent;

}
