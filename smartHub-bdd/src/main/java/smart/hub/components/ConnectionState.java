package smart.hub.components;

import com.microsoft.azure.servicebus.QueueClient;
import com.mongodb.MongoClient;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class ConnectionState {

    private CloseableHttpClient client;
    private Map<String, MongoClient> conPool = new HashMap<>();
    private Map<String, QueueClient> serviceBusPool = new HashMap<>();
    private Jedis redisClient = null;
    private String token;
}
