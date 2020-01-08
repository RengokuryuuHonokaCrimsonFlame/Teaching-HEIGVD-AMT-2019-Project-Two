package ch.heigvd.dnd.api.endpoints.helpers;

import java.io.IOException;
import java.util.Properties;
import ch.heigvd.dnd.api.DefaultApi;

public class Environment {
    private DefaultApi api = new DefaultApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.dnd.server.url");
        api.getApiClient().setBasePath(url);

    }

    public DefaultApi getApi() {
        return api;
    }
}
