package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class ExtracurricularActivities {
    private int activity_id;
    private String activity_name;
    private String description;
    private Date  start_time;
    private Date end_time;
    private String location;

    private int user_id;
    private String materials_needed;
    private boolean status;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMaterials_needed() {
        return materials_needed;
    }

    public void setMaterials_needed(String materials_needed) {
        this.materials_needed = materials_needed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ExtracurricularActivities(int activity_id, String activity_name, String description, Date start_time, Date end_time, String location, int user_id, String materials_needed, boolean status) {
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.user_id = user_id;
        this.materials_needed = materials_needed;
        this.status = status;
    }

    public ExtracurricularActivities() {
    }
}
