package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
  private static Config config;
  private String url;
  private String username;
  private String password;
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

  public Properties getProperties(String propertiesName) throws IOException {
    if (properties == null) {
      Properties prop = new Properties();
      try (InputStream inputStream =
          Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
        if (inputStream == null) {
          throw new IOException(
              "Property file '" + propertiesName + "' was not found or could not be read.");
        }
        prop.load(inputStream);
        properties = prop;
      }
    }
    return properties;
  }
}
