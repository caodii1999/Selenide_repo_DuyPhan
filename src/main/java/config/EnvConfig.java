package config;

import helper.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class EnvConfig {

    private final Properties configProps;

    public EnvConfig() {
        try {
            configProps = new Properties();
            configProps.load(Files.newInputStream(Paths.get(Constants.CONFIG_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EnvConfig(String path) {
        try {
            configProps = new Properties();
            configProps.load(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBaseUrl() {
        return getProperty("urlHost");
    }

    public String getEmail() {
        return getAccountProperty("EMAIL");
    }

    public String getPassword() {
        return getAccountProperty("PASSWORD");
    }

    public String getProperty(String key) {
        String value = configProps.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    public String getAccountProperty(String key) {
        String value = configProps.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public String getRemoteUrl() {
        return getProperty("remoteUrl");
    }
}