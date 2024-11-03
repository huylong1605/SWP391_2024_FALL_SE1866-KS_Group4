package org.example.kindergarten_management_system_g4.model;

public class TeacherSchedule {
        private int scheduleID;
        private String className;
        private String subjectName;
        private String slotName;
        private String date;
        private String room;
        private String termName;
        private String dayOfWeek;
        private String startTime;
        private String endTime;

        public TeacherSchedule(int scheduleID, String className, String subjectName, String slotName, String date,
                               String room, String termName, String dayOfWeek, String startTime, String endTime) {
            this.scheduleID = scheduleID;
            this.className = className;
            this.subjectName = subjectName;
            this.slotName = slotName;
            this.date = date;
            this.room = room;
            this.termName = termName;
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }

    public String getClassName() {
        return className;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
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
