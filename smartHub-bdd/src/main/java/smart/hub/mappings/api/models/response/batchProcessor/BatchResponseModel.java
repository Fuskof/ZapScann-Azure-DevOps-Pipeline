package smart.hub.mappings.api.models.response.batchProcessor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchResponseModel {

    private String id;
    private Integer batchSequence;
    private Integer version;
    private Long createdAt;
    private Long generatedAt;
    private String batchRequestFileName;
    private String batchResponseFileName;
    private String processStatus;

}
