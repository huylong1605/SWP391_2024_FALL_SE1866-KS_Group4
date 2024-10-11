package org.example.kindergarten_management_system_g4.model;

public class Classes {

    private int classId;
    private String className;
    private int classLevelId;
    private int userId;
    private int roomId;

    public Classes() {
    }

    public Classes(int classId, String className, int classLevelId, int userId, int roomId) {
        this.classId = classId;
        this.className = className;
        this.classLevelId = classLevelId;
        this.userId = userId;
        this.roomId = roomId;
    }

    public Classes( String className, int classLevelId, int userId, int roomId) {

        this.className = className;
        this.classLevelId = classLevelId;
        this.userId = userId;
        this.roomId = roomId;
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

    public int getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(int classLevelId) {
        this.classLevelId = classLevelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
