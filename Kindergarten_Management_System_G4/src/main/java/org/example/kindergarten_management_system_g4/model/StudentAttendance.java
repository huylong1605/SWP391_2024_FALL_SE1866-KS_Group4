package org.example.kindergarten_management_system_g4.model;

import java.sql.Date;

public class StudentAttendance {
    private int studentId;
    private String studentName;
    private Date dateOfBirth;
    private boolean attendStatus; // Hoặc có thể dùng int nếu bạn muốn dùng 0 và 1
    // Constructor
    public StudentAttendance(int studentId, String studentName, Date dateOfBirth, boolean attendStatus) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.attendStatus = attendStatus;
    }

    // Getters
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getAttendStatus() { // Sử dụng boolean ở đây
        return attendStatus;
    }

    // Setters (nếu cần thiết)
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAttendStatus(boolean attendStatus) { // Sử dụng boolean ở đây
        this.attendStatus = attendStatus;
    }

    @Override
    public String toString() {
        return "StudentAttendance{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", attendStatus=" + attendStatus +
                '}';
    }
}
