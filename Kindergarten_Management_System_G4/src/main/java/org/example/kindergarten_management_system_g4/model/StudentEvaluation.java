package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;

public class StudentEvaluation {
    private int evaluationId;
    private int studentId;
    private String studentName;
    private String termName;
    private String ranking; // Using String for ENUM values
    private String description;
    private LocalDate evaluationDate;
    private int teacherId;
    private int termId;
    private LocalDate dateOfBirth;
    private String gender;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Default constructor
    public StudentEvaluation() {
    }

    // Constructor with all fields
    public StudentEvaluation(int evaluationId, int studentId, String ranking, String description, LocalDate evaluationDate, int teacherId) {
        this.evaluationId = evaluationId;
        this.studentId = studentId;
        this.ranking = ranking;
        this.description = description;
        this.evaluationDate = evaluationDate;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "StudentEvaluation{" +
                "evaluationId=" + evaluationId +
                ", studentId=" + studentId +
                ", ranking='" + ranking + '\'' +
                ", description='" + description + '\'' +
                ", evaluationDate=" + evaluationDate +
                ", teacherId=" + teacherId +
                '}';
    }
}

