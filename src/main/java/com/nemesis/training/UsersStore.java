package com.nemesis.training;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UsersStore {

  String url;
  String username;
  String password;
  Config config;
  UsersStore() {
    try {
      this.config = new Config();
      Properties properties = config.getProperties("application.properties");
      this.url = properties.getProperty("db.JDBC_URL");
      this.username = properties.getProperty("db.USERNAME");
      this.password = properties.getProperty("db.PASSWORD");
      try (Connection connection = config.getConnection(url, username, password);
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
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

  public void addUser(String name) {
    String insertSQL;
    try {
      this.config = new Config();
      Properties properties = config.getProperties("application.properties");
      insertSQL = "INSERT INTO USERS (name) VALUES (?)";
      this.url = properties.getProperty("db.JDBC_URL");
      this.username = properties.getProperty("db.USERNAME");
      this.password = properties.getProperty("db.PASSWORD");

      try (Connection connection = config.getConnection(url, username, password);
          PreparedStatement preparedStatement =
              connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        long generatedId;
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1);
            System.out.println("Name added successfully with ID = " + generatedId);}
        else System.err.println("Failed to save name to database.");
        }
      }
    } catch (SQLException | IOException e) {
      System.out.println(
          "An error occurred while adding the user in the database." + e.getMessage());
      e.printStackTrace();
    }
  }
}
