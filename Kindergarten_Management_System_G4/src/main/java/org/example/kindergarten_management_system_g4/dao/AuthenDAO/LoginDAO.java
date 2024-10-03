/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140             Update Logger
 */

package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kế thừa từ lớp DBConection
 * sử lý  rồi đưa các nghiệp vụ của chức năng đăng nhập ở lớp LoginController
 * @author Nguyễn Huy Long
 */
public class LoginDAO extends DBConnection {  // Kế thừa từ DBConnection

    private static final Logger LOGGER = Logger.getLogger(LoginDAO.class.getName());
    private static final String CHECK_LOGIN = "SELECT * FROM user WHERE Email=?";
    private static final String GET_PASSWORD = "SELECT password FROM user WHERE Email=?";

    /**
     * Lấy đối tượng người dùng thông qua hàm getUser
     * @param email Email của người dùng cần lấy thông tin
     * @return Đối tượng User nếu tìm thấy, ngược lại trả về null
     * @throws ClassNotFoundException Nếu không tìm thấy lớp cơ sở dữ liệu
     */
    public User getUser(String email) throws ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();  // Sử dụng phương thức từ lớp cha để kết nối đến cơ sở dữ liệu
            LOGGER.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(CHECK_LOGIN); //Truy vấn SQL để tìm người dùng theo email
            preparedStatement.setString(1, email);
            LOGGER.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery(); // Thực thi truy vấn và lưu kết quả vào resultSet

            // Nếu tìm thấy người dùng, khởi tạo và trả về đối tượng User
            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("User_id"));
                user.setFullname(resultSet.getString("Fullname"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                user.setRoleId(resultSet.getInt("Role_id"));
                LOGGER.log(Level.INFO, "User found: {0}", user);
                return user;  // Trả về đối tượng User
            }

            // Ghi log cảnh báo nếu không tìm thấy người dùng với email đã cho
            LOGGER.log(Level.WARNING, "No user found with email: {0}", email);
        } catch (SQLException e) {

            // Ghi log khi có ngoại lệ SQL và xử lý lỗi
            LOGGER.log(Level.SEVERE, "SQL Exception occurred", e);
            printSQLException(e);
        } finally {

            //Đóng kết nối quá hàm closeResources
            closeResources(resultSet, preparedStatement, connection);
        }

        return null; // Trả về null nếu không tìm thấy người dùng
    }

    /**
     * Lấy mật khẩu của người dùng thông qua email
     * @param email Email của người dùng
     * @return Mật khẩu của người dùng nếu tìm thấy, ngược lại trả về chuỗi rỗng
     * @throws ClassNotFoundException Nếu không tìm thấy lớp cơ sở dữ liệu
     */
    public String getPassword(String email) throws ClassNotFoundException {
        String password = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(GET_PASSWORD);
            preparedStatement.setString(1, email);
            LOGGER.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString(1);  // Lưu Password ở đây
                LOGGER.log(Level.INFO, "Password retrieved for email: {0}", email);  // Ghi log khi lấy được Password
            } else {
                LOGGER.log(Level.WARNING, "No password found for email: {0}", email); // Ghi log không tìm thấy Password
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return password;
    }



    /**
     * Đóng các tài nguyên cơ sở dữ liệu (ResultSet, PreparedStatement, Connection)
     * @param resultSet Đối tượng ResultSet cần đóng
     * @param preparedStatement Đối tượng PreparedStatement cần đóng
     * @param connection Đối tượng Connection cần đóng
     */
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        // Đóng ResultSet
        try {
            if (resultSet != null) {
                resultSet.close();
                LOGGER.log(Level.INFO, "ResultSet closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing ResultSet", e);
            printSQLException(e);
        }

        // Đóng PreparedStatement
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
                LOGGER.log(Level.INFO, "PreparedStatement closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing PreparedStatement", e);
            printSQLException(e);
        }

        // Đóng Connection
        try {
            if (connection != null) {
                connection.close();
                LOGGER.log(Level.INFO, "Connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing Connection", e);
            printSQLException(e);
        }
    }

    /**
     * Ghi lại thông tin lỗi SQL
     * @param ex Đối tượng SQLException
     */
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.log(Level.SEVERE, "SQLState: " + ((SQLException) e).getSQLState());
                LOGGER.log(Level.SEVERE, "Error Code: " + ((SQLException) e).getErrorCode());
                LOGGER.log(Level.SEVERE, "Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.log(Level.SEVERE, "Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
