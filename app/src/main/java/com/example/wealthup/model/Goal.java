package com.example.wealthup.model;

public class Goal {
    private int id;
    private String name;
    private double targetAmount;
    private long startDateMillis;
    private long endDateMillis;
    private boolean dividedMonthly;

    public Goal() {
    }

    public Goal(int id, String name, double targetAmount, long startDateMillis, long endDateMillis, boolean dividedMonthly) {
        this.id = id;
        this.name = name;
        this.targetAmount = targetAmount;
        this.startDateMillis = startDateMillis;
        this.endDateMillis = endDateMillis;
        this.dividedMonthly = dividedMonthly;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getTargetAmount() { return targetAmount; }
    public long getStartDateMillis() { return startDateMillis; }
    public long getEndDateMillis() { return endDateMillis; }
    public boolean isDividedMonthly() { return dividedMonthly; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }
    public void setStartDateMillis(long startDateMillis) { this.startDateMillis = startDateMillis; }
    public void setEndDateMillis(long endDateMillis) { this.endDateMillis = endDateMillis; }
    public void setDividedMonthly(boolean dividedMonthly) { this.dividedMonthly = dividedMonthly; }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", targetAmount=" + targetAmount +
                ", startDateMillis=" + startDateMillis +
                ", endDateMillis=" + endDateMillis +
                ", dividedMonthly=" + dividedMonthly +
                '}';
    }
}