package org.example.kindergarten_management_system_g4.model;

public class Subject {
    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private String description;
    private String status;
    // Constructor
    public Subject() {}

    /**
     * Constructor to initialize the Subject with all fields.
     *
     * @param subjectId The unique ID of the subject.
     * @param subjectCode The code of the subject.
     * @param subjectName The name of the subject.
     * @param description The description of the subject.
     * @param status The status of the subject.
     */
    public Subject(int subjectId, String subjectCode, String subjectName, String description, String status) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.description = description;
        this.status = status;
    }


    // Getters and Setters<%@ include file="Views/common/header.jsp" %>
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
