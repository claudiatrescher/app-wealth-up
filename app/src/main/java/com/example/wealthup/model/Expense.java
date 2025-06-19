package com.example.wealthup.model;

public class Expense {
    private String category;
    private String date;
    private String description;
    private String amount;

    public Expense(String category, String date, String description, String amount) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}