package com.example.user.bitm_project.ExpenseRecord;

public class ExpenseRecord {

    String id;
    String dateTime;
    String expenseDetails;
    String expenseAmount;

    public ExpenseRecord() {
    }

    public ExpenseRecord(String id,String dateTime, String expenseDetails, String expenseAmount) {
        this.id = id;
        this.expenseDetails = expenseDetails;
        this.expenseAmount = expenseAmount;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
