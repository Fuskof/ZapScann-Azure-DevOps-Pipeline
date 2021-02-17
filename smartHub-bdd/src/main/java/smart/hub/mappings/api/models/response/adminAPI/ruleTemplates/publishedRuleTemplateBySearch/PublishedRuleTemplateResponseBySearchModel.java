package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.publishedRuleTemplateBySearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedRuleTemplateResponseBySearchModel {

    private String id;
    private String name;
    private String desc;
    private String type;
    private String ruleXml;
    private String status;
    private String createdOn;
    private List<PublishedRuleTemplateAttributesModel> attributes;
}
