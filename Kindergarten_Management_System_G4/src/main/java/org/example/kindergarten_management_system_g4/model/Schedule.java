package org.example.kindergarten_management_system_g4.model;

public class Schedule {
    private int scheduleId;
    private String dayOfWeek;
    private String dateOfDay;
    private int termId;
    private int classId;
    private int slotId;

    public Schedule() {
    }

    public Schedule(int scheduleId, String dayOfWeek, String dateOfDay, int termId, int classId, int slotId) {
        this.scheduleId = scheduleId;
        this.dayOfWeek = dayOfWeek;
        this.dateOfDay = dateOfDay;
        this.termId = termId;
        this.classId = classId;
        this.slotId = slotId;
    }

    public Schedule(String dateOfDay, int classId, int slotId) {
        this.dateOfDay = dateOfDay;
        this.classId = classId;
        this.slotId = slotId;
    }

    public Schedule(String dayOfWeek, String dateOfDay, int termId, int classId, int slotId) {
        this.dayOfWeek = dayOfWeek;
        this.dateOfDay = dateOfDay;
        this.termId = termId;
        this.classId = classId;
        this.slotId = slotId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
}
