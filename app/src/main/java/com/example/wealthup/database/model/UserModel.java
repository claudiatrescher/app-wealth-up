package com.example.wealthup.database.model;

public class UserModel {

    public static final String TABLE_NAME = "tb_user";

    //  Colunas da tabela
    public static final String
            COLUNA_ID = "_id",
            COLUNA_NAME = "name",
            COLUNA_EMAIL = "email",
            COLUNA_PASSWORD = "password";

    //  SCript de criação da tabela
    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NAME + " text not null, "
            + COLUNA_EMAIL + " text not null UNIQUE, "
            + COLUNA_PASSWORD + " text not null"
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";

    private int id;
    private String name;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}