package com.nemesis.training;

import java.io.InputStream;
import java.util.Properties;

import static jdk.internal.org.jline.utils.Log.debug;

public class GetProperties {
  public static Properties getProperties(String propertiesName) {
    final Properties properties = new Properties();
    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
      properties.load(inputStream);
    } catch (Exception e) {
      debug("Error in fetching properties: ", e);
      return null;
    }
    return properties;
  }
}
