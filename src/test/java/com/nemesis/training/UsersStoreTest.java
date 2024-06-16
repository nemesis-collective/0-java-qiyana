package com.nemesis.training;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersStoreTest {


    @Test
    void createTableTest_shouldCreateTable() throws SQLException {
    String createTableSQL =
            "CREATE TABLE IF NOT EXISTS USERS ("
                    + "id LONG AUTO_INCREMENT, "
                    + "name VARCHAR(255) NOT NULL)";
        Connection connMock = mock(Connection.class);
        Statement statementMock = mock(Statement.class);
        when(connMock.createStatement()).thenReturn(statementMock);
        when(statementMock.execute(createTableSQL)).thenReturn(true);
        new UsersStore(connMock);
        verify(statementMock).execute(createTableSQL);
    }


//    @Test
//    void addUserTest_shouldCreate() throws SQLException {
//        String insertSQL = "INSERT INTO USERS (name) VALUES (?)";
//        String name = "joaopaulo";
//        Connection connMock = mock(Connection.class);
//        PreparedStatement statementMock = mock(PreparedStatement.class);
//
//        when(connMock.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)).thenReturn(statementMock);
//
//        when(statementMock.setString(1,any())).thenThrow(new SQLException());
//
//
//        new UsersStore(connMock);
//        verify(statementMock).execute(insertSQL);
//    }
    @Test
    void addUserTest_shouldAddUser() throws SQLException, IOException {
        String nome = "joaopaulo";
        Connection conn = UsersStore.getConnection("application.properties");
        UsersStore usersStore = new UsersStore(conn);
        usersStore.addUser(nome);
    }

//    @Test
//    public void getConnectionTest_shouldReturnSQLException() throws SQLException, IOException {
//
//        UsersStore usersStoreMock = mock(UsersStore.class);
//        // Connection conn = mock(Connection.class);
//
//        when(usersStoreMock.getConnection()).thenThrow(new SQLException());
//
//        SQLException exception = assertThrows(SQLException.class, () -> {
//            UsersStore.getConnection();
//        });
//
//        assertEquals("An error occurred while connecting to database.", exception.getMessage());
//
//    }

    @Test
    public void getConnectionTest_shouldReturnConnection() throws SQLException, IOException {
        Connection conn = UsersStore.getConnection("application.properties");
        assertNotNull(conn);
        conn.close();
    }

}


