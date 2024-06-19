package com.nemesis.training;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class UsersStore {
    Config config;
    Connection conn;
    public final String insertSQL = "INSERT INTO USERS (name) VALUES (?)";
    public final String createTableSQL =
            "CREATE TABLE IF NOT EXISTS USERS ("
                    + "id LONG AUTO_INCREMENT, "
                    + "name VARCHAR(255) NOT NULL)";

    UsersStore(Connection connection) {
        this.config = Config.getConfig();
        this.conn = connection;
        this.createTable();
    }

    public void createTable() {
        try (Statement stmt = this.conn.createStatement()) {
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS USERS ("
                            + "id LONG AUTO_INCREMENT, "
                            + "name VARCHAR(255) NOT NULL)";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println(
                    "An error occurred while creating the table in the database." + e.getMessage());
        }
    }

    public User createUser(String name) {

        long generatedId = 0;

        try (PreparedStatement preparedStatement =
                     this.conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            generatedId = getGeneratedKeys(preparedStatement);
        } catch (SQLException e) {
            System.err.println(
                    "An error occurred while adding the user in the database." + e.getMessage());
        }
        return UserBuild.builder()
                .id(Optional.of(generatedId))
                .name(name)
                .build();
    }

    public static Connection getConnection(String file) throws IOException, SQLException {
        Config config = Config.getConfig();
        config.getProperties(file);
        return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    }

    public long getGeneratedKeys(PreparedStatement prep) throws SQLException {
        long generatedId = 0;
        try {
            ResultSet generatedKeys = prep.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro.");
        }
        return generatedId;
    }
}
