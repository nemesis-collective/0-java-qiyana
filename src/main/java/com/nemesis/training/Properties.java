package com.nemesis.training;

import java.io.InputStream;

import static jdk.internal.org.jline.utils.Log.debug;

public class Properties {
  public static java.util.Properties getProperties(String propertiesName) {
    final java.util.Properties properties = new java.util.Properties();
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
