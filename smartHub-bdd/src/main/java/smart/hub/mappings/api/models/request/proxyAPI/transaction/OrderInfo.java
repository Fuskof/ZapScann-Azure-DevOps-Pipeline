
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.OrderType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.PreOrderIndicator;

import static smart.hub.helpers.Constants.DATE_PATTERN_YYYY_MM_DD;

@Getter
@Setter
public class OrderInfo {

    @GenerateValue(type = Boolean.class, value = "{FALSE}")
    private Object isReorder;

    @GenerateValue(type = PreOrderIndicator.class)
    private Object preOrderIndicator;

    @GenerateValue(value = "{TODAY+1m}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object preOrderDate;

    @GenerateValue(type = OrderType.class)
    private Object orderType;

}
