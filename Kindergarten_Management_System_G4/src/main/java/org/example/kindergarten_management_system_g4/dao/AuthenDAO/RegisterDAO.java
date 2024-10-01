package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterDAO extends DBConnection {
    private static final Logger logger = Logger.getLogger(RegisterDAO.class.getName());
    private static final String CHECK_PHONE = "SELECT * FROM user WHERE phoneNumber=?";
    private static final String CHECK_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String INSERT_USER = "INSERT INTO user(Fullname, email, password, gender, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_LOGIN_GG = "INSERT INTO user(Fullname, email) VALUES (?, ?)";

    public boolean checkPhone(String phone) throws ClassNotFoundException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CHECK_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();

            exists = resultSet.next(); // Kiểm tra xem có bản ghi nào không
            logger.log(Level.INFO, "Check phone: {0}, exists: {1}", new Object[]{phone, exists});
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while checking phone", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return exists; // Trả về kết quả kiểm tra
    }

    public boolean checkEmail(String email) throws ClassNotFoundException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CHECK_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            exists = resultSet.next(); // Kiểm tra xem có bản ghi nào không
            logger.log(Level.INFO, "Check email: {0}, exists: {1}", new Object[]{email, exists});
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while checking email", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return exists; // Trả về kết quả kiểm tra
    }

    public boolean insertUser(User u) throws ClassNotFoundException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, u.getFullname());
            preparedStatement.setString(2, u.getEmail());
            preparedStatement.setString(3, u.getPassword());
            preparedStatement.setInt(4, u.getGender());
            preparedStatement.setString(5, u.getPhoneNumber());
            preparedStatement.setString(6, u.getAddress());

            logger.log(Level.INFO, "Executing insert user: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                logger.log(Level.INFO, "User inserted successfully: {0}", u.getEmail());
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while inserting user", e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
    }

    public boolean insertUserLoginGG(String mail, String name) throws ClassNotFoundException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER_LOGIN_GG);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, mail); // Sửa lỗi từ name thành mail

            logger.log(Level.INFO, "Executing insert user for Google login: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                logger.log(Level.INFO, "User inserted successfully with Google login: {0}", mail);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while inserting user for Google login", e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
    }

    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        // Đóng ResultSet
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while closing ResultSet", e);
        }

        // Đóng PreparedStatement
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while closing PreparedStatement", e);
        }

        // Đóng Connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception while closing Connection", e);
        }
    }


}
