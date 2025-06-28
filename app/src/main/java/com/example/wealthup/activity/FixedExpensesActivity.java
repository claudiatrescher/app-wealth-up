package com.example.wealthup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private BottomNavigationView bottomNavigationView;
    private TextView nameUserTextInHeader;
    private ImageView profileImageInHeader;
    private ImageView buttonExitApp;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_expenses_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();


        LinearLayout commonHeader = findViewById(R.id.commonHeaderLayout);

        nameUserTextInHeader = commonHeader.findViewById(R.id.nameUserText);
        profileImageInHeader = commonHeader.findViewById(R.id.profileImage);
        buttonExitApp = commonHeader.findViewById(R.id.buttonExitApp);

        addFixedExpense = findViewById(R.id.addFixedExpense);
        bottomNavigationView = findViewById(R.id.bottomNavInclude);

        recycler_view_fixed_expense = findViewById(R.id.recycler_view_fixed_expense);
        recycler_view_fixed_expense.setLayoutManager(new LinearLayoutManager(FixedExpensesActivity.this));
        previewFixedExpenseAdapter = new FixedExpenseAdapter(new ArrayList<>());
        recycler_view_fixed_expense.setAdapter(previewFixedExpenseAdapter);
        recycler_view_fixed_expense.setNestedScrollingEnabled(false);

        addFixedExpense.setOnClickListener(v -> showAddFixedExpenseModal());
        preferences = PreferenceManager.getDefaultSharedPreferences(FixedExpensesActivity.this);
        edit = preferences.edit();

        nameUserTextInHeader.setText(preferences.getString("KEY_NAME", ""));

        buttonExitApp.setOnClickListener(v -> {
            showExitConfirmationDialog();
        });


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_gastos) {
                    Intent intent = new Intent(FixedExpensesActivity.this, ExpensesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(FixedExpensesActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_ganhos) {
                    Intent intent = new Intent(FixedExpensesActivity.this, IncomesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_categorias) {
                    Intent intent = new Intent(FixedExpensesActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_gastos_fixos) {
                    return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.nav_gastos_fixos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fixed_expenses_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sair do Aplicativo")
                .setMessage("Tem certeza que deseja sair?")
                .setPositiveButton("Sim", (dialog, which) -> {

                    finishAffinity();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
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