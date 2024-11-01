package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class Activity_Registrations {
    private int registration_id;
    private int activity_id;
    private int student_id;
    private Date registration_date;
    private String status;
    private boolean parent_approval;

    public Activity_Registrations() {
    }

    public Activity_Registrations(int registration_id, int activity_id, int student_id, Date registration_date, String status, boolean parent_approval) {
        this.registration_id = registration_id;
        this.activity_id = activity_id;
        this.student_id = student_id;
        this.registration_date = registration_date;
        this.status = status;
        this.parent_approval = parent_approval;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isParent_approval() {
        return parent_approval;
    }

    public void setParent_approval(boolean parent_approval) {
        this.parent_approval = parent_approval;
    }

    @Override
    public String toString() {
        return "Activity_Registrations{" +
                "registration_id=" + registration_id +
                ", activity_id=" + activity_id +
                ", student_id=" + student_id +
                ", registration_date=" + registration_date +
                ", status='" + status + '\'' +
                ", parent_approval=" + parent_approval +
                '}';
    }
}
