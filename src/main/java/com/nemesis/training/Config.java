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
  private static Config config;
  private String url;
  private String username;
  private String password;
  private static Properties properties;

  public static Config getConfig() {
    if(config == null){
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
    }
    return properties;
  }

  public Connection getConnection() throws SQLException, IOException {
    Connection connection = null;
    Properties properties = this.getProperties("application.properties");
    String url = properties.getProperty("db.JDBC_URL");
    String username = properties.getProperty("db.USERNAME");
    String password = properties.getProperty("db.PASSWORD");
    try{
      connection = DriverManager.getConnection(url, username, password);
    }catch (SQLException e){
      System.out.println("An error occurred while connecting to database.");
    }
    return connection;
  }
}
