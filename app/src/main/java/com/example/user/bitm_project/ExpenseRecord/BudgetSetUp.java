package com.example.user.bitm_project.ExpenseRecord;

public class BudgetSetUp {

    private int balance;
    private int mainBalance;

    public int getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(int mainBalance) {
        this.mainBalance = mainBalance;
    }

    public BudgetSetUp() {
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void Withdraw(int amount){

        if (getBalance() - amount >=0){
            balance -= amount;
        }



    }
    public void AddBalance(int amount){

        balance += amount;


    }
    public void AddMainBalance(int amount){

        mainBalance += amount;


    }

}
