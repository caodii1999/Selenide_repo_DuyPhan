package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvConfig {

  private static Properties configProps;
  private static Properties accountProps;

  static {
    loadProperties();
  }

  private static void loadProperties() {
    try {
      configProps = new Properties();
      configProps.load(Files.newInputStream(Paths.get("src/main/resources/config.properties")));

      accountProps = new Properties();
      accountProps.load(Files.newInputStream(Paths.get("src/main/resources/account.properties")));

      log.info("Configuration files loaded successfully");

    } catch (IOException e) {
      log.error("Failed to load configuration files: {}", e.getMessage(), e);
      throw new RuntimeException("Failed to load configuration files", e);
    }
  }

  public static String getProperty(String key) {
    String value = configProps.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Property '" + key + "' not found in config.properties");
    }
    return value;
  }

  public static String getAccountProperty(String key) {
    String value = accountProps.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Property '" + key + "' not found in account.properties");
    }
    return value;
  }

  public static String getBrowser() {
    return getProperty("browser");
  }

  public static String getBaseUrl() {
    return getProperty("urlHost");
  }
  
  public static String getEmail() {
    return getAccountProperty("EMAIL");
  }

  public static String getPassword() {
    return getAccountProperty("PASSWORD");
  }
}