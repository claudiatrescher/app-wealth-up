package com.example.wealthup.database.model;

import java.io.Serializable;

public class ExpenseModel implements Serializable {

    public static final String TABLE_NAME = "tb_expense";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_CATEGORY = "category",
            COLUNA_DAY = "day",
            COLUNA_MONTH = "month",
            COLUNA_YEAR = "year",
            COLUNA_DESCRIPTION = "description",
            COLUNA_AMOUNT = "amount",
            COLUNA_ID_USER = "_id_user";

    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_CATEGORY + " text not null, "
            + COLUNA_DAY + " int not null, "
            + COLUNA_MONTH + " int not null, "
            + COLUNA_YEAR + " int not null, "
            + COLUNA_DESCRIPTION + " text not null, "
            + COLUNA_AMOUNT + " real not null, "
            + COLUNA_ID_USER + " integer not null, "
            + "FOREIGN KEY("+COLUNA_ID_USER+") REFERENCES tb_user(_id)"
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";

    private int id;
    private String category;
    private String date;
    private long dateInMillis;
    private String description;
    private double amount;
    private int id_user;

    public ExpenseModel(String description, double amount, long dateMillis, String category, int id_user) {
        this.description = description;
        this.amount = amount;
        this.dateInMillis = dateMillis;
        this.category = category;
        this.id_user = id_user;
    }

    public ExpenseModel() {
    }

    public int getId() {
        return id;
    }
    public String getCategory() {
        return category;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}