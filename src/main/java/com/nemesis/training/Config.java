package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {
  private static Config conf;
  private static Properties properties;

  protected Config() {}

  public static Config getConfig() {
    if (conf == null) {
      conf = new Config();
    }
    return conf;
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
        if (inputStream != null) {
          prop.load(inputStream);
          properties = prop;
        }
      } catch (IOException e) {
        log.error("An error occurred while loading properties.{}", e);
      }
    }

    return prop;
  }
}
