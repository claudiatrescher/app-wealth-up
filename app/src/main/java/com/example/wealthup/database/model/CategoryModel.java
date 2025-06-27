package com.example.wealthup.database.model;

public class CategoryModel {

    public CategoryModel(String name, String color, int idUser) {
        this.name = name;
        this.color = color;
        this.idUser = idUser;
    }

    public CategoryModel() {
    }
    public static final String TABLE_NAME = "tb_category";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_NAME = "name",
            COLUNA_COLOR = "color",
            COLUNA_ID_USER = "_id_user";

    //  Script de criação da tabela
    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NAME + " text not null, "
            + COLUNA_COLOR + " text not null, "
            + COLUNA_ID_USER + " integer not null, "
            + "FOREIGN KEY("+COLUNA_ID_USER+") REFERENCES tb_user(_id)"
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";
    private int id;
    private String name;
    private String color;
    private int idUser;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}