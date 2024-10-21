
/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <10/2/2024>                 <1.1>           <Vu Viet Chuc>            <Update getAccount method>
 */

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
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;


/**
 * Lớp AccountDAO xử lý các thao tác liên quan đến tài khoản người dùng.
 * Bao gồm các phương thức tạo tài khoản, lấy thông tin tài khoản,
 * cập nhật trạng thái tài khoản, gửi email và kiểm tra sự tồn tại của email.
 */

public class AccountDAO extends DBConnection{

    /**
     * Lấy danh sách tài khoản với phân trang, tìm kiếm và lọc theo vai trò.
     *
     * @param currentPage Số trang hiện tại.
     * @param pageSize Kích thước trang (số lượng tài khoản trên mỗi trang).
     * @param searchName Tên để tìm kiếm tài khoản (nếu có).
     * @param roleId ID của vai trò để lọc tài khoản (nếu có).
     * @return Danh sách tài khoản thỏa mãn điều kiện tìm kiếm và lọc.
     * @throws SQLException Nếu có lỗi trong quá trình truy vấn cơ sở dữ liệu.
     */

    // Lay danh sach account phan trang, tim kiem va loc account
    public List<User> getAccounts(int currentPage, int pageSize, String searchName, Integer roleId) throws SQLException {
        List<User> accounts = new ArrayList<>();
        int offset = (currentPage - 1) * pageSize;

        StringBuilder sql = new StringBuilder("SELECT * FROM kindergartenmanagementsystem.user WHERE 1=1");

        // Them dieu kien tim kiem theo ten
        if (searchName != null && !searchName.isEmpty()) {
            sql.append(" AND fullname LIKE ?");
        }

        // Them dieu kien loc theo roleId
        if (roleId != null) {
            sql.append(" AND role_id = ?");
        }

        // Them dieu kien LIMIT cho phan trang
        sql.append(" LIMIT ?, ?");

        // ket noi voi data va thuc hien truy van
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Thiet lap gia tri cho tham so tim kiem theo ten
            if (searchName != null && !searchName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchName + "%");
            }

            // Thiet lap gia tri cho tham so roleId
            if (roleId != null) {
                stmt.setInt(paramIndex++, roleId);
            }

            // Thiet lap gia tri cho offset va pageSize
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex++, pageSize);

            // Thuc hien truy van va xu ly ket qua
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User account = new User();
                    // Lay thong tin tu ResultSet va gan cho doi tuong User
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
                    accounts.add(account);
                }
            }
        }

        return accounts;
    }


    /**
     * Thay đổi trạng thái của tài khoản
     *
     * @param userId ID của tài khoản người dùng cần thay đổi trạng thái.
     * @throws SQLException Nếu có lỗi trong quá trình cập nhật cơ sở dữ liệu.
     */

    // Thay doi status account
    public void toggleAccountStatus(int userId) throws SQLException {
        String sql = "UPDATE user SET Status = CASE WHEN Status = 1 THEN 0 ELSE 1 END WHERE User_id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId); // Truyen tham so ID nguoi dung
            ps.executeUpdate(); // Thuc thi cap nhat trang thai
        }
    }


    /**
     * Lấy thông tin chi tiết của tài khoản theo ID.
     *
     * @param userId ID của tài khoản cần lấy thông tin.
     * @return Đối tượng User chứa thông tin của tài khoản, hoặc null nếu không tìm thấy.
     * @throws SQLException Nếu có lỗi trong quá trình truy vấn cơ sở dữ liệu.
     */

    // Lay thong tin chi tiet account theo ID
    public User getAccountById(int userId) throws SQLException {
        User account = null;
        Connection connection = getConnection();
        // lay du lieu account theo ID
        String sql = "SELECT * FROM user WHERE User_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        // gan cac gia tri cho doi tuong User
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

    /**
     * Tạo tài khoản mới với thông tin cung cấp.
     *
     * @param fullname Họ và tên của người dùng.
     * @param email Địa chỉ email của người dùng.
     * @param roleId ID của role cho tài khoản mới.
     * @throws SQLException Nếu có lỗi trong quá trình thêm tài khoản vào cơ sở dữ liệu.
     */
    // Tao tai khoan moi
    public void createAccount(String fullname, String email, int roleId) throws SQLException {
        // Tao mat khau ngau nhien cho tai khoan
        String password = generateRandomPassword();
        // Ma hoa mat khau
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        // Them tai khoan moi vao co so du lieu
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

        // Gui email thong bao mat khau cho nguoi dung
        sendEmail(email, password);
    }

    /**
     * Tạo một mật khẩu ngẫu nhiên có độ dài 8 ký tự, bao gồm chữ cái hoa, chữ cái thường và số.
     *
     * @return Mật khẩu ngẫu nhiên được tạo ra dưới dạng chuỗi.
     */

    // Tao mat khau ngau nhien
    private String generateRandomPassword() {
        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(); //tao chuoi mat khau
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length()))); // Tao ky tu ngau nhien
        }
        return password.toString();
    }

    /**
     * Gửi email chứa mật khẩu cho người dùng.
     *
     * @param recipient Địa chỉ email của người nhận.
     * @param password Mật khẩu cần gửi đến người nhận.
     * @throws MessagingException Nếu có lỗi trong quá trình gửi email.
     */
    // Gui email chua mat khau cho nguoi dung
    private void sendEmail(String recipient, String password) {
        String host = "smtp.gmail.com";
        final String username = "tcnatsu150977@gmail.com";
        final String passwordSender = "yqxf ijdq ypze lhed";

        // Cau hinh cac thuoc tinh SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Tao phien SMTP voi xac thuc
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, passwordSender);
                    }
                });
        try {
            // Tao doi tuong Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Dia chi gui
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // Nguoi nhan
            message.setSubject("Kindergatent-sofware");
            // Noi dung email
            String emailBody = "Your password is: " + password + "\n" +
                    "Welcome! You have become a member of Kindergarten. " +
                    "Click this link to login: http://localhost:8080/Kindergarten_Management_System/Login.jsp";

            message.setText(emailBody); // Noi dung tin nhan

            // Gui email
            Transport.send(message);
            System.out.println("Email da duoc gui thanh cong den " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Loi khi gui email: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra xem địa chỉ email có tồn tại trong cơ sở dữ liệu hay không.
     *
     * @param email Địa chỉ email cần kiểm tra.
     * @return true nếu email tồn tại, false nếu không.
     * @throws SQLException Nếu có lỗi trong quá trình truy vấn cơ sở dữ liệu.
     */
    // Kiem tra xem email co ton tai trong data khong
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE Email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email); // Truyen email can kiem tra
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Tra ve true neu email ton tai
            }
        }
        return false; // Tra ve false neu khong tim thay email
    }

    /**
     * Đếm số lượng tài khoản thỏa mãn điều kiện tìm kiếm và lọc.
     *
     * @param searchName Tên để tìm kiếm tài khoản (nếu có).
     * @param roleId ID của vai trò để lọc tài khoản (nếu có).
     * @return Số lượng tài khoản thỏa mãn điều kiện.
     * @throws SQLException Nếu có lỗi trong quá trình truy vấn cơ sở dữ liệu.
     */

    // Dem so account
    public int getAccountCount(String searchName, Integer roleId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM kindergartenmanagementsystem.user WHERE 1=1");

        // Neu co tim kiem theo ten, them dieu kien tim kiem
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND Fullname LIKE ?");
        }

        // Neu co vai tro, them dieu kien loc theo vai tro
        if (roleId != null) {
            sql.append(" AND Role_id = ?");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int parameterIndex = 1;

            // Gan tham so tim kiem theo ten
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(parameterIndex++, "%" + searchName + "%");
            }

            // Gan tham so tim kiem theo vai tro
            if (roleId != null) {
                ps.setInt(parameterIndex++, roleId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Tra ve ket qua dem
            }
        }

        return 0; // Tra ve 0 neu khong co tai khoan nao phu hop
    }


    public static void main(String[] args) {
        try {
            AccountDAO accountDAO = new AccountDAO();
            // Khoi tao thong tin phan trang va tim kiem
            int currentPage = 1;
            int pageSize = 7;
            String searchName = "";
            Integer roleId = 2;

            int accountCount = accountDAO.getAccountCount(searchName, roleId);
            System.out.println("Account count: " + accountCount);
            List<User> accounts = accountDAO.getAccounts(currentPage, pageSize, searchName, roleId);
            for (User account : accounts) {
                System.out.println("User: " + account.getFullname());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

