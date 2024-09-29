package org.example.kindergarten_management_system_g4.dao.AccountDAO;


import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<User> getAllAccounts() throws SQLException {
        List<User> accounts = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT User_id, Role_id, Fullname, Email, Status, address from user;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User account = new User();
            account.setUserID(rs.getInt("User_id"));
            account.setRoleId(rs.getInt("Role_id"));
            account.setFullname(rs.getString("Fullname"));
            account.setEmail(rs.getString("Email"));
            account.setStatus(rs.getInt("Status"));
            accounts.add(account);
        }

        return accounts;
    }

    public void toggleAccountStatus(int userId) throws SQLException {
        String sql = "UPDATE user SET Status = CASE WHEN Status = 1 THEN 0 ELSE 1 END WHERE User_id = ?;";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public User getAccountById(int userId) throws SQLException {
        User account = null;
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM user WHERE User_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            account = new User();
            account.setUserID(rs.getInt("User_id"));
            account.setRoleId(rs.getInt("Role_id"));
            account.setFullname(rs.getString("Fullname"));
            account.setEmail(rs.getString("Email"));
            account.setStatus(rs.getInt("Status"));
            account.setAddress(rs.getString("Address")); // Giả sử bạn có trường địa chỉ
            account.setPhoneNumber(rs.getString("PhoneNumber"));
            account.setDateOfBirth(rs.getString("date_Of_birth"));
            account.setPassword(rs.getString("Password"));
            account.setImage(rs.getString("Image"));
            account.setGender(rs.getInt("Gender"));
        }

        return account;
    }

    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();

        // Kiểm tra với một userId bất kỳ
        int userId = 3; // Bạn có thể thay đổi giá trị này để kiểm tra với các userId khác

        try {
            // Kiểm tra phương thức getAccountById()
            User user = accountDAO.getAccountById(userId);
            if (user != null) {
                System.out.println("Người dùng tồn tại với ID: " + userId);
                System.out.println("Thông tin người dùng:");
                System.out.println("Fullname: " + user.getFullname());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Status: " + user.getStatus());
                System.out.println("Fullname: " + user.getPhoneNumber());
                System.out.println("Email: " + user.getDateOfBirth());
                System.out.println("Status: " + user.getRoleId());
            } else {
                System.out.println("Không tìm thấy người dùng với ID: " + userId);
            }

            // Kiểm tra phương thức isUserExists()
            boolean exists = accountDAO.isUserExists(userId);
            if (exists) {
                System.out.println("Người dùng với ID " + userId + " tồn tại.");
            } else {
                System.out.println("Người dùng với ID " + userId + " không tồn tại.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
    }

    public boolean isUserExists(int userId) throws SQLException {
        User account = getAccountById(userId);
        return account != null;
    }

}
