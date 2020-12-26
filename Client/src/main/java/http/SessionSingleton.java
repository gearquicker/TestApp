package http;

import main.Constants;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Properties;

public class SessionSingleton {

    private static volatile SessionSingleton instance;

    private final HttpClient httpClient;
    private final String uri;

    private SessionSingleton() {
        httpClient = HttpClientBuilder.create().build();
        uri = getUriFromConfig();
    }

    private String getUriFromConfig(){
        try {
            Properties config = new Properties();
            config.load(getClass().getResource("/config.ini").openStream());
            return config.getProperty("uri") + "/Server/test?request=";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Constants.DEFAULT_URI + "/Server/test?request=";
    }

    public static SessionSingleton getInstance() {
        if (instance == null) {
            synchronized (SessionSingleton.class) {
                if (instance == null) {
                    instance = new SessionSingleton();
                }
            }
        }
        return instance;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public String getUri() {
        return uri;
    }
}