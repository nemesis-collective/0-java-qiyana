package com.nemesis.training;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import static jdk.internal.org.jline.utils.Log.debug;

public class UsersStore {
  static Properties properties = new Properties();

  UsersStore() {
    try {
      properties = getProperties("application.properties");
      String JDBC_URL = properties.getProperty("db.JDBC_URL");
      String USERNAME = properties.getProperty("db.USERNAME");
      String PASSWORD = properties.getProperty("db.PASSWORD");

      try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
          Statement stmt = connection.createStatement()) {
        String createTableSQL =
            "CREATE TABLE IF NOT EXISTS USERS ("
                + "id LONG AUTO_INCREMENT, "
                + "name VARCHAR(255) NOT NULL)";
        stmt.execute(createTableSQL);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Long addUser(User user) {
    String insertSQL = "INSERT INTO USERS (name) VALUES (?)";
    long generatedId = -1;
    try {
      properties = getProperties("application.properties");
      String JDBC_URL = properties.getProperty("db.JDBC_URL");
      String USERNAME = properties.getProperty("db.USERNAME");
      String PASSWORD = properties.getProperty("db.PASSWORD");

      try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
          PreparedStatement preparedStatement =
              connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatement.setString(1, user.name);
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1);
          }
        }
      }
      if (generatedId == -1) {
        throw new Exception("An error occurred while adding the user.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return generatedId;
  }

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
