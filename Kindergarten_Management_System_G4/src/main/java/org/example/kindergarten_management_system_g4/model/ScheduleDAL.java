package org.example.kindergarten_management_system_g4.model;

public class ScheduleDAL {

    private int scheduleId;
    private String subject_name;
    private String  termName;
    private String slotName;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String dateOfDay;
    private String className;
    private String room;
    private String teacher;
    private String start_Date;
    private String end_Date;
    private String attendance;
    public ScheduleDAL() {
    }

    public ScheduleDAL(int scheduleId, String subject_name, String termName, String slotName, String startTime, String endTime, String dayOfWeek, String dateOfDay, String className, String room, String teacher, String start_Date, String end_Date, String attendance) {
        this.scheduleId = scheduleId;
        this.subject_name = subject_name;
        this.termName = termName;
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.dateOfDay = dateOfDay;
        this.className = className;
        this.room = room;
        this.teacher = teacher;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.attendance = attendance;
    }

    public ScheduleDAL(int scheduleId, String subject_name, String termName, String slotName, String startTime, String endTime, String dayOfWeek, String dateOfDay, String className, String room, String teacher) {
        this.scheduleId = scheduleId;
        this.subject_name = subject_name;
        this.termName = termName;
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.dateOfDay = dateOfDay;
        this.className = className;
        this.room = room;
        this.teacher = teacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(String dateOfDay) {
        this.dateOfDay = dateOfDay;
    }

    public String getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    public String getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "ScheduleDAL{" +
                "scheduleId=" + scheduleId +
                ", subject_name='" + subject_name + '\'' +
                ", termName='" + termName + '\'' +
                ", slotName='" + slotName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", dateOfDay='" + dateOfDay + '\'' +
                ", className='" + className + '\'' +
                ", room='" + room + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
