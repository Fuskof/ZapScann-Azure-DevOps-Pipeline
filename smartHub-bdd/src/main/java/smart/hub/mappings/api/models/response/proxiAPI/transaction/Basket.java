
package smart.hub.mappings.api.models.response.proxiAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ArticleType;

@Getter
@Setter
public class Basket {

    private String articleNumber;
    private ArticleType articleType;
    private Long discount;
    private String name;
    private Long quantity;
    private Double tax;
    private Long totalPrice;
    private Long totalPriceWithTax;
    private Long unitPrice;
    private Long unitPriceWithTax;

}
