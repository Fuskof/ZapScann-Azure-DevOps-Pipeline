package smart.hub.mappings.api.models.response.adminAPI.ruleTemplates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.TemplatesModel;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleTemplatesListResponseModel {

    private List<TemplatesModel> templates;
    private String totalPages;
    private String totalItems;

    public boolean isSorted(List<TemplatesModel> list, String sortedBy, String order) {
        boolean isSorted = true;
        if (order.equals("Descend"))
            switch (sortedBy) {
                case "Name":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getName().compareTo(list.get(i + 1).getName()) < 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "Type":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getType().compareTo(list.get(i + 1).getType()) < 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "CreatedOn":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getCreatedOn().compareTo(list.get(i + 1).getCreatedOn()) < 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "Status":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getStatus().compareTo(list.get(i + 1).getStatus()) < 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
            }
        else if (order.equals("Ascend"))
            switch (sortedBy) {
                case "Name":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getName().compareTo(list.get(i + 1).getName()) > 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "Type":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getType().compareTo(list.get(i + 1).getType()) > 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "CreatedOn":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getCreatedOn().compareTo(list.get(i + 1).getCreatedOn()) > 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
                case "Status":
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).getStatus().compareTo(list.get(i + 1).getStatus()) > 0) {
                            isSorted = false;
                        }
                    }
                    return isSorted;
            }
        return isSorted;
    }
}
