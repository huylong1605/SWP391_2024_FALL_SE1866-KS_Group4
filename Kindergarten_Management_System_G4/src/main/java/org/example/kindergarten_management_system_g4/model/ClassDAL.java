/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/10/2024       1.1              Nguyễn Huy Long - He160140              Create classDAL
 * 10/12/2024       1.2              Nguyễn Huy Long - He160140              Update classDAL
 */
package org.example.kindergarten_management_system_g4.model;

/**
 * Lớp ClassDAL đại diện cho thông tin của một lớp học trong hệ thống.
 * Chứa các thuộc tính liên quan đến lớp học và các phương thức
 * để truy cập và cập nhật các thuộc tính này.
 * @author Nguyễn Huy Long
 */
public class ClassDAL {

    private int classId;               // ID của lớp
    private String className;          // Tên của lớp
    private String classLevelName;     // Tên cấp lớp
    private String fullname;            // Tên đầy đủ của giáo viên
    private String roomNumber;          // Số phòng của lớp
    private String description;         // Mô tả về lớp
    private String Email;               // Địa chỉ email của giáo viên
    private String capacity;            // Sức chứa của lớp học

    // Constructor không tham số
    public ClassDAL() {
    }

    /**
     * Constructor với tất cả các thuộc tính.
     *
     * @param classId ID của lớp
     * @param className Tên của lớp
     * @param classLevelName Tên cấp lớp
     * @param fullname Tên đầy đủ của giáo viên
     * @param roomNumber Số phòng của lớp
     * @param description Mô tả về lớp
     * @param email Địa chỉ email của giáo viên
     * @param capacity Sức chứa của lớp học
     */
    public ClassDAL(int classId, String className, String classLevelName, String fullname, String roomNumber, String description, String email, String capacity) {
        this.classId = classId;
        this.className = className;
        this.classLevelName = classLevelName;
        this.fullname = fullname;
        this.roomNumber = roomNumber;
        this.description = description;
        Email = email;
        this.capacity = capacity;
    }

    /**
     * Constructor với một số thuộc tính.
     *
     * @param classId ID của lớp
     * @param className Tên của lớp
     * @param classLevelName Tên cấp lớp
     * @param fullname Tên đầy đủ của giáo viên
     * @param roomNumber Số phòng của lớp
     */
    public ClassDAL(int classId, String className, String classLevelName, String fullname, String roomNumber) {
        this.classId = classId;
        this.className = className;
        this.classLevelName = classLevelName;
        this.fullname = fullname;
        this.roomNumber = roomNumber;
    }

    // Phương thức getter và setter cho các thuộc tính

    public String getDescription() {
        return description; // Trả về mô tả của lớp
    }

    public void setDescription(String description) {
        this.description = description; // Cập nhật mô tả của lớp
    }

    public String getEmail() {
        return Email; // Trả về địa chỉ email của giáo viên
    }

    public void setEmail(String email) {
        Email = email; // Cập nhật địa chỉ email của giáo viên
    }

    public String getCapacity() {
        return capacity; // Trả về sức chứa của lớp
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity; // Cập nhật sức chứa của lớp
    }

    public int getClassId() {
        return classId; // Trả về ID của lớp
    }

    public void setClassId(int classId) {
        this.classId = classId; // Cập nhật ID của lớp
    }

    public String getClassName() {
        return className; // Trả về tên của lớp
    }

    public void setClassName(String className) {
        this.className = className; // Cập nhật tên của lớp
    }

    public String getClassLevelName() {
        return classLevelName; // Trả về tên cấp lớp
    }

    public void setClassLevelName(String classLevelName) {
        this.classLevelName = classLevelName; // Cập nhật tên cấp lớp
    }

    public String getFullname() {
        return fullname; // Trả về tên đầy đủ của giáo viên
    }

    public void setFullname(String fullname) {
        this.fullname = fullname; // Cập nhật tên đầy đủ của giáo viên
    }

    public String getRoomNumber() {
        return roomNumber; // Trả về số phòng của lớp
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber; // Cập nhật số phòng của lớp
    }

    @Override
    public String toString() {
        return "ClassDAL{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", classLevelName='" + classLevelName + '\'' +
                ", fullname='" + fullname + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", description='" + description + '\'' +
                ", Email='" + Email + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }


}
