package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;
import java.time.Period;

public class Student {
    private int studentId;
    private LocalDate dob;
    private boolean gender;
    private String name;
    private int classId;
    private int userId;
    private String address;
    private String phoneNumber;
    private String parentName;
    private String parentEmail;
    private String parentAddress;
    private String parentPhone;
    private String className;

    public String getParentName() {
        return parentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public int getAge() {
        if (dob == null) {
            return 0;
        }
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public Student() {
    }

    public Student(int studentId, LocalDate dob, boolean gender, String name,String address, String phoneNumber) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public Student(int studentId, LocalDate dob, boolean gender, String name, int classId, int userId) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.classId = classId;
        this.userId = userId;
    }
    public Student(int studentId, LocalDate dob, boolean gender, String name, int userId) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.userId = userId;
    }
    public Student(int studentId, LocalDate dob, boolean gender, String name) {
        this.studentId = studentId;
        this.dob = dob;
        this.gender = gender;
        this.name = name;
    }



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

    public boolean getGender() {return gender;}

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
