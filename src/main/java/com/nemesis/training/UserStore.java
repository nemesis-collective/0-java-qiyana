package com.nemesis.training;

import org.h2.table.Table;

import java.sql.*;

public class UserStore {

  private static final String JDBC_URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE";
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";

  static {
    // Initialize database and create table if not exists
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        Statement stmt = connection.createStatement()) {
      String createTableSQL =
          "CREATE TABLE IF NOT EXISTS USERS ("
              + "id LONG AUTO_INCREMENT, "
              + "name VARCHAR(255) NOT NULL)";
      stmt.execute(createTableSQL);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static int addName(String name) {
    String insertSQL = "INSERT INTO USERS (name) VALUES (?)";
    int generatedId = -1;

    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement preparedStatement =
            connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, name);
      preparedStatement.executeUpdate();

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          generatedId = generatedKeys.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return generatedId;
  }
}
