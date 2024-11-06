/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    Vũ Gia Huy                      NotificationDAO
 */

package org.example.kindergarten_management_system_g4.dao.NotificationDAO;

import org.example.kindergarten_management_system_g4.model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.kindergarten_management_system_g4.connection.DBConnection;

/**
 * Lớp NotificationDAO chứa các phương thức truy cập dữ liệu cho bảng Notification.
 * Các phương thức bao gồm lấy danh sách, thêm, cập nhật, xóa, và tìm kiếm thông báo trong cơ sở dữ liệu.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @see Notification
 */
public class NotificationDAO extends DBConnection {

    /**
     * Lấy tất cả các thông báo từ cơ sở dữ liệu.
     *
     * @return danh sách tất cả thông báo
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public List<Notification> getAllNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification";

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

    /**
     * Lấy một số lượng thông báo giới hạn bằng cách phân trang (offset, limit).
     *
     * @param offset vị trí bắt đầu
     * @param limit số lượng thông báo cần lấy
     * @return danh sách các thông báo với phân trang
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public List<Notification> getNotifications(int offset, int limit) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification LIMIT ?, ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();

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

    /**
     * Lấy tổng số lượng thông báo trong cơ sở dữ liệu.
     *
     * @return tổng số lượng thông báo
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public int getTotalNotifications() throws SQLException {
        int totalNotifications = 0;
        String query = "SELECT COUNT(*) FROM Notification";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                totalNotifications = rs.getInt(1);
            }
        }
        return totalNotifications;
    }

    /**
     * Lấy thông báo cụ thể dựa trên ID.
     *
     * @param notificationId ID của thông báo cần lấy
     * @return đối tượng Notification nếu tìm thấy, ngược lại là null
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public Notification getNotificationById(int notificationId) throws SQLException {
        Notification notification = null;
        String query = "SELECT * FROM Notification WHERE Notification_ID = " + notificationId;

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
        return notification;
    }

    /**
     * Thêm một thông báo mới vào cơ sở dữ liệu.
     *
     * @param notification đối tượng Notification cần thêm
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public void addNotification(Notification notification) throws SQLException {
        if (titleExists(notification.getTitle())) {
            throw new SQLException("Tiêu đề đã tồn tại! Không thể thêm thông báo.");
        }

        String query = "INSERT INTO Notification (title, Content, Date) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, notification.getTitle());
            pstmt.setString(2, notification.getContent());
            pstmt.setDate(3, new java.sql.Date(notification.getDate().getTime()));

            pstmt.executeUpdate();
        }
    }

    /**
     * Kiểm tra xem tiêu đề thông báo có tồn tại trong cơ sở dữ liệu hay không.
     *
     * @param title tiêu đề thông báo cần kiểm tra
     * @return true nếu tiêu đề tồn tại, ngược lại là false
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public boolean titleExists(String title) throws SQLException {
        String query = "SELECT COUNT(*) FROM Notification WHERE title = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Cập nhật thông tin thông báo trong cơ sở dữ liệu.
     *
     * @param notification đối tượng Notification với thông tin cần cập nhật
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public void updateNotification(Notification notification) throws SQLException {
        String query = "UPDATE Notification SET title = ?, Content = ?, Date = ? WHERE Notification_ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, notification.getTitle());
            pstmt.setString(2, notification.getContent());
            pstmt.setDate(3, new java.sql.Date(notification.getDate().getTime()));
            pstmt.setInt(4, notification.getNotificationId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Xóa thông báo khỏi cơ sở dữ liệu dựa trên ID.
     *
     * @param notificationId ID của thông báo cần xóa
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public void deleteNotification(int notificationId) throws SQLException {
        String query = "DELETE FROM Notification WHERE Notification_ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Tìm kiếm thông báo theo tiêu đề.
     *
     * @param title tiêu đề hoặc từ khóa cần tìm kiếm
     * @return danh sách thông báo phù hợp với tiêu đề
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public List<Notification> searchNotificationsByTitle(String title) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification WHERE title LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

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
}
