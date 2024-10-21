/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/10/2024       1.1              Đào Xuân Bình - HE163115              Manage class level
 */

package org.example.kindergarten_management_system_g4.model;

/**
 * Lớp ClassLevel đại diện cho cấp lớp trong hệ thống quản lý mầm non.
 * Chứa thông tin về cấp lớp bao gồm tên lớp, ID lớp, mô tả, và độ tuổi.
 * <p>Lỗi: Chưa phát hiện lỗi.
 * @author Đào Xuân Bình
 */
public class ClassLevel {

    private int classLevelId;           // ID của cấp lớp
    private String classLevelName;      // Tên của cấp lớp
    private String description;         // Mô tả về cấp lớp
    private int ageRange;               // Độ tuổi của cấp lớp

    // Constructor không tham số
    public ClassLevel() {
    }

    /**
     * Constructor với các thuộc tính chính.
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

    /**
     * Constructor đầy đủ với tất cả các thuộc tính.
     *
     * @param classLevelId ID của cấp lớp
     * @param classLevelName Tên của cấp lớp
     * @param description Mô tả về cấp lớp
     * @param ageRange Độ tuổi của cấp lớp
     */
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
        return ageRange; // Trả về độ tuổi của cấp lớp
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange; // Cập nhật độ tuổi của cấp lớp
    }
}
