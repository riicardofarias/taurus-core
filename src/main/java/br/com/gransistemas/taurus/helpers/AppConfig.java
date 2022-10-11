package br.com.gransistemas.taurus.helpers;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private Properties properties;

    public AppConfig() {
        properties = new Properties();

        try {
            properties.load(AppConfig.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }
}
