package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class ExtracurricularActivities {
    private int activity_id;
    private String activity_name;
    private String description;
    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;
    private String location;

    private int user_id;
    private String materials_needed;
    private String status;

    public ExtracurricularActivities() {
    }

    public ExtracurricularActivities(int activity_id, String activity_name, String description, LocalDate date, LocalTime start_time, LocalTime end_time, String location, int user_id, String materials_needed, String status) {
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.user_id = user_id;
        this.materials_needed = materials_needed;
        this.status = status;
    }

    public ExtracurricularActivities(int activity_id, String activity_name, String description, LocalDate date, LocalTime start_time, LocalTime end_time, String location, int user_id, String materials_needed) {
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.user_id = user_id;
        this.materials_needed = materials_needed;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExtracurricularActivities{" +
                "activity_id=" + activity_id +
                ", activity_name='" + activity_name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", location='" + location + '\'' +
                ", user_id=" + user_id +
                ", materials_needed='" + materials_needed + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}