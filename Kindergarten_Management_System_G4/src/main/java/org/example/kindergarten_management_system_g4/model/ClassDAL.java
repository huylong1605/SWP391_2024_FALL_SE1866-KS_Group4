package org.example.kindergarten_management_system_g4.model;

public class ClassDAL {

      private int classId;
       private String className;
       private String classLevelName;
       private String fullname;
       private String roomNumber;

    public ClassDAL() {
    }

    public ClassDAL(int classId, String className, String classLevelName, String fullname, String roomNumber) {
        this.classId = classId;
        this.className = className;
        this.classLevelName = classLevelName;
        this.fullname = fullname;
        this.roomNumber = roomNumber;
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
