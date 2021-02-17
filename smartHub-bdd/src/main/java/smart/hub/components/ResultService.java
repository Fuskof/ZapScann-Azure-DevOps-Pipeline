package smart.hub.components;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class ResultService {

    @Autowired
    private ResultState resState;

    private Logger logger = Logger.getLogger(ConnectionService.class.getName());

    public String rawResult(HttpResponse resp) throws Exception {
        if( resp.getEntity() == null) return null;

        BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

        StringBuffer rawResult = new StringBuffer();
        String line = "";

        try {
            while ((line = rd.readLine()) != null) {
                rawResult.append(line);
            }
            return rawResult.toString();
        } catch (ConnectionClosedException e) {
            return null;
        }
    }
}
