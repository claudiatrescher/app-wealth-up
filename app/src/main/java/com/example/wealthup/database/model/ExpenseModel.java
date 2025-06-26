package com.example.wealthup.database.model;

import java.io.Serializable;

public class ExpenseModel implements Serializable {

    public static final String TABLE_NAME = "tb_expense";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_CATEGORY = "category",
            COLUNA_DATE = "date",
            COLUNA_DESCRIPTION = "description",
            COLUNA_AMOUNT = "amount",
            COLUNA_ID_USER = "_id_user";

    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_CATEGORY + " text not null, "
            + COLUNA_DATE + " integer  not null, "
            + COLUNA_DESCRIPTION + " text not null, "
            + COLUNA_AMOUNT + " real not null, "
            + COLUNA_ID_USER + " integer not null, "
            + "FOREIGN KEY("+COLUNA_ID_USER+") REFERENCES tb_user(_id)"
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";

    private int _id;
    private String category;
    private String date;
    private long dateInMillis;
    private String description;
    private long amount;
    private int id_user;

    public ExpenseModel(int i, String name, double value, long dateMillis, String category) {
    }

    public int get_id() {
        return _id;
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

    public long getAmount() {
        return amount;
    }

    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public void setAmount(long amount) {
        this.amount = amount;
    }

}