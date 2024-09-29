package org.example.kindergarten_management_system_g4.dao;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.kindergarten_management_system_g4.connection.DBConnection.getConnection;

public class NotificationDAO {

    public List<Notification> getAllNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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

    public void addNotification(Notification notification) throws SQLException {
        // Kiểm tra xem tiêu đề đã tồn tại chưa
        if (titleExists(notification.getTitle())) {
            throw new SQLException("Title already exists! Cannot add notification.");
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

    public boolean titleExists(String title) throws SQLException {
        String query = "SELECT COUNT(*) FROM Notification WHERE title = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất một dòng, tiêu đề đã tồn tại
            }
        }
        return false;
    }
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
    public void deleteNotification(int notificationId) throws SQLException {
        String query = "DELETE FROM Notification WHERE Notification_ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        }
    }
}
