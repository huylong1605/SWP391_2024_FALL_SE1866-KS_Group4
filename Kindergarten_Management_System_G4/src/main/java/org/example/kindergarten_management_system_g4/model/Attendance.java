package org.example.kindergarten_management_system_g4.model;

public class Attendance {
    private int attendanceID;
    private int attendanceStatus;
    private String attendanceDate;
    private int attendanceTime;
    private int studentID;
    private int slotID;

    public Attendance() {
    }

    public Attendance(int attendanceID, int attendanceStatus, String attendanceDate, int attendanceTime, int studentID, int slotID) {
        this.attendanceID = attendanceID;
        this.attendanceStatus = attendanceStatus;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.studentID = studentID;
        this.slotID = slotID;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public int getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(int attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSlotID() {
        return slotID;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }
}
