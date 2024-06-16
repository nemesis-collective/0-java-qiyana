package com.nemesis.training;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UsersStore{
  Config config;
  Connection conn;
  UsersStore(Connection connection){
    this.config = Config.getConfig();
    this.conn = connection;
    this.createTable();
  }

  public void createTable(){
    try (Statement stmt = this.conn.createStatement()) {
        String createTableSQL =
                "CREATE TABLE IF NOT EXISTS USERS ("
                        + "id LONG AUTO_INCREMENT, "
                        + "name VARCHAR(255) NOT NULL)";
        stmt.execute(createTableSQL);

      }
     catch (SQLException e) {
      System.out.println(
              "An error occurred while creating the table in the database." + e.getMessage());
      e.printStackTrace();
    }
  }

  public void addUser(String name) {
    String insertSQL;
    try {
      insertSQL = "INSERT INTO USERS (name) VALUES (?)";

      try (PreparedStatement preparedStatement =
                   this.conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
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
    } catch (SQLException e) {
      System.err.println(
          "An error occurred while adding the user in the database." + e.getMessage());
    }
  }

  public static Connection getConnection() throws IOException, SQLException {
    Config config = Config.getConfig();

    return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
  }

}
