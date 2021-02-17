package smart.hub.steps;

import configuration.AppConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionState;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class Hooks {

    private CloseableHttpClient client;

    @Autowired
    private ConnectionState connectionState;

    @Autowired
    private Environment env;

    @Before
    public void initialization() throws Exception {

        if (System.getProperty("serenity.proxy.http") != null) {
            int port = Integer.valueOf(System.getProperty("serenity.proxy.http_port"));
            String url = System.getProperty("serenity.proxy.http");

            HttpClientBuilder hcBuilder = HttpClients.custom();
            HttpHost proxy = new HttpHost(url, port, "http");
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            hcBuilder.setRoutePlanner(routePlanner);
            client = hcBuilder.build();
        } else {
            client = HttpClientBuilder.create().build();
        }

        connectionState.setClient(client);
        connectionState.setToken(env.getProperty("smartHub.auth"));
    }

    @After
    public void teardown() throws Exception {
        connectionState.getClient().close();
        getDriver().quit();
    }
}
