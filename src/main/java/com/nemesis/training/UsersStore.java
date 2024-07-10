package com.nemesis.training;

import java.sql.*;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/** Class responsible for database operations. */
@Slf4j
public class UsersStore {
  private final Config config;
  private final Connection conn;
  public static final String INSERT_QUERY = "INSERT INTO USERS (name) VALUES (?)";
  public static final String CREATE_TABLE_QUERY =
      "CREATE TABLE IF NOT EXISTS USERS ("
          + "id LONG AUTO_INCREMENT, "
          + "name VARCHAR(255) NOT NULL)";

  protected UsersStore(final Connection connection) {
    this.config = Config.getConfig();
    this.conn = connection;
    this.createTable();
  }

  /** Executes a CREATE TABLE query, but only if the table doesn't exist yet. */
  private void createTable() {
    try (Statement stmt = this.conn.createStatement()) {
      stmt.execute(CREATE_TABLE_QUERY);
    } catch (SQLException e) {
      log.error("An error occurred while creating the table in the database.{}", e);
    }
  }

  /**
   * Uses an SQL query to insert a username string into the previously created table.
   *
   * @param name username who will be added at database.
   * @return a User Object.
   */
  public User createUser(final String name) {

    long generatedId = 0;

    try (PreparedStatement preparedStatement =
        this.conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, name);
      preparedStatement.executeUpdate();

      generatedId = getGeneratedId(preparedStatement);
    } catch (SQLException e) {
      log.error("An error occurred while adding the user in the database.{}", e);
    }
    return UserBuild.builder().id(Optional.of(generatedId)).name(name).build();
  }

  /**
   * Read the variables of a properties file to create a Connection Object.
   *
   * @param file name of the properties file who got the variables to connect at database.
   * @return a Connection Object.
   */
  public static Connection getConnection(final String file) {
    final Config config = Config.getConfig();
    Connection conn = null;
    try {
      config.getProperties(file);
      conn =
          DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    } catch (SQLException e) {
      log.error("An error occurred while creating a connection with database.{}", e);
    }
    return conn;
  }

  /**
   * Use a PreparedStatement Object to find the id of the last name added at the database.
   *
   * @param prep a PreparedStatement Object created by a Connection.
   * @return the id of the last name added to database.
   */
  public long getGeneratedId(final PreparedStatement prep) {
    long lastId = 0;
    try {
      final ResultSet generatedKeys = prep.getGeneratedKeys();
      if (generatedKeys.next()) {
        lastId = generatedKeys.getInt(1);
      }
    } catch (SQLException e) {
      log.error("An error occurred while find the last id.{}", e);
    }

    return lastId;
  }

  public void verifyUserCreation(final long id) {
    if (id == 0) {
      log.error("Failed to save name to database.");
    } else {
      log.debug("Name added successfully with ID = {}", id);
    }
  }
}
