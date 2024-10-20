package org.example.kindergarten_management_system_g4.model;

import java.util.Date;

public class Term {
    private int termId;
    private String termName;
    private Date startDate;
    private Date endDate;
    private int year;

    // Constructor
    public Term() {}

    public Term(int termId, String termName, Date startDate, Date endDate, int year) {
        this.termId = termId;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
    }

    public int getTermId() {
        return termId;
    }

    public String getTermName() {
        return termName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getYear() {
        return year;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
