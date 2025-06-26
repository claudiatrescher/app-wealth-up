package com.example.wealthup.database.model;

public class CategoryModel {

    public static final String TABLE_NAME = "tb_category";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_NAME = "name",
            COLUNA_COLOR = "color";

    //  Script de criação da tabela
    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NAME + " text not null, "
            + COLUNA_COLOR + " text not null, "
            + " );";

    public static final String
            DROP_TABLE =  "drop table if exist " + TABLE_NAME + ";";
    private int id;
    private String name;
    private String color;

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