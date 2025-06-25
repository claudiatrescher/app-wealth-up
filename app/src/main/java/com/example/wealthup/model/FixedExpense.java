package com.example.wealthup.model;

public class FixedExpense {
    private int id;
    private String name;
    private double value;
    private long dueDateMillis;

    public FixedExpense() {
    }

    public FixedExpense(int id, String name, double value, long dueDateMillis) {
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