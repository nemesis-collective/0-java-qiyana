package com.nemesis.training;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Config {
  public  Properties getProperties(String propertiesName) throws IOException{
    final Properties properties = new Properties();
    try (InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName)) {
      if(inputStream == null){
        throw new IOException("Property file '" + propertiesName + "' was not found or could not be read.");
      }
      properties.load(inputStream);
    }
    return properties;
  }

  public Connection getConnection(String url, String username, String password) throws SQLException {
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(url, username, password);
    }catch (SQLException e){
      System.out.println("An error occurred while connecting to database.");
    }
    return connection;
  }
}
