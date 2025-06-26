package com.example.wealthup.database.model;

public class FixedExpenseModel {

    public static final String TABLE_NAME = "tb_fixed_expense";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_NAME = "name",
            COLUNA_VALUE = "value",
            COLUNA_DATEMILIS = "data_milis",
            COLUNA_CATEGORY = "category",
            COLUNA_ID_USER = "_id_user";

    //  Script de criação da tabela
    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NAME + " text not null, "
            + COLUNA_CATEGORY + " text not null, "
            + COLUNA_DATEMILIS + " long not null, "
            + COLUNA_VALUE + " real not null, "
            + COLUNA_ID_USER + " integer not null, "
            + "FOREIGN KEY("+COLUNA_ID_USER+") REFERENCES tb_user(_id)"
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";

    private int id;
    private String name;
    private double value;
    private long dueDateMillis;
    private String category;
    private int idUser;

    public FixedExpenseModel() {
    }

    public FixedExpenseModel(int id, String name, double value, long dueDateMillis) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.dueDateMillis = dueDateMillis;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getValue() {
        return value;
    }

    public long getDueDateMillis() {
        return dueDateMillis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDueDateMillis(long dueDateMillis) {
        this.dueDateMillis = dueDateMillis;
    }

    @Override
    public String toString() {
        return "FixedExpense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", dueDateMillis=" + dueDateMillis +
                '}';
    }
}