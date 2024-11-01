/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/5/2024       1.1              Đào Xuân Bình - HE163115              Create UserProfileDAO class
 */

package org.example.kindergarten_management_system_g4.dao;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp UserProfileDAO chịu trách nhiệm tương tác với cơ sở dữ liệu để thực hiện các thao tác liên quan đến hồ sơ người dùng.
 * Các phương thức bao gồm lấy thông tin người dùng theo ID và cập nhật hồ sơ người dùng.
 * <p>Lỗi: Chưa phát hiện lỗi.
 */
public class UserProfileDAO {

    /**
     * Phương thức lấy thông tin người dùng từ cơ sở dữ liệu dựa trên ID người dùng.
     *
     * @param userId ID của người dùng cần lấy thông tin.
     * @return đối tượng User chứa thông tin người dùng, hoặc null nếu không tìm thấy.
     */
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM user WHERE User_Id = ?"; // Câu truy vấn SQL để lấy thông tin người dùng

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Thiết lập tham số userId cho câu truy vấn
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Nếu tìm thấy người dùng khớp, thiết lập các thông tin cho đối tượng User
            if (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt("User_id"));
                user.setFullname(resultSet.getString("Fullname"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getInt("gender"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAddress(resultSet.getString("address"));
                user.setImage(resultSet.getString("image"));
            }
            resultSet.close(); // Luôn đóng ResultSet sau khi sử dụng

        } catch (SQLException e) {
            // In ra chi tiết lỗi nếu có ngoại lệ SQL
            e.printStackTrace();
        }

        return user; // Trả về đối tượng User hoặc null nếu không tìm thấy
    }

    /**
     * Phương thức cập nhật thông tin hồ sơ người dùng trong cơ sở dữ liệu.
     *
     * @param user đối tượng User chứa thông tin đã được cập nhật.
     * @return true nếu cập nhật thành công, false nếu cập nhật thất bại.
     */
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE user SET Fullname = ?, Email = ?, gender = ?, phoneNumber = ?, address = ?, image = ? WHERE User_Id = ?"; // Câu truy vấn SQL để cập nhật thông tin người dùng

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Thiết lập các thuộc tính của đối tượng User vào câu lệnh SQL
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getGender());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getImage());
            preparedStatement.setInt(7, user.getUserID());

            // Thực thi câu lệnh cập nhật và trả về true nếu ít nhất một bản ghi đã được cập nhật
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            // In ra chi tiết lỗi nếu có ngoại lệ SQL
            e.printStackTrace();
        }

        return false; // Trả về false nếu cập nhật không thành công
    }

    public List<User> getAllUserParent() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_id = 3"; // Giả định rằng có một cột 'role' chỉ định vai trò người dùng là 'parent'

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Lặp qua các kết quả và thêm vào danh sách người dùng
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("User_id"));
                user.setFullname(resultSet.getString("Fullname"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getInt("gender"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAddress(resultSet.getString("address"));
                user.setImage(resultSet.getString("image"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }

        return users; // Trả về danh sách người dùng
    }
}
