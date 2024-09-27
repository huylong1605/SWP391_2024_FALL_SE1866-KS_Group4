package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {


    private static final String Check_Login = "SELECT * FROM user WHERE Email=?";
    private static final String PASS = "SELECT password FROM user WHERE Email = ?";


    public User getUser(String Email) throws ClassNotFoundException {

        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(Check_Login)) {

            preparedStatement.setString(1, Email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                User user = new User();
                user.setUserID(resultSet.getInt("User_id"));
                user.setFullname(resultSet.getString("Fullname"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                user.setRoleId(resultSet.getInt("Role_id"));
                return user;
            }
            System.out.println("Executing query: " + preparedStatement);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public String Password(String email) throws ClassNotFoundException {

        String password ="";
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(PASS)) {


            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString(1);  // Corrected column index
            }



        } catch (SQLException e) {
            printSQLException(e);
        }
        return password;
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
