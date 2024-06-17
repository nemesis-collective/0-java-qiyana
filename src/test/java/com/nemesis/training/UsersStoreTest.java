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

    @Test
    void addUserTest_shouldAddUser() throws SQLException, IOException {
        String name = "joaopaulo";
        Connection conn = UsersStore.getConnection("application.properties");
        UsersStore usersStore = new UsersStore(conn);
        usersStore.createUser(name);
    }

    @Test
    public void getConnectionTest_shouldReturnConnection() throws SQLException, IOException {
        Connection conn = UsersStore.getConnection("application.properties");
        assertNotNull(conn);
        conn.close();
    }

}


