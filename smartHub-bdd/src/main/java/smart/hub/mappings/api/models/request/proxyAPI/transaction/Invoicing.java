
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;

@Getter
@Setter
public class Invoicing {

    @GenerateValue("{ALPHANUMERIC:8,15}")
    private Object invoiceId;

    @GenerateValue("{TODAY}")
    private Object invoiceDate;

    @GenerateValue("{TODAY+2d}")
    private Object deliveryDate;

    @GenerateValue("{TODAY+7d}")
    private Object dueDate;

}
