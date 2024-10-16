package org.example.kindergarten_management_system_g4.dao;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDAO {

    // Lấy thông tin người dùng dựa trên ID người dùng
    public User getUserById(int userId) {
        User user = null;
        // Câu truy vấn SQL để chọn thông tin người dùng theo User_Id
        String sql = "SELECT * FROM user WHERE User_Id = ?";

        // Thiết lập kết nối với cơ sở dữ liệu và chuẩn bị câu lệnh SQL
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
            // Luôn đóng ResultSet sau khi sử dụng
            resultSet.close();
        } catch (SQLException e) {
            // In ra chi tiết lỗi nếu có ngoại lệ SQL
            e.printStackTrace();
        }
        // Trả về đối tượng User với thông tin đã được thiết lập hoặc null nếu không tìm thấy
        return user;
    }

    // Cập nhật thông tin hồ sơ người dùng trong cơ sở dữ liệu
    public boolean updateUserProfile(User user) {
        // Câu truy vấn SQL để cập nhật thông tin người dùng theo User_Id
        String sql = "UPDATE user SET Fullname = ?, Email = ?, gender = ?, phoneNumber = ?, address = ?, image = ? WHERE User_Id = ?";

        // Thiết lập kết nối với cơ sở dữ liệu và chuẩn bị câu lệnh SQL
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
        // Trả về false nếu cập nhật không thành công
        return false;
    }
}


