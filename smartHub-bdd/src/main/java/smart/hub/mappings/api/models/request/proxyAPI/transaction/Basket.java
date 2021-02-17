
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ArticleType;

@Getter
@Setter
public class Basket {

    @GenerateValue("{FAKER:COMMERCE.PRODUCTNAME}")
    private Object name;

    @GenerateValue("{ALPHANUMERIC:8,15U}")
    private Object articleNumber;

    @GenerateValue(type = Long.class, value = "{LONG:500..1000}")
    private Object totalPrice;

    @GenerateValue(type = Long.class, value = "{LONG:1000..1500}")
    private Object totalPriceWithTax;

    @GenerateValue(type = Long.class, value = "{LONG:500..1000}")
    private Object unitPrice;

    @GenerateValue(type = Long.class, value = "{LONG:1000..1500}")
    private Object unitPriceWithTax;

    @GenerateValue(type = Integer.class, value = "{INT:1..100}")
    private Object tax;

    @GenerateValue(type = Integer.class, value = "{INT:1..5}")
    private Object quantity;

    @GenerateValue(type = Long.class, value = "{-LONG:1,4}", isOptional = true)
    private Object discount;

    @GenerateValue(type = ArticleType.class)
    private Object articleType;

}
