package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {
  private static Config config;
  private static Properties properties;

  public static Config getConfig() {
    if (config == null) {
      config = new Config();
    }
    return config;
  }

  public String getUrl() {
    return properties.getProperty("db.JDBC_URL");
  }

  public String getUsername() {
    return properties.getProperty("db.USERNAME");
  }

  public String getPassword() {
    return properties.getProperty("db.PASSWORD");
  }

  public Properties getProperties(String propertiesName) {
    Properties prop = new Properties();
    if (!Objects.equals(propertiesName, "")) {
      try (InputStream inputStream =
          Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
        prop.load(inputStream);
        properties = prop;
      } catch (IOException | NullPointerException e) {
        log.error("An error occurred while loading properties." + e.getMessage());
      }
    }

    return prop;
  }
}
