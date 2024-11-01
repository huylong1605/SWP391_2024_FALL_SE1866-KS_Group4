package org.example.kindergarten_management_system_g4.model;

import java.sql.Time;

public class Slot {

    private int slotId;
    private String slotName;
    private Time statrTime;
    private Time endTime;

    public Slot() {
    }

    public Slot(int slotId, String slotName, Time statrTime, Time endTime) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.statrTime = statrTime;
        this.endTime = endTime;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public Time getStatrTime(Time time) {
        return statrTime;
    }

    public void setStatrTime(Time statrTime) {
        this.statrTime = statrTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
