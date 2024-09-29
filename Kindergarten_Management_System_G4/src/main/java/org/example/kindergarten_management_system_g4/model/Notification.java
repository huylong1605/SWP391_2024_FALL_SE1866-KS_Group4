package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class Notification {
    private int notificationId;
    private String title;
    private String content;
    private Date date;
    private int userId;

    public Notification() {
    }

    public Notification(int notificationId, String title, String content, Date date, int userId) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.userId = userId;
    }

    public Notification(int notificationId, String title, String content, Date date) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.date = date;

    }


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
