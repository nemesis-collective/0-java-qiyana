package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
  public static Properties getProperties(String propertiesName) {
    final Properties properties = new Properties();
    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
      properties.load(inputStream);
    } catch (IOException e) {
      System.out.println("Error in fetching properties: " + e.getMessage());
      return null;
    }
    return properties;
  }
}
