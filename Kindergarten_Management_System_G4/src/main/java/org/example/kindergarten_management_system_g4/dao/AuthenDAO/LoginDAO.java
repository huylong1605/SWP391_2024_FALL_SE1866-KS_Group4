package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO extends DBConnection {  // Kế thừa từ DBConnection

    private static final Logger logger = Logger.getLogger(LoginDAO.class.getName());
    private static final String CHECK_LOGIN = "SELECT * FROM user WHERE Email=?";
    private static final String GET_PASSWORD = "SELECT password FROM user WHERE Email=?";

    public User getUser(String email) throws ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();  // Sử dụng phương thức từ lớp cha
            logger.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(CHECK_LOGIN);
            preparedStatement.setString(1, email);
            logger.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("User_id"));
                user.setFullname(resultSet.getString("Fullname"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                user.setRoleId(resultSet.getInt("Role_id"));
                logger.log(Level.INFO, "User found: {0}", user);
                return user;
            }
            logger.log(Level.WARNING, "No user found with email: {0}", email);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return null;
    }

    public String getPassword(String email) throws ClassNotFoundException {
        String password = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();  // Sử dụng phương thức từ lớp cha
            logger.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(GET_PASSWORD);
            preparedStatement.setString(1, email);
            logger.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString(1);  // Corrected column index
                logger.log(Level.INFO, "Password retrieved for email: {0}", email);
            } else {
                logger.log(Level.WARNING, "No password found for email: {0}", email);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return password;
    }

    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        // Đóng ResultSet
        try {
            if (resultSet != null) {
                resultSet.close();
                logger.log(Level.INFO, "ResultSet closed");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing ResultSet", e);
            printSQLException(e);
        }

        // Đóng PreparedStatement
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
                logger.log(Level.INFO, "PreparedStatement closed");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing PreparedStatement", e);
            printSQLException(e);
        }

        // Đóng Connection
        try {
            if (connection != null) {
                connection.close();
                logger.log(Level.INFO, "Connection closed");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing Connection", e);
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                logger.log(Level.SEVERE, "SQLState: " + ((SQLException) e).getSQLState());
                logger.log(Level.SEVERE, "Error Code: " + ((SQLException) e).getErrorCode());
                logger.log(Level.SEVERE, "Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    logger.log(Level.SEVERE, "Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
