/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/10/2024       1.1              Nguyễn Huy Long - He160140              Create class Classes
 * 10/12/2024       1.2              Nguyễn Huy Long - He160140              Update class Classes
 */
package org.example.kindergarten_management_system_g4.model;

/**
 * Lớp Classes đại diện cho một lớp học trong hệ thống quản lý mầm non.
 * Chứa các thuộc tính liên quan đến lớp học và các phương thức
 * để truy cập và cập nhật các thuộc tính này.
 * @author Nguyễn Huy Long
 */
public class Classes {

    private int classId;               // ID của lớp
    private String className;          // Tên của lớp
    private int classLevelId;          // ID cấp lớp
    private int userId;                // ID của giáo viên phụ trách lớp
    private int roomId;                // ID của phòng học

    // Constructor không tham số
    public Classes() {
    }

    /**
     * Constructor với tất cả các thuộc tính.
     *
     * @param classId ID của lớp
     * @param className Tên của lớp
     * @param classLevelId ID cấp lớp
     * @param userId ID của giáo viên phụ trách lớp
     * @param roomId ID của phòng học
     */
    public Classes(int classId, String className, int classLevelId, int userId, int roomId) {
        this.classId = classId;
        this.className = className;
        this.classLevelId = classLevelId;
        this.userId = userId;
        this.roomId = roomId;
    }

    /**
     * Constructor với một số thuộc tính (không có classId).
     *
     * @param className Tên của lớp
     * @param classLevelId ID cấp lớp
     * @param userId ID của giáo viên phụ trách lớp
     * @param roomId ID của phòng học
     */
    public Classes(String className, int classLevelId, int userId, int roomId) {
        this.className = className;
        this.classLevelId = classLevelId;
        this.userId = userId;
        this.roomId = roomId;
    }

    // Phương thức getter và setter cho các thuộc tính

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

    public int getClassLevelId() {
        return classLevelId; // Trả về ID cấp lớp
    }

    public void setClassLevelId(int classLevelId) {
        this.classLevelId = classLevelId; // Cập nhật ID cấp lớp
    }

    public int getUserId() {
        return userId; // Trả về ID của giáo viên phụ trách lớp
    }

    public void setUserId(int userId) {
        this.userId = userId; // Cập nhật ID của giáo viên phụ trách lớp
    }

    public int getRoomId() {
        return roomId; // Trả về ID của phòng học
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId; // Cập nhật ID của phòng học
    }
}
