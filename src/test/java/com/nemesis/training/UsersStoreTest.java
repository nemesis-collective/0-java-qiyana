package com.nemesis.training;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import org.junit.jupiter.api.Test;

class UsersStoreTest {
  private static final String TEST_PATH = "test.application.properties";
  private static final String VALID_NAME = "joaopaulo";

  @Test
  void createTableTestShouldCreateTable() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
    final UsersStore userTest = new UsersStore(conn);
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createTableTestWhenStatementCreationFailsShouldNotThrowException() throws SQLException {
    final Connection connMock = mock(Connection.class);
    when(connMock.createStatement()).thenThrow(new SQLException());
    assertDoesNotThrow(
        () -> {
          new UsersStore(connMock);
        });
  }

  @Test
  void createTableTestWhenStatementExecuteFailsShouldNotThrowException() throws SQLException {
    final Connection connMock = mock(Connection.class);
    final Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);

    when(statementMock.execute(UsersStore.CREATE_TABLE_QUERY)).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          new UsersStore(connMock);
        });
  }

  @Test
  void createUserTestWhenNamePassedShouldCreateUser() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
    final UsersStore usersStore = new UsersStore(conn);
    final User userTest = usersStore.createUser(VALID_NAME);
    assertNotNull(userTest);
    conn.close();
  }

  @Test
  void createUserTestWhenPreparedStatementCreationFailsShouldNotThrowSqlException()
      throws SQLException {

    final Connection connMock = mock(Connection.class);
    final Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(UsersStore.CREATE_TABLE_QUERY)).thenReturn(true);

    when(connMock.prepareStatement(UsersStore.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS))
        .thenThrow(new SQLException());

    final UsersStore usersTest = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersTest.createUser(VALID_NAME);
        });
  }

  @Test
  void createUserTestWhenPreparedStatementExecuteSetStringFailsShouldNotThrowSqlException()
      throws SQLException {
    final Connection connMock = mock(Connection.class);
    final Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(anyString())).thenReturn(true);

    final PreparedStatement prep = mock(PreparedStatement.class);

    when(connMock.prepareStatement(UsersStore.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS))
        .thenReturn(prep);

    doThrow(new SQLException()).when(prep).setString(anyInt(), anyString());

    final UsersStore usersTest = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersTest.createUser(VALID_NAME);
        });
  }

  @Test
  void createUserTestWhenPreparedStatementExecuteUpdateFailsShouldNotThrowSqlException()
      throws SQLException {
    final Connection connMock = mock(Connection.class);
    final Statement statementMock = mock(Statement.class);

    when(connMock.createStatement()).thenReturn(statementMock);
    when(statementMock.execute(anyString())).thenReturn(true);

    final PreparedStatement prep = mock(PreparedStatement.class);

    when(connMock.prepareStatement(UsersStore.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS))
        .thenReturn(prep);
    doThrow(new SQLException()).when(prep).executeUpdate();

    final UsersStore usersStore = new UsersStore(connMock);

    assertDoesNotThrow(
        () -> {
          usersStore.createUser(VALID_NAME);
        });
  }

  @Test
  void getGeneratedIdTestWhenGetGeneratedKeysFailsShouldNotThrowException() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
    final UsersStore usersStore = new UsersStore(conn);
    final PreparedStatement prep = mock(PreparedStatement.class);

    when(prep.getGeneratedKeys()).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          usersStore.getGeneratedId(prep);
        });
    conn.close();
  }

  @Test
  void getGeneratedIdTestWhenNextFailsShouldNotThrowException() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
    final UsersStore usersStore = new UsersStore(conn);

    final PreparedStatement prep = mock(PreparedStatement.class);
    final ResultSet resultMock = mock(ResultSet.class);

    when(prep.getGeneratedKeys()).thenReturn(resultMock);
    when(resultMock.next()).thenThrow(new SQLException());

    assertDoesNotThrow(
        () -> {
          usersStore.getGeneratedId(prep);
        });
  }

  @Test
  void getGeneratedIdTestWhenNextIsFalseShouldReturn0() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
    final UsersStore usersStore = new UsersStore(conn);

    final PreparedStatement prep = mock(PreparedStatement.class);
    final ResultSet resultMock = mock(ResultSet.class);

    when(prep.getGeneratedKeys()).thenReturn(resultMock);
    when(resultMock.next()).thenReturn(false);

    assertEquals(0, usersStore.getGeneratedId(prep));
  }

  @Test
  void getConnectionTestWhenFileIsPassedShouldReturnConnection() throws SQLException {
    final Connection conn = UsersStore.getConnection(TEST_PATH);
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
