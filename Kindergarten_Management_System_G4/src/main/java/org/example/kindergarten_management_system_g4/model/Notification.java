package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

/**
 * Lớp Notification đại diện cho thông báo trong hệ thống quản lý nhà trẻ.
 * Lớp này bao gồm các thuộc tính của thông báo như ID, tiêu đề, nội dung, ngày tháng và ID người dùng.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 */
public class Notification {
    private int notificationId; // ID của thông báo
    private String title;       // Tiêu đề của thông báo
    private String content;     // Nội dung của thông báo
    private Date date;          // Ngày thông báo được tạo
    private int userId;         // ID của người dùng tạo ra thông báo

    /**
     * Constructor mặc định của lớp Notification.
     */
    public Notification() {
    }

    /**
     * Constructor của lớp Notification với tất cả các thuộc tính.
     *
     * @param notificationId ID của thông báo
     * @param title Tiêu đề của thông báo
     * @param content Nội dung của thông báo
     * @param date Ngày thông báo được tạo
     * @param userId ID của người dùng tạo ra thông báo
     */
    public Notification(int notificationId, String title, String content, Date date, int userId) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.userId = userId;
    }

    /**
     * Constructor của lớp Notification mà không có userId.
     *
     * @param notificationId ID của thông báo
     * @param title Tiêu đề của thông báo
     * @param content Nội dung của thông báo
     * @param date Ngày thông báo được tạo
     */
    public Notification(int notificationId, String title, String content, Date date) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // Các phương thức getter và setter cho từng thuộc tính

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
