package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import org.junit.jupiter.api.Test;

class UsersStoreTest {

  private final String INSERT_QUERY = "INSERT INTO USERS (name) VALUES (?)";
  private final String CREATE_TABLE_QUERY =
      "CREATE TABLE IF NOT EXISTS USERS ("
          + "id LONG AUTO_INCREMENT, "
          + "name VARCHAR(255) NOT NULL)";

  @Test
  void createTableTest_shouldCreateTable() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
    UsersStore userTest = new UsersStore(conn);
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createTableTest_whenStatementCreationFails_shouldNotThrowException() throws SQLException {
    Connection connMock = mock(Connection.class);
    when(connMock.createStatement()).thenThrow(new SQLException());
    assertDoesNotThrow(
        () -> {
          new UsersStore(connMock);
        });
  }

  @Test
  void createTableTest_whenStatementExecuteFails_shouldNotThrowException() throws SQLException {
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
  void createUserTest_whenNamePassed_shouldCreateUser() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
    UsersStore usersStore = new UsersStore(conn);
    User userTest = usersStore.createUser("joaopaulo");
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createUserTest_whenPreparedStatementCreationFails_shouldNotThrowSqlException()
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
          usersTest.createUser("joaopaulo");
        });
  }

  @Test
  void createUserTest_whenPreparedStatementExecuteSetStringFails_shouldNotThrowSqlException()
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
          usersTest.createUser("joaopaulo");
        });
  }

  @Test
  void createUserTest_whenPreparedStatementExecuteUpdateFails_shouldNotThrowSqlException()
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
          usersStore.createUser("joaopaulo");
        });
  }

  @Test
  void getGeneratedIdTest_whenGetGeneratedKeysFails_shouldNotThrowException() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
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
  void getGeneratedIdTest_whenNextFails_shouldNotThrowException() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
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
  void getGeneratedIdTest_whenNextIsFalse_shouldReturn0() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
    UsersStore usersStore = new UsersStore(conn);

    PreparedStatement prep = mock(PreparedStatement.class);
    ResultSet resultMock = mock(ResultSet.class);

    when(prep.getGeneratedKeys()).thenReturn(resultMock);
    when(resultMock.next()).thenReturn(false);

    assertEquals(0, usersStore.getGeneratedId(prep));
  }

  @Test
  void getConnectionTest_whenFileIsPassed_shouldReturnConnection() throws SQLException {
    Connection conn = UsersStore.getConnection("test.application.properties");
    assertNotNull(conn);
    conn.close();
  }

  @Test
  void getConnectionTest_whenPropertiesIsWrong_shouldNotThrowException() {
    assertDoesNotThrow(
        () -> {
          UsersStore.getConnection("invalid.application.properties");
        });
  }
}
