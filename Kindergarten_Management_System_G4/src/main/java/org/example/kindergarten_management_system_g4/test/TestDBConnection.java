package org.example.kindergarten_management_system_g4.test;

import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection extends DBConnection {
    private static Connection mockConnection;

    public static void setMockConnection(Connection connection) {
        mockConnection = connection;
    }

    public static Connection getConnection() throws SQLException {
        if (mockConnection != null) {
            return mockConnection;
        }
        return DBConnection.getConnection(); // dùng connection thật nếu không có mock
    }
}