package org.example.kindergarten_management_system_g4.model;

public class ScheduleSubject {
    int scheduleId;
    int subjectId;

    public ScheduleSubject() {
    }

    public ScheduleSubject(int scheduleId, int subjectId) {
        this.scheduleId = scheduleId;
        this.subjectId = subjectId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
