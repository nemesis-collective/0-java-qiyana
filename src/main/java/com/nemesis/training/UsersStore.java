package com.nemesis.training;

import java.sql.*;
import java.util.Optional;

/** Class responsible for database operations. */
public class UsersStore {
  Config config;
  Connection conn;
  public final String INSERT_SQL = "INSERT INTO USERS (name) VALUES (?)";
  public final String CREATE_TABLE_SQL =
      "CREATE TABLE IF NOT EXISTS USERS ("
          + "id LONG AUTO_INCREMENT, "
          + "name VARCHAR(255) NOT NULL)";

  UsersStore(Connection connection) {
    this.config = Config.getConfig();
    this.conn = connection;
    this.createTable();
  }

  /** Executes a CREATE TABLE query, but only if the table doesn't exist yet. */
  public void createTable() {
    try (Statement stmt = this.conn.createStatement()) {
      stmt.execute(CREATE_TABLE_SQL);
    } catch (SQLException e) {
      System.out.println(
          "An error occurred while creating the table in the database." + e.getMessage());
    }
  }

  /**
   * Uses an SQL query to insert a username string into the previously created table.
   *
   * @param name username who will be added at database.
   * @return a User Object.
   */
  public User createUser(String name) {

    long generatedId = 0;

    try (PreparedStatement preparedStatement =
        this.conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, name);
      preparedStatement.executeUpdate();

      generatedId = getGeneratedId(preparedStatement);
    } catch (SQLException e) {
      System.err.println(
          "An error occurred while adding the user in the database." + e.getMessage());
    }
    return UserBuild.builder().id(Optional.of(generatedId)).name(name).build();
  }

  /**
   * Read the variables of a properties file to create a Connection Object.
   *
   * @param file name of the properties file who got the variables to connect at database.
   * @return a Connection Object.
   */
  public static Connection getConnection(String file) {
    Config config = Config.getConfig();
    Connection conn = null;
    try {
      config.getProperties(file);
      conn =
          DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    } catch (SQLException e) {
      System.err.println(
          "An error occurred while creating a connection with database." + e.getMessage());
    }
    return conn;
  }

  /**
   * Use a PreparedStatement Object to find the id of the last name added at the database.
   *
   * @param prep a PreparedStatement Object created by a Connection.
   * @return the id of the last name added to database.
   */
  public long getGeneratedId(PreparedStatement prep) {
    long lastId = 0;
    try {
      ResultSet generatedKeys = prep.getGeneratedKeys();
      if (generatedKeys.next()) {
        lastId = generatedKeys.getInt(1);
      }
    } catch (SQLException e) {
      System.err.println("An error occurred while find the last id." + e.getMessage());
    }
    return lastId;
  }

  public void verifyUserCreation(User user) {
    if (user.getId() == 0) {
      System.err.print("Failed to save name to database.");
    } else {
      System.out.print("Name added successfully with ID = " + user.getId());
    }
  }
}
