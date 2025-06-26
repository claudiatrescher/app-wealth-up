package com.example.wealthup.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.ExpenseAdapter;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.dao.ExpenseDao;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpensesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<ExpenseModel> allExpens;
    private MaterialButtonToggleGroup timeFilterToggleGroup;
    private ExpenseDao expenseDao;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expenseslist);

        recyclerView = findViewById(R.id.recycler_view_expenses);
        timeFilterToggleGroup = findViewById(R.id.time_filter_toggle_group);

        preferences = PreferenceManager.getDefaultSharedPreferences(ExpensesActivity.this);
        edit = preferences.edit();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseAdapter = new ExpenseAdapter(allExpens);
        recyclerView.setAdapter(expenseAdapter);

        timeFilterToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.button_dia) {
                    filterExpenses("Dia");
                } else if (checkedId == R.id.button_semana) {
                    filterExpenses("Semana");
                } else if (checkedId == R.id.button_mes) {
                    filterExpenses("Mês");
                }
            }
        });

        if (timeFilterToggleGroup.getCheckedButtonId() == R.id.button_mes) {
            filterExpenses("Mês");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.expenses_layout_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void filterExpenses(String filterType) {
        List<ExpenseModel> currentFilteredList;

        switch (filterType) {
            case "Dia":
                currentFilteredList = allExpens.stream()
                        .filter(expenseModel -> expenseModel.getDate().equals("26 de maio"))
                        .collect(Collectors.toList());
                break;
            case "Semana":
                currentFilteredList = allExpens.stream()
                        .filter(expenseModel -> expenseModel.getDate().equals("26 de maio") || expenseModel.getDate().equals("25 de maio") || expenseModel.getDate().equals("24 de maio"))
                        .collect(Collectors.toList());
                break;
            case "Mês":
            default:
                currentFilteredList = new ArrayList<>(allExpens);
                break;
        }

        expenseAdapter.updateList(currentFilteredList);

    }
}