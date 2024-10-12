package org.example.kindergarten_management_system_g4.model;

public class ClassLevel {

    private int classLevelId;
    private String classLevelName;
    private String description;

    public ClassLevel() {
    }

    public ClassLevel(int classLevelId, String classLevelName, String description) {
        this.classLevelId = classLevelId;
        this.classLevelName = classLevelName;
        this.description = description;
    }

    public int getClassLevelId() {
        return classLevelId;
    }

    public void setClassLevelId(int classLevelId) {
        this.classLevelId = classLevelId;
    }

    public String getClassLevelName() {
        return classLevelName;
    }

    public void setClassLevelName(String classLevelName) {
        this.classLevelName = classLevelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
