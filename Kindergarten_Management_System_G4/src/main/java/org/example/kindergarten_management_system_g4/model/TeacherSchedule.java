package org.example.kindergarten_management_system_g4.model;

public class TeacherSchedule {
    private int scheduleID;
    private int classId; // Thêm classId
    private String className;
    private String subjectName;
    private int slotId; // Thêm slotId
    private String slotName;
    private String date;
    private String room;
    private String termName;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private boolean attendanceMarked;

    private boolean canAttend; // Thêm thuộc tính này

    // Getter và Setter cho canAttend
    public boolean isCanAttend() {
        return canAttend;
    }

    public void setCanAttend(boolean canAttend) {
        this.canAttend = canAttend;
    }

    public TeacherSchedule(int scheduleID, int classId, String className, String subjectName, int slotId, String slotName,
                           String date, String room, String termName, String dayOfWeek, String startTime, String endTime, boolean attendanceMarked) {
        this.scheduleID = scheduleID;
        this.classId = classId; // Khởi tạo classId
        this.className = className;
        this.subjectName = subjectName;
        this.slotId = slotId; // Khởi tạo slotId
        this.slotName = slotName;
        this.date = date;
        this.room = room;
        this.termName = termName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceMarked = attendanceMarked;
    }

    public boolean isAttendanceMarked() {
        return attendanceMarked;
    }

    public void setAttendanceMarked(boolean attendanceMarked) {
        this.attendanceMarked = attendanceMarked;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getClassId() { // Getter cho classId
        return classId;
    }

    public void setClassId(int classId) { // Setter cho classId
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSlotId() { // Getter cho slotId
        return slotId;
    }

    public void setSlotId(int slotId) { // Setter cho slotId
        this.slotId = slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
