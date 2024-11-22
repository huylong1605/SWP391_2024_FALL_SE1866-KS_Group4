/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115             Student
 */

package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Student đại diện cho một học sinh trong hệ thống.
 * Lớp này chứa thông tin chi tiết về học sinh, bao gồm ID, ngày sinh, giới tính, tên, lớp học,
 * thông tin liên hệ, và thông tin phụ huynh.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @see java.time.LocalDate
 * @see java.time.Period
 *
 */
public class Student {
    private int studentId;              // ID của học sinh
    private LocalDate dob;              // Ngày sinh của học sinh
    private boolean gender;             // Giới tính của học sinh
    private String name;                // Tên của học sinh
    private int classId;                // ID của lớp học
    private int userId;                 // ID của người quản lý học sinh
    private String address;             // Địa chỉ của học sinh
    private String phoneNumber;         // Số điện thoại của học sinh
    private String parentName;          // Tên phụ huynh của học sinh
    private String parentEmail;         // Email phụ huynh của học sinh
    private String parentAddress;       // Địa chỉ phụ huynh
    private String parentPhone;         // Số điện thoại phụ huynh
    private String className;           // Tên lớp học của học sinh

    // Constructor và Getter/Setter
    /**
     * Constructor mặc định cho lớp Student.
     */
    public Student() {
    }

    /**
     * Constructor cho lớp Student với các thuộc tính cơ bản.
     *
     * @param studentId   ID của học sinh.
     * @param dob         Ngày sinh của học sinh.
     * @param gender      Giới tính của học sinh.
     * @param name        Tên của học sinh.
     * @param address     Địa chỉ của học sinh.
     * @param phoneNumber Số điện thoại của học sinh.
     */
    public Student(int studentId, LocalDate dob, boolean gender, String name, String address, String phoneNumber) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor cho lớp Student với thông tin lớp học và người quản lý.
     *
     * @param studentId ID của học sinh.
     * @param dob       Ngày sinh của học sinh.
     * @param gender    Giới tính của học sinh.
     * @param name      Tên của học sinh.
     * @param classId   ID của lớp học.
     * @param userId    ID của người quản lý học sinh.
     */
    public Student(int studentId, LocalDate dob, boolean gender, String name, int classId, int userId) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.classId = classId;
        this.userId = userId;
    }

    /**
     * Constructor cho lớp Student chỉ với ID người quản lý.
     *
     * @param studentId ID của học sinh.
     * @param dob       Ngày sinh của học sinh.
     * @param gender    Giới tính của học sinh.
     * @param name      Tên của học sinh.
     * @param userId    ID của người quản lý học sinh.
     */
    public Student(int studentId, LocalDate dob, boolean gender, String name, int userId) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.userId = userId;
    }

    /**
     * Constructor cho lớp Student chỉ với các thuộc tính cơ bản.
     *
     * @param studentId ID của học sinh.
     * @param dob       Ngày sinh của học sinh.
     * @param gender    Giới tính của học sinh.
     * @param name      Tên của học sinh.
     */
    public Student(int studentId, LocalDate dob, boolean gender, String name) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
    }

    /**
     * Lấy tuổi của học sinh dựa trên ngày sinh.
     *
     * @return tuổi của học sinh, hoặc 0 nếu không có ngày sinh.
     */
    public int getAge() {
        if (dob == null) {
            return 0;
        }
        return Period.between(dob, LocalDate.now()).getYears();
    }

    // Getter và Setter cho các thuộc tính còn lại

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Trả về biểu diễn dạng chuỗi của đối tượng Student.
     *
     * @return chuỗi chứa thông tin của đối tượng Student.
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", dob=" + dob +
                ", Age=" + getAge() +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", classId=" + classId +
                ", userId=" + userId +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
