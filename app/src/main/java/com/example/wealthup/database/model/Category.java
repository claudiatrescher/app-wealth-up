package com.example.wealthup.database.model;

public class Category {
    private int id;
    private String name;
    private String color; // Armazena a cor como String (ex: "#RRGGBB" ou "red")

    public Category() {
        // Construtor vazio
    }

    public Category(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

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