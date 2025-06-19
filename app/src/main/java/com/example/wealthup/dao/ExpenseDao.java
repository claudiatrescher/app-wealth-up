package com.example.wealthup.dao;

import com.example.wealthup.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDao {

    private static List<Expense> dummyExpenses = new ArrayList<>();

    static {
        dummyExpenses.add(new Expense("Estudos", "26 de maio", "Livros", "R$ 25,00"));
        dummyExpenses.add(new Expense("Comida", "26 de maio", "Hamburguer", "R$ 50,00"));
        dummyExpenses.add(new Expense("Roupa", "26 de maio", "Blusa", "R$ 75,00"));
        dummyExpenses.add(new Expense("Compras", "26 de maio", "Brinco", "R$ 169,99"));
        dummyExpenses.add(new Expense("Estudos", "25 de maio", "Curso Online", "R$ 120,00"));
        dummyExpenses.add(new Expense("Transporte", "24 de maio", "Uber", "R$ 30,00"));
        dummyExpenses.add(new Expense("Lazer", "23 de maio", "Cinema", "R$ 40,00"));
        dummyExpenses.add(new Expense("Saúde", "22 de maio", "Farmácia", "R$ 15,00"));
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(dummyExpenses);
    }

}