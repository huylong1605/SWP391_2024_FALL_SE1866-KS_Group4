/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115             ExtracurricularActivities
 */

package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * ExtracurricularActivities đại diện cho một hoạt động ngoại khóa trong hệ thống.
 * Lớp này chứa thông tin chi tiết về một hoạt động ngoại khóa, bao gồm tên hoạt động, mô tả, ngày, thời gian,
 * địa điểm, người quản lý, vật liệu cần thiết, và trạng thái của hoạt động.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @see java.time.LocalDate
 * @see java.time.LocalTime
 *
 */
public class ExtracurricularActivities {
    private int activity_id;           // ID của hoạt động
    private String activity_name;       // Tên của hoạt động
    private String description;         // Mô tả hoạt động
    private LocalDate date;             // Ngày diễn ra hoạt động
    private LocalTime start_time;       // Thời gian bắt đầu
    private LocalTime end_time;         // Thời gian kết thúc
    private String location;            // Địa điểm tổ chức
    private int user_id;                // ID của người quản lý hoạt động
    private String materials_needed;    // Các vật liệu cần thiết cho hoạt động
    private String status;              // Trạng thái của hoạt động (Planned, Completed, etc.)

    /**
     * Constructor mặc định cho ExtracurricularActivities.
     */
    public ExtracurricularActivities() {
    }

    /**
     * Constructor với tất cả các thuộc tính của ExtracurricularActivities.
     *
     * @param activity_id       ID của hoạt động.
     * @param activity_name     Tên của hoạt động.
     * @param description       Mô tả hoạt động.
     * @param date              Ngày diễn ra hoạt động.
     * @param start_time        Thời gian bắt đầu.
     * @param end_time          Thời gian kết thúc.
     * @param location          Địa điểm tổ chức.
     * @param user_id           ID của người quản lý hoạt động.
     * @param materials_needed  Vật liệu cần thiết cho hoạt động.
     * @param status            Trạng thái của hoạt động.
     */
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

    /**
     * Constructor cho ExtracurricularActivities mà không có trạng thái hoạt động.
     *
     * @param activity_id       ID của hoạt động.
     * @param activity_name     Tên của hoạt động.
     * @param description       Mô tả hoạt động.
     * @param date              Ngày diễn ra hoạt động.
     * @param start_time        Thời gian bắt đầu.
     * @param end_time          Thời gian kết thúc.
     * @param location          Địa điểm tổ chức.
     * @param user_id           ID của người quản lý hoạt động.
     * @param materials_needed  Vật liệu cần thiết cho hoạt động.
     */
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

    // Getter và Setter cho các thuộc tính

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

    /**
     * Trả về biểu diễn dạng chuỗi của đối tượng ExtracurricularActivities.
     *
     * @return chuỗi chứa thông tin của đối tượng ExtracurricularActivities.
     */
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
