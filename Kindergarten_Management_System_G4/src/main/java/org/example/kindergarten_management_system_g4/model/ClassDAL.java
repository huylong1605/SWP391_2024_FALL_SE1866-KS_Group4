package org.example.kindergarten_management_system_g4.model;

public class ClassDAL {

    private int classId;
    private String className;
    private String classLevelName;
    private String fullname;
    private String roomNumber;
    private String description;
    private String Email;
    private String capacity;


    public ClassDAL() {
    }

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

    public ClassDAL(int classId, String className, String classLevelName, String fullname, String roomNumber) {
        this.classId = classId;
        this.className = className;
        this.classLevelName = classLevelName;
        this.fullname = fullname;
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassLevelName() {
        return classLevelName;
    }

    public void setClassLevelName(String classLevelName) {
        this.classLevelName = classLevelName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
