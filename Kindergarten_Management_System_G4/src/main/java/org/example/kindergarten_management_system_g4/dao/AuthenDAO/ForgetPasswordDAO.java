package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForgetPasswordDAO extends DBConnection {

    private static final Logger logger = Logger.getLogger(ForgetPasswordDAO.class.getName());
    private static final String GET_EMAIL = "SELECT email FROM user WHERE email = ?";
    private static final String INSERT_CODE = "Update user set Code = ? WHERE email = ?";
    private static final String GET_CODE = "SELECT code FROM user WHERE email = ?";
    private static final String UPDATE_PASS_USER = "UPDATE user SET password = ? WHERE email = ?";

    public String findMail(String email) throws ClassNotFoundException {

        String mail = "";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                mail = resultSet.getString(1);  // Corrected column index
                logger.log(Level.INFO, "Found email: {0}", email);
            } else {
                logger.log(Level.WARNING, "No email found for: {0}", email);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred while finding email", e);
            printSQLException(e);
        }
        return mail;
    }

    public int insertCode(String code, String email) throws ClassNotFoundException {
        int result = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CODE)) {

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, email);
            logger.log(Level.INFO, "Inserting code for email: {0}", email);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred while inserting code for email: " + email, e);
            printSQLException(e);
        }
        return result; // Trả về số bản ghi được cập nhật
    }

    public String findCode(String email) throws ClassNotFoundException {
        String code = "";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CODE)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                code = resultSet.getString(1);  // Corrected column index
                logger.log(Level.INFO, "Found code for email: {0}", email);
            } else {
                logger.log(Level.WARNING, "No code found for email: {0}", email);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred while finding code for email: " + email, e);
            printSQLException(e);
        }
        return code;
    }

    public int newPass(String email, String newPass) throws ClassNotFoundException {
        int result = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASS_USER)) {

            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);
            logger.log(Level.INFO, "Updating password for email: {0}", email);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred while updating password for email: " + email, e);
            printSQLException(e);
        }
        return result;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                logger.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                logger.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                logger.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                Throwable t = ex.getCause();
                while (t != null) {
                    logger.log(Level.SEVERE, "Cause: {0}", t);
                    t = t.getCause();
                }
            }
        }
    }
}
