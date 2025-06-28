package com.example.wealthup.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.CategoryAdapter;
import com.example.wealthup.adapter.FixedExpenseAdapter;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.dao.FixedExpenseDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.FixedExpenseModel;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;
import com.example.wealthup.ui.dialog.AddFixedExpenseDialogFragment;
import com.example.wealthup.ui.dialog.AddGoalDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FixedExpensesActivity extends AppCompatActivity
        implements AddFixedExpenseDialogFragment.OnFixedExpenseAddedListener {

    private ImageButton addFixedExpense;
    private FixedExpenseAdapter previewFixedExpenseAdapter;
    private RecyclerView recycler_view_fixed_expense;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_expenses_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();

        addFixedExpense = findViewById(R.id.addFixedExpense);

        recycler_view_fixed_expense = findViewById(R.id.recycler_view_fixed_expense);
        recycler_view_fixed_expense.setLayoutManager(new LinearLayoutManager(FixedExpensesActivity.this));
        previewFixedExpenseAdapter = new FixedExpenseAdapter(new ArrayList<>());
        recycler_view_fixed_expense.setAdapter(previewFixedExpenseAdapter);
        recycler_view_fixed_expense.setNestedScrollingEnabled(false);

        addFixedExpense.setOnClickListener(v -> showAddFixedExpenseModal());

        List<FixedExpenseModel> categories = getFixedExpenses();
        viewAllFixedExpenses(categories);

    }

    public void viewAllFixedExpenses(List<FixedExpenseModel> fixedExpenses){
        previewFixedExpenseAdapter.updateList(fixedExpenses);
    }

    public List<FixedExpenseModel> getFixedExpenses() {
        FixedExpenseDao dao = new FixedExpenseDao(this);
        return dao.SelectAll(preferences.getInt("KEY_ID", 0));
    }

    private void showAddFixedExpenseModal() {
        AddFixedExpenseDialogFragment dialog = new AddFixedExpenseDialogFragment();
        dialog.setOnFixedExpenseAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddFixedExpenseModal");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFixedExpenseAdded() {
        List<FixedExpenseModel> fixedExpenses = getFixedExpenses();
        viewAllFixedExpenses(fixedExpenses);
    }
}