/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    vu gia huy                     NotificationDao
 */
package org.example.kindergarten_management_system_g4.dao.NotificationDAO;

import org.example.kindergarten_management_system_g4.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Room;

public class NotificationDAO extends DBConnection {

    // Lấy tất cả các thông báo từ cơ sở dữ liệu
    public List<Notification> getAllNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification";

        // Mở kết nối và thực thi câu lệnh SQL
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Duyệt qua ResultSet và thêm các thông báo vào danh sách
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("Notification_ID"),
                        rs.getString("title"),
                        rs.getString("Content"),
                        rs.getDate("Date"),
                        rs.getInt("User_id")
                );
                notifications.add(notification);
            }
        }
        return notifications;
    }

    // Lấy một số lượng thông báo giới hạn bằng cách phân trang (offset, limit)
    public List<Notification> getNotifications(int offset, int limit) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification LIMIT ?, ?";

        // Mở kết nối và thực thi câu lệnh SQL với tham số phân trang
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();

            // Duyệt qua ResultSet và thêm các thông báo vào danh sách
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("Notification_ID"),
                        rs.getString("title"),
                        rs.getString("Content"),
                        rs.getDate("Date"),
                        rs.getInt("User_id")
                );
                notifications.add(notification);
            }
        }
        return notifications;  // Trả về danh sách thông báo
    }

    // Lấy tổng số lượng thông báo trong cơ sở dữ liệu
    public int getTotalNotifications() throws SQLException {
        int totalNotifications = 0;
        String query = "SELECT COUNT(*) FROM Notification";

        // Thực thi câu lệnh SQL để đếm tổng số thông báo
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                totalNotifications = rs.getInt(1);  // Lấy tổng số từ câu truy vấn
            }
        }
        return totalNotifications;  // Trả về tổng số thông báo
    }

    // Lấy thông báo cụ thể dựa trên ID
    public Notification getNotificationById(int notificationId) throws SQLException {
        Notification notification = null;
        String query = "SELECT * FROM Notification WHERE Notification_ID = " + notificationId;

        // Mở kết nối và thực thi câu truy vấn dựa trên ID của thông báo
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                notification = new Notification(
                        rs.getInt("Notification_ID"),
                        rs.getString("title"),
                        rs.getString("Content"),
                        rs.getDate("Date"),
                        rs.getInt("User_id")
                );
            }
        }
        return notification;  // Trả về thông báo theo ID
    }

    // Thêm một thông báo mới vào cơ sở dữ liệu
    public void addNotification(Notification notification) throws SQLException {
        // Kiểm tra xem tiêu đề đã tồn tại trong cơ sở dữ liệu chưa
        if (titleExists(notification.getTitle())) {
            throw new SQLException("Tiêu đề đã tồn tại! Không thể thêm thông báo.");  // Ném lỗi nếu tiêu đề đã tồn tại
        }

        String query = "INSERT INTO Notification (title, Content, Date) VALUES (?, ?, ?)";
        // Mở kết nối và chuẩn bị câu lệnh SQL để thêm thông báo
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, notification.getTitle());
            pstmt.setString(2, notification.getContent());
            pstmt.setDate(3, new java.sql.Date(notification.getDate().getTime()));  // Thiết lập ngày

            pstmt.executeUpdate();  // Thực thi câu lệnh thêm thông báo
        }
    }

    // Kiểm tra xem tiêu đề thông báo có tồn tại trong cơ sở dữ liệu hay không
    public boolean titleExists(String title) throws SQLException {
        String query = "SELECT COUNT(*) FROM Notification WHERE title = ?";
        // Mở kết nối và chuẩn bị câu truy vấn để kiểm tra tiêu đề
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, title);  // Thiết lập tiêu đề
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất một dòng, tiêu đề đã tồn tại
            }
        }
        return false;  // Trả về false nếu tiêu đề chưa tồn tại
    }

    // Cập nhật thông tin thông báo
    public void updateNotification(Notification notification) throws SQLException {
        String query = "UPDATE Notification SET title = ?, Content = ?, Date = ? WHERE Notification_ID = ?";
        // Mở kết nối và chuẩn bị câu truy vấn để cập nhật thông báo
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, notification.getTitle());
            pstmt.setString(2, notification.getContent());
            pstmt.setDate(3, new java.sql.Date(notification.getDate().getTime()));
            pstmt.setInt(4, notification.getNotificationId());

            pstmt.executeUpdate();
        }
    }

    // Xóa thông báo khỏi cơ sở dữ liệu dựa trên ID
    public void deleteNotification(int notificationId) throws SQLException {
        String query = "DELETE FROM Notification WHERE Notification_ID = ?";
        // Mở kết nối và chuẩn bị câu truy vấn để xóa thông báo
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        }
    }
    // Tìm kiếm thông báo theo tiêu đề
    public List<Notification> searchNotificationsByTitle(String title) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification WHERE title LIKE ?";  // Sử dụng LIKE để tìm kiếm

        // Mở kết nối và chuẩn bị câu truy vấn để tìm kiếm thông báo
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, "%" + title + "%");  // Thêm ký tự % để tìm kiếm chứa chuỗi title
            ResultSet rs = pstmt.executeQuery();

            // Duyệt qua ResultSet và thêm các thông báo vào danh sách
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("Notification_ID"),
                        rs.getString("title"),
                        rs.getString("Content"),
                        rs.getDate("Date"),
                        rs.getInt("User_id")
                );
                notifications.add(notification);
            }
        }
        return notifications;  // Trả về danh sách thông báo tìm kiếm được
    }

}
