package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import org.junit.jupiter.api.Test;

class UsersStoreTest {
  private final String testPath = "test.application.properties";
  private final String validName = "joaopaulo";
  private final String INSERT_QUERY = "INSERT INTO USERS (name) VALUES (?)";
  private final String CREATE_TABLE_QUERY =
      "CREATE TABLE IF NOT EXISTS USERS ("
          + "id LONG AUTO_INCREMENT, "
          + "name VARCHAR(255) NOT NULL)";

  @Test
  void createTableTestShouldCreateTable() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    UsersStore userTest = new UsersStore(conn);
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createTableTestWhenStatementCreationFailsShouldNotThrowException() throws SQLException {
    Connection connMock = mock(Connection.class);
    when(connMock.createStatement()).thenThrow(new SQLException());
    assertDoesNotThrow(
        () -> {
          new UsersStore(connMock);
        });
  }

  @Test
  void createTableTestWhenStatementExecuteFailsShouldNotThrowException() throws SQLException {
    Connection connMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);

    when(statementMock.execute(CREATE_TABLE_QUERY)).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          new UsersStore(connMock);
        });
  }

  @Test
  void createUserTestWhenNamePassedShouldCreateUser() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    UsersStore usersStore = new UsersStore(conn);
    User userTest = usersStore.createUser(validName);
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createUserTestWhenPreparedStatementCreationFailsShouldNotThrowSqlException()
      throws SQLException {

    Connection connMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(CREATE_TABLE_QUERY)).thenReturn(true);

    when(connMock.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS))
        .thenThrow(new SQLException());

    UsersStore usersTest = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersTest.createUser(validName);
        });
  }

  @Test
  void createUserTestWhenPreparedStatementExecuteSetStringFailsShouldNotThrowSqlException()
      throws SQLException {
    Connection connMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(anyString())).thenReturn(true);

    PreparedStatement prep = mock(PreparedStatement.class);

    when(connMock.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)).thenReturn(prep);

    doThrow(new SQLException()).when(prep).setString(anyInt(), anyString());

    UsersStore usersTest = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersTest.createUser(validName);
        });
  }

  @Test
  void createUserTestWhenPreparedStatementExecuteUpdateFailsShouldNotThrowSqlException()
      throws SQLException {
    Connection connMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(anyString())).thenReturn(true);

    PreparedStatement prep = mock(PreparedStatement.class);

    when(connMock.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)).thenReturn(prep);
    doThrow(new SQLException()).when(prep).executeUpdate();

    UsersStore usersStore = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersStore.createUser(validName);
        });
  }

  @Test
  void getGeneratedIdTestWhenGetGeneratedKeysFailsShouldNotThrowException() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    UsersStore usersStore = new UsersStore(conn);
    PreparedStatement prep = mock(PreparedStatement.class);

    when(prep.getGeneratedKeys()).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          usersStore.getGeneratedId(prep);
        });
    conn.close();
  }

  @Test
  void getGeneratedIdTestWhenNextFailsShouldNotThrowException() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    UsersStore usersStore = new UsersStore(conn);

    PreparedStatement prep = mock(PreparedStatement.class);
    ResultSet resultMock = mock(ResultSet.class);

    when(prep.getGeneratedKeys()).thenReturn(resultMock);
    when(resultMock.next()).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          usersStore.getGeneratedId(prep);
        });
  }

  @Test
  void getGeneratedIdTestWhenNextIsFalseShouldReturn0() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    UsersStore usersStore = new UsersStore(conn);

    PreparedStatement prep = mock(PreparedStatement.class);
    ResultSet resultMock = mock(ResultSet.class);

    when(prep.getGeneratedKeys()).thenReturn(resultMock);
    when(resultMock.next()).thenReturn(false);

    assertEquals(0, usersStore.getGeneratedId(prep));
  }

  @Test
  void getConnectionTestWhenFileIsPassedShouldReturnConnection() throws SQLException {
    Connection conn = UsersStore.getConnection(testPath);
    assertNotNull(conn);
    conn.close();
  }

  @Test
  void getConnectionTestWhenPropertiesIsWrongShouldNotThrowException() {
    assertDoesNotThrow(
        () -> {
          UsersStore.getConnection("invalid.application.properties");
        });
  }
}
