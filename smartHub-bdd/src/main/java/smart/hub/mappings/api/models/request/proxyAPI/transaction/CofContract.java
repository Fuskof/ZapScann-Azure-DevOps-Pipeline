
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.CofContractType;

import static smart.hub.helpers.Constants.DATE_PATTERN_YYYY_MM_DD;

@Getter
@Setter
public class CofContract {

    @GenerateValue(value = "{ALPHANUMERIC:5,8}")
    private Object id;

    @GenerateValue(type = CofContractType.class)
    private Object type;

    @GenerateValue(value = "{TODAY+1y}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object recurringExpiry;
// good version accordingly with specs
//    @GenerateValue("{REGEX:[1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]}")
//    private Object recurringFrequency;

    @GenerateValue(type = Integer.class, value = "{INT:1..5}")
    private Object recurringFrequency;

}
