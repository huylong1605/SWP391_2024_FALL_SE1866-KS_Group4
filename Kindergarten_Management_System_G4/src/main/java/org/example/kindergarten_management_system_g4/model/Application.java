package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class Application {
    private int applicationId;
    private String applicationContent;
    private int userId;
    private Date dateCreate;
    private String status;
    private String applicationResponse;
    private Date dateResponse;

    public Application(int applicationId, String applicationContent, int userId, Date dateCreate, String status, String applicationResponse, Date dateResponse) {
        this.applicationId = applicationId;
        this.applicationContent = applicationContent;
        this.userId = userId;
        this.dateCreate = dateCreate;
        this.status = status;
        this.applicationResponse = applicationResponse;
        this.dateResponse = dateResponse;
    }

    public Application() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationContent() {
        return applicationContent;
    }

    public void setApplicationContent(String applicationContent) {
        this.applicationContent = applicationContent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getApplicationResponse() {
        return applicationResponse;
    }

    public void setApplicationResponse(String applicationResponse) {
        this.applicationResponse = applicationResponse;
    }

    public Date getDateResponse() {
        return dateResponse;
    }

    public void setDateResponse(Date dateResponse) {
        this.dateResponse = dateResponse;
    }
}
