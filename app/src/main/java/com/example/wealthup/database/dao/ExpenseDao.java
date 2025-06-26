package com.example.wealthup.database.dao;

import com.example.wealthup.database.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDao {

    private static List<ExpenseModel> dummyExpens = new ArrayList<>();

    static {
    }

    public List<ExpenseModel> getAllExpenses() {
        return new ArrayList<>(dummyExpens);
    }

}