package smart.hub.components;

import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static smart.hub.helpers.Constants.LOG_SEPARATOR;

@Component
public class RedisService {

    @Autowired
    private ConnectionState connectionState;

    @Autowired
    private Environment env;

    private Logger logger = Logger.getLogger(RedisService.class.getName());

    public Jedis getRedisConnection() {
        if (Objects.isNull(connectionState.getRedisClient())) {
            Jedis jedis = createRedisConnection(env.getProperty("redis.host"),
                    Integer.parseInt(env.getProperty("redis.port")),
                    env.getProperty("redis.password"),
                    Boolean.parseBoolean(env.getProperty("redis.ssl")));
            connectionState.setRedisClient(jedis);
        }
        return connectionState.getRedisClient();
    }

    public Jedis createRedisConnection(String host, int port, String password, boolean ssl) {
        Jedis jedis = new Jedis(host, port, ssl);
        jedis.auth(password);
        return jedis;
    }

    public void sendRedisEntry(Jedis jedis, String key, String value) {
        jedis.set(key, value);
        logger.info(String.format(LOG_SEPARATOR
                        + "Sent Redis entry:\nkey: %s\nvalue: %s"
                        + LOG_SEPARATOR,
                key, value));
        Serenity.setSessionVariable("redisSentKey").to(key);
        Serenity.setSessionVariable("redisSentValue").to(value);
    }

    public void sendRedisEntry(String key, String value) {
        Jedis jedis = getRedisConnection();
        sendRedisEntry(jedis, key, value);
    }

    public String getRedisEntry(Jedis jedis, String key) {
        String value = jedis.get(key);
        logger.info(String.format(LOG_SEPARATOR
                        + "Received Redis entry:\nkey: %s\nvalue: %s"
                        + LOG_SEPARATOR,
                key, value));
        Serenity.setSessionVariable("redisReceivedValue").to(value);
        return value;
    }

    public String getRedisEntry(String key) {
        Jedis jedis = getRedisConnection();
        return getRedisEntry(jedis, key);
    }

    public Map<String, String> getRedisMap(Jedis jedis, String key) {
        Map<String, String> redisEntry = jedis.hgetAll(key);
        logger.info(String.format(LOG_SEPARATOR
                        + "Received Redis entry:\nkey: %s\nvalue: %s"
                        + LOG_SEPARATOR,
                key, Arrays.asList(redisEntry).toString()));
        Serenity.setSessionVariable("redisReceivedValue").to(redisEntry);
        return redisEntry;
    }

    public Map<String, String> getRedisMap(String key) {
        Jedis jedis = getRedisConnection();
        return getRedisMap(jedis, key);
    }

    public String getRedisValueFromMap(Jedis jedis, String key, String field) {
        Map<String, String> redisEntry = jedis.hgetAll(key);
        String value = redisEntry.get(field);
        logger.info(String.format(LOG_SEPARATOR
                        + "Received Redis entry:\nkey: %s\nvalue: %s"
                        + LOG_SEPARATOR,
                key, value));
        Serenity.setSessionVariable("redisReceivedValue").to(value);
        return value;
    }

    public String getRedisValueFromMap(String key, String field) {
        Jedis jedis = getRedisConnection();
        return getRedisValueFromMap(jedis, key, field);
    }

//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("smarthub3.redis.cache.windows.net", 6380, true);
//        jedis.auth("JcI6CV2NvZq2aj6w2r+J4boSm5Qw8Hd4yUUWw1PmZ50=");
//        String key = "QA_" + System.currentTimeMillis();
//        String value = key + "_value";
//        jedis.set(key, value);
//        System.out.println(jedis.get(key));
//    }
}

