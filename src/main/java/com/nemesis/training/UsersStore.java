package com.nemesis.training;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import static jdk.internal.org.jline.utils.Log.debug;

public class UsersStore {

  String url;
  String username;
  String password;

  UsersStore() {
    try {
      Properties properties = GetProperties.getProperties("application.properties");
      this.url = properties.getProperty("db.JDBC_URL");
      this.username = properties.getProperty("db.USERNAME");
      this.password = properties.getProperty("db.PASSWORD");
      try (Connection connection = DriverManager.getConnection(url, username, password);
          Statement stmt = connection.createStatement()) {
        String createTableSQL =
            "CREATE TABLE IF NOT EXISTS USERS ("
                + "id LONG AUTO_INCREMENT, "
                + "name VARCHAR(255) NOT NULL)";
        stmt.execute(createTableSQL);
      }
    } catch (SQLException e) {
      System.out.println(
          "An error occurred while creating the table in the database." + e.getMessage());
      e.printStackTrace();
    }
  }

  public Long addUser(User user) {
    String insertSQL = "INSERT INTO USERS (name) VALUES (?)";
    long generatedId = 0;
    try {
      Properties properties = GetProperties.getProperties("application.properties");
      this.url = properties.getProperty("db.JDBC_URL");
      this.username = properties.getProperty("db.USERNAME");
      this.password = properties.getProperty("db.PASSWORD");

      try (Connection connection = DriverManager.getConnection(url, username, password);
          PreparedStatement preparedStatement =
              connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatement.setString(1, user.getName());
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1);
          }
        }
      }
    } catch (SQLException e) {
      System.out.println(
          "An error occurred while adding the user in the database." + e.getMessage());
      e.printStackTrace();
    }
    return generatedId;
  }
}
