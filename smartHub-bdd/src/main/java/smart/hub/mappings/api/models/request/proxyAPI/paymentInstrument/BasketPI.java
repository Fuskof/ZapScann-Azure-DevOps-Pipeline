package smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ArticleType;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.Basket;


@Getter
@Setter
public class BasketPI  {

    @GenerateValue("{FAKER:COMMERCE.PRODUCTNAME}")
    private Object name;

    @GenerateValue(type = ArticleType.class)
    private Object articleType;

    @GenerateValue("http://article.url")
    private Object articleUrl;

    @GenerateValue("http://image.url")
    private Object imageUrl;

    @GenerateValue(type = Integer.class, value = "{INT:1..5}")
    private Object quantity;

    @GenerateValue(type = Long.class, value = "{LONG:500..1000}")
    private Object unitPrice;

    @GenerateValue(type = Long.class, value = "{LONG:500..1000}")
    private Object totalPrice;

    @GenerateValue(type = Long.class, value = "{-LONG:1,4}")
    private Object discount;





}
