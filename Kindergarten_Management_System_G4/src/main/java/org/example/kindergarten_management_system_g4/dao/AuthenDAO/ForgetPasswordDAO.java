package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class ForgetPasswordDAO {

    private static final String EMAIL = "SELECT email FROM user WHERE email = ?";
    private static final String InsertCode = "Update user set Code = ? WHERE email = ?";
    private static final String SelectCode = "SELECT code FROM user WHERE email = ?";
    private static final String UPDATE_PASS_USER = "UPDATE user SET password = ? WHERE email = ?";

    public String FindMail(String email) throws ClassNotFoundException {

        String Mail ="";
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(EMAIL)) {


            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Mail = resultSet.getString(1);  // Corrected column index
            }


        } catch (SQLException e) {
           printSQLException(e);
        }
        return Mail;
    }
    public int InsertCode(String code ,String email) throws ClassNotFoundException {
        int result = 0;
        String Mail ="";
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(InsertCode)) {


            preparedStatement.setString(1, code);
            preparedStatement.setString(2, email);

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public String FindCode(String email) throws ClassNotFoundException {

        String code ="";
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SelectCode)) {


            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                code = resultSet.getString(1);  // Corrected column index
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return code;
    }

    public int newPass(String email, String newPass) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASS_USER)) {


            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
