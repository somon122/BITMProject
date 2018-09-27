package com.example.user.bitm_project.TravelEvent;

public class TravelEvents {

    String id;
    String distination;
    String budget;
    String fromDate;
    String toDate;

    public TravelEvents() {
    }

    public TravelEvents(String id, String distination, String budget, String fromDate, String toDate) {
        this.id = id;
        this.distination = distination;
        this.budget = budget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistination() {
        return distination;
    }

    public void setDistination(String distination) {
        this.distination = distination;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
