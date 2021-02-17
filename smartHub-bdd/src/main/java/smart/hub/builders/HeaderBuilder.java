package smart.hub.builders;

import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import smart.hub.components.ConnectionState;

@Component
public class HeaderBuilder {

    @Autowired
    private Environment env;

    @Autowired
    private ConnectionState conState;

    public HeaderBuilder basicHeaderProps(HttpRequestBase request){
        request.addHeader("accept", env.getProperty("accept"));
        request.addHeader("Authorization", conState.getToken());
        request.addHeader("Content-Type", env.getProperty("contentType"));
        return this;
    }

    public HeaderBuilder firstLoginHeaderProps(HttpRequestBase request){
        request.addHeader("accept", env.getProperty("accept"));
        request.addHeader("Content-Type", env.getProperty("contentType"));
        return this;
    }
}
