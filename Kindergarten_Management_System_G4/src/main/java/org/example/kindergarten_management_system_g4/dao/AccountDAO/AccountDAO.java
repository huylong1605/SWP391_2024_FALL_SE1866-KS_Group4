package org.example.kindergarten_management_system_g4.dao.AccountDAO;


import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
import java.util.Base64;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

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


    public boolean isUserExists(int userId) throws SQLException {
        User account = getAccountById(userId);
        return account != null;
    }

    public void createAccount(String fullname, String email, int roleId) throws SQLException {
        String password = generateRandomPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        // Lưu tài khoản vào cơ sở dữ liệu
        String sql = "INSERT INTO user (Fullname, Email, Password, Role_id, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setInt(4, roleId);
            ps.setInt(5, 1); // Trạng thái mặc định là "Active"
            ps.executeUpdate();
        }

        // Gửi email
        sendEmail(email, password);
    }

    private String generateRandomPassword() {
        // Tạo mật khẩu ngẫu nhiên
        int length = 8; // Độ dài mật khẩu
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    private void sendEmail(String recipient, String password) {
        String host = "smtp.gmail.com"; // Địa chỉ máy chủ SMTP
        final String username = "tcnatsu150977@gmail.com"; // Địa chỉ email gửi
        final String passwordSender = "yqxf ijdq ypze lhed"; // Mật khẩu email

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, passwordSender);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Kindergatent-sofware");
            String emailBody = "Your password is: " + password + "\n" +
                    "Welcome! You have become a member of Kindergarten. " +
                    "Click this link to login: http://localhost:8080/Kindergarten_Management_System/Login.jsp";

            message.setText(emailBody);

            Transport.send(message);
            System.out.println("Email đã được gửi thành công đến " + recipient); // Thông báo khi gửi thành công
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi gửi email: " + e.getMessage()); // Thông báo lỗi nếu có
        }

    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE Email = ?"; // Câu truy vấn để kiểm tra email
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email); // Thiết lập giá trị email cho câu truy vấn
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng lớn hơn 0, email đã tồn tại
            }
        }
        return false;
    }


    public static List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE fullname LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getInt("status"));
                user.setRoleId(rs.getInt("roleId"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        try {

            // Thêm đoạn mã kiểm tra email đã tồn tại trước khi tạo tài khoản
            String newFullname = "Call me Natsu"; // Tên người dùng mới
            String newEmail = "robert.anime.vn1@gmail.com"; // Email người dùng mới
            int newRoleId = 4; // Vai trò người dùng mới (thay đổi giá trị này nếu cần)

            // Kiểm tra xem email đã tồn tại chưa
            if (accountDAO.isEmailExists(newEmail)) {
                System.out.println("Email '" + newEmail + "' đã tồn tại. Không thể tạo tài khoản.");
            } else {
                // Nếu email chưa tồn tại, tiến hành tạo tài khoản và gửi email
                accountDAO.createAccount(newFullname, newEmail, newRoleId);
                System.out.println("Tài khoản đã được tạo và email đã được gửi thành công!");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
    }

}

