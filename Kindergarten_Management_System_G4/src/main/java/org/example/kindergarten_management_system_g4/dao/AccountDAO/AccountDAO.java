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
            account.setAddress(rs.getString("Address"));
            account.setPhoneNumber(rs.getString("PhoneNumber"));
            account.setDateOfBirth(rs.getString("date_Of_birth"));
            account.setPassword(rs.getString("Password"));
            account.setImage(rs.getString("Image"));
            account.setGender(rs.getInt("Gender"));
        }

        return account;
    }

    public void createAccount(String fullname, String email, int roleId) throws SQLException {
        String password = generateRandomPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        String sql = "INSERT INTO user (Fullname, Email, Password, Role_id, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setInt(4, roleId);
            ps.setInt(5, 1);
            ps.executeUpdate();
        }


        sendEmail(email, password);
    }

    private String generateRandomPassword() {

        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    private void sendEmail(String recipient, String password) {
        String host = "smtp.gmail.com";
        final String username = "tcnatsu150977@gmail.com";
        final String passwordSender = "yqxf ijdq ypze lhed";

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
            System.out.println("Email đã được gửi thành công đến " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi gửi email: " + e.getMessage());
        }

    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE Email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }


    public int getAccountCount(String searchName, Integer roleId) throws SQLException {

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM kindergartenmanagementsystem.user WHERE 1=1");

        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND Fullname LIKE ?");
        }

        if (roleId != null) {
            sql.append(" AND Role_id = ?");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int parameterIndex = 1;

            if (searchName != null && !searchName.trim().isEmpty()) {
                stmt.setString(parameterIndex++, "%" + searchName + "%");
            }

            if (roleId != null) {
                stmt.setInt(parameterIndex++, roleId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }


    public List<User> getAccounts(int currentPage, int pageSize, String searchName, Integer roleId) throws SQLException {
        List<User> accounts = new ArrayList<>();
        int offset = (currentPage - 1) * pageSize;


        StringBuilder sql = new StringBuilder("SELECT * FROM kindergartenmanagementsystem.user WHERE 1=1");


        if (searchName != null && !searchName.isEmpty()) {
            sql.append(" AND fullname LIKE ?");
        }


        if (roleId != null) {
            sql.append(" AND role_id = ?");
        }


        sql.append(" LIMIT ?, ?");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;


            if (searchName != null && !searchName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchName + "%");
            }


            if (roleId != null) {
                stmt.setInt(paramIndex++, roleId);
            }


            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User account = new User();

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

                    accounts.add(account);
                }
            }
        }

        return accounts;
    }


    public static void main(String[] args) {
        try {

            AccountDAO accountDAO = new AccountDAO();

            int currentPage = 1;
            int pageSize = 7;
            String searchName = "";
            Integer roleId = 2;

            int accountCount = accountDAO.getAccountCount(searchName, roleId);
            System.out.println("Số lượng tài khoản: " + accountCount);

            List<User> accounts = accountDAO.getAccounts(currentPage, pageSize, searchName, roleId);
            System.out.println("Danh sách tài khoản:");

            if (accounts.isEmpty()) {
                System.out.println("Không tìm thấy tài khoản nào phù hợp.");
            } else {
                for (User user : accounts) {
                    System.out.println(user.toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }



}

