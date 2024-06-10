package com.nemesis.training;

import java.io.InputStream;
import java.util.Properties;


public class Config {
  public static Properties getProperties(String propertiesName) {
    final Properties properties = new Properties();
    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
      properties.load(inputStream);
    } catch (Exception e) {
      System.out.println("Error in fetching properties: " + e);
      return null;
    }
    return properties;
  }
}
