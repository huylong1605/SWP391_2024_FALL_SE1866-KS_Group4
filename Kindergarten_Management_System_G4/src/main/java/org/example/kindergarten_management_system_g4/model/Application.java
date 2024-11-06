package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class Application {
    private int applicationId;
    private String applicationContent;
    private String title;
    private int userId;
    private Date dateCreate;
    private String status;
    private String applicationResponse;
    private Date dateResponse;
    private String parentName;

    /**
     * Gets the parent name associated with the application.
     *
     * @return parentName The name of the parent.
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets the parent name for the application.
     *
     * @param parentName The name of the parent to be set.
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Constructor to initialize the Application object with all fields.
     *
     * @param applicationId The unique ID of the application.
     * @param applicationContent The content of the application.
     * @param userId The ID of the user who submitted the application.
     * @param dateCreate The date the application was created.
     * @param status The current status of the application.
     * @param applicationResponse The response to the application.
     * @param dateResponse The date the application received a response.
     */
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
