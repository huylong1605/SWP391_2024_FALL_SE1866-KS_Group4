package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class AttendanceRecord {

    private int studentId;
    private String studentName;
    private int totalAttendance; // Chỉ dùng cho tóm tắt
    private int totalPresent;    // Chỉ dùng cho tóm tắt
    private int totalAbsent;     // Chỉ dùng cho tóm tắt
    private Date date;           // Chỉ dùng cho chi tiết
    private String slotName;     // Chỉ dùng cho chi tiết
    private String attendStatus; // Chỉ dùng cho chi tiết


    public AttendanceRecord(java.sql.Date date, String slotName, String attendStatus) {
        this.date = date;
        this.slotName = slotName;
        this.attendStatus = attendStatus;
    }

    public AttendanceRecord(int studentId, String studentName, int totalAttendance, int presentCount, int absentCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.totalAttendance = totalAttendance;
        this.totalPresent = presentCount;
        this.totalAbsent = absentCount;
    }

    // Getters và setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public int getTotalAttendance() { return totalAttendance; }
    public void setTotalAttendance(int totalAttendance) { this.totalAttendance = totalAttendance; }
    public int getTotalPresent() { return totalPresent; }
    public void setTotalPresent(int totalPresent) { this.totalPresent = totalPresent; }
    public int getTotalAbsent() { return totalAbsent; }
    public void setTotalAbsent(int totalAbsent) { this.totalAbsent = totalAbsent; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getSlotName() { return slotName; }
    public void setSlotName(String slotName) { this.slotName = slotName; }
    public String getAttendStatus() { return attendStatus; }
    public void setAttendStatus(String attendStatus) { this.attendStatus = attendStatus; }

    @Override
    public String toString() {
        if (totalAttendance > 0) {
            return "AttendanceRecord{" +
                    "studentId=" + studentId +
                    ", studentName='" + studentName + '\'' +
                    ", totalAttendance=" + totalAttendance +
                    ", totalPresent=" + totalPresent +
                    ", totalAbsent=" + totalAbsent +
                    '}';
        } else {
            return "AttendanceRecord{" +
                    "date=" + date +
                    ", slotName='" + slotName + '\'' +
                    ", attendanceStatus='" + attendStatus + '\'' +
                    '}';
        }
    }
}
