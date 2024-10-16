/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/10/2024       1.1              Nguyễn Huy Long - He160140              Create class level
 * 10/12/2024       1.2              Nguyễn Huy Long - He160140              Update class level
 */
package org.example.kindergarten_management_system_g4.model;

/**
 * Lớp ClassLevel đại diện cho cấp lớp trong hệ thống quản lý mầm non.
 * Chứa thông tin về cấp lớp bao gồm tên lớp, ID lớp, và mô tả.
 * @author Nguyễn Huy Long
 */
public class ClassLevel {

    private int classLevelId;           // ID của cấp lớp
    private String classLevelName;      // Tên của cấp lớp
    private String description;
    private int ageRange;
    // Mô tả về cấp lớp

    // Constructor không tham số
    public ClassLevel() {
    }

    /**
     * Constructor với tất cả các thuộc tính.
     *
     * @param classLevelId ID của cấp lớp
     * @param classLevelName Tên của cấp lớp
     * @param description Mô tả về cấp lớp
     */
    public ClassLevel(int classLevelId, String classLevelName, String description) {
        this.classLevelId = classLevelId;
        this.classLevelName = classLevelName;
        this.description = description;
    }

    public ClassLevel(int classLevelId, String classLevelName, String description, int ageRange) {
        this.classLevelId = classLevelId;
        this.classLevelName = classLevelName;
        this.description = description;
        this.ageRange = ageRange;
    }
    // Phương thức getter và setter cho các thuộc tính

    public int getClassLevelId() {
        return classLevelId; // Trả về ID của cấp lớp
    }

    public void setClassLevelId(int classLevelId) {
        this.classLevelId = classLevelId; // Cập nhật ID của cấp lớp
    }

    public String getClassLevelName() {
        return classLevelName; // Trả về tên của cấp lớp
    }

    public void setClassLevelName(String classLevelName) {
        this.classLevelName = classLevelName; // Cập nhật tên của cấp lớp
    }

    public String getDescription() {
        return description; // Trả về mô tả của cấp lớp
    }

    public void setDescription(String description) {
        this.description = description; // Cập nhật mô tả của cấp lớp
    }

    public int getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }
}
