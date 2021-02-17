package smart.hub.components;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class  ResultState {

    private String result;
    private int statusCode;
    private String statusMessage;
    private Header[] headers;
    private String requestJson;
}
