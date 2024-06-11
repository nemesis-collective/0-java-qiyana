package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
  public  Properties getProperties(String propertiesName) throws IOException{
    final Properties properties = new Properties();
    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
      if(inputStream == null){
        System.out.println("Property file '" + propertiesName + "' not found in the classpath");
        return null;
      }
      properties.load(inputStream);
    } catch (IOException e) {
      System.out.println("Error in fetching properties: " + e.getMessage());
      return null;
    }
    return properties;
  }
}
