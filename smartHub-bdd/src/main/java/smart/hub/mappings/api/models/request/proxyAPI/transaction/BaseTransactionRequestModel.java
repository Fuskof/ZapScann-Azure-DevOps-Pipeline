package smart.hub.mappings.api.models.request.proxyAPI.transaction;

import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class BaseTransactionRequestModel {

    @GenerateValue(type = Long.class, value = "{LONG:6..999}")
    private Object initialAmount;

    @GenerateValue("EUR")
    private Object currency;

    @GenerateValue(value = "Description {ALPHANUMERIC:8,20l}", isOptional = true)
    private Object description;

    @GenerateValue(type = Basket[].class, isOptional = true)
    private Object basket;

    @GenerateValue(type = Meta.class)
    private Object meta;
}
