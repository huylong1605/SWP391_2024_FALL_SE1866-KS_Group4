/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                                      DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140               Create connection for changePassword
 */

package org.example.kindergarten_management_system_g4.dao.profileDAO.Implement;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.AuthenDAO.ForgetPasswordDAO;
import org.example.kindergarten_management_system_g4.dao.profileDAO.IChangePassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp ChangePasswordDAO kế thừa từ DBConnection và thực hiện các phương thức
 * trong interface ChangePasswordService. Lớp này chứa các phương thức
 * để tìm kiếm email, mật khẩu và cập nhật mật khẩu trong cơ sở dữ liệu.
 * @author Nguyễn Huy Long
 */
public class ChangePasswordDAOImpl extends DBConnection implements IChangePassword {

    private static final Logger LOGGER = Logger.getLogger(ForgetPasswordDAO.class.getName());

    // Các câu lệnh SQL sử dụng trong DAO
    private static final String GET_EMAIL = "SELECT email FROM user WHERE email = ?";

    private static final String GET_PASSWORD = "SELECT password FROM user WHERE email=?";
    private static final String UPDATE_PASSWORD = "UPDATE user SET Password = ? WHERE email = ?;";


    /**
     * Tìm kiếm email trong cơ sở dữ liệu.
     * @param email email cần tìm.
     * @return email tìm thấy hoặc chuỗi rỗng nếu không tìm thấy.
     * @throws SQLException nếu có lỗi trong quá trình thực hiện SQL.
     */
    @Override
    public String findEmail(String email) throws SQLException {
        String mail = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                mail = resultSet.getString(1);  // Corrected column index
                LOGGER.log(Level.INFO, "Found email: {0}", email);
            } else {
                LOGGER.log(Level.WARNING, "No email found for: {0}", email);
            }

        } catch (SQLException e) {
            throw new SQLException("Error find Email: " + e.getMessage(), e); // Ném lại ngoại lệ sau khi log chi tiết
        } finally {
            // Đóng tài nguyên theo thứ tự: ResultSet -> PreparedStatement -> Connection
            closeResources(resultSet, preparedStatement, connection);
        }
        return mail;
    }


    /**
     * Tìm kiếm mật khẩu cho bằng email .
     * @param email email cần tìm mật khẩu.
     * @return mật khẩu liên kết với email hoặc chuỗi rỗng nếu không tìm thấy.
     * @throws SQLException nếu có lỗi trong quá trình thực hiện SQL.
     */
    @Override
    public String findPassword(String email) throws SQLException {
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
            throw new SQLException("Error find Password: " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return password;
    }


    /**
     * Cập nhật mật khẩu cho một email cụ thể.
     * @param email email mà mật khẩu sẽ được cập nhật.
     * @param NewPassword mật khẩu mới để thiết lập.
     * @return true nếu mật khẩu được cập nhật thành công; false nếu ngược lại.
     * @throws SQLException nếu có lỗi trong quá trình thực hiện SQL.
     */
    @Override
    public boolean updatePassword(String email, String NewPassword) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);

            preparedStatement.setString(1, NewPassword);
            preparedStatement.setString(2, email);

            LOGGER.log(Level.INFO, "Executing Update Password: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                LOGGER.log(Level.INFO, "User Update successfully: {0}", email);
            }
        } catch (SQLException e) {
            throw new SQLException("Error to update Password: " + e.getMessage(), e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
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
     *
     * @param ex Đối tượng SQLException
     */
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                LOGGER.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                LOGGER.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.log(Level.SEVERE, "Cause: {0}", t);
                    t = t.getCause();
                }
            }
        }
    }
}
