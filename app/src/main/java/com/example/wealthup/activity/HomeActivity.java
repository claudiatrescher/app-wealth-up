package com.example.wealthup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wealthup.activity.ExpensesActivity;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.dao.IncomeDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.IncomeModel;
import com.example.wealthup.view.MyBarChartView;
import com.example.wealthup.R;
import com.example.wealthup.view.MyBarChartView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private MyBarChartView myChartView;
    private BottomNavigationView bottomNavigationView;
    private TextView nameUserTextInHeader;
    private ImageView profileImageInHeader;
    private ImageView buttonExitApp;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    private TextView totalExpenses;
    private TextView totalIncomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);


        LinearLayout commonHeader = findViewById(R.id.headerLayout);

        nameUserTextInHeader = commonHeader.findViewById(R.id.nameUserText);
        profileImageInHeader = commonHeader.findViewById(R.id.profileImage);
        buttonExitApp = commonHeader.findViewById(R.id.buttonExitApp);

        Calendar calendar = Calendar.getInstance();

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        edit = preferences.edit();

        totalExpenses = findViewById(R.id.totalExpenses);
        totalIncomes = findViewById(R.id.totalIncomes);

        ExpenseDao eDao = new ExpenseDao(HomeActivity.this);
        IncomeDao iDao = new IncomeDao(HomeActivity.this);

        double totalIncomesValue = 0;
        double totalExpensesValue = 0;
        List<IncomeModel> incomeList = iDao.SelectByMonth(calendar.get(Calendar.MONTH) + 1, preferences.getInt("KEY_ID", 0));
        List<ExpenseModel> expenseList = eDao.SelectByMonth(calendar.get(Calendar.MONTH) + 1, preferences.getInt("KEY_ID", 0));

        for (IncomeModel income : incomeList) {
            totalIncomesValue += income.getAmount();
        }
        for (ExpenseModel expense : expenseList) {
            totalExpensesValue += expense.getAmount();
        }

        totalExpenses.setText(String.valueOf(totalExpensesValue));
        totalIncomes.setText(String.valueOf(totalIncomesValue));

        bottomNavigationView = findViewById(R.id.bottomNavInclude);


        if(preferences.getBoolean("FIRST", false)) {
            insertCategories();
            edit.putBoolean("FIRST", false);
            edit.apply();
        }

        nameUserTextInHeader = findViewById(R.id.nameUserText);
        nameUserTextInHeader.setText(preferences.getString("KEY_NAME", ""));
        nameUserTextInHeader.setText(preferences.getString("KEY_NAME", ""));

        buttonExitApp.setOnClickListener(v -> {
            showExitConfirmationDialog();
        });


        if (myChartView != null) {
            List<Float> values = Arrays.asList(0.6f, 0.4f, 0.8f, 0.65f, 0.3f, 0.7f, 0.5f);
            int highlightIndex = 2;

            myChartView.setBarValues(values, highlightIndex);
        } else {
            Log.e("HomeActivity", "myChartView is null!");
        }

        Button btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnViewExpenses.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ExpensesActivity.class);
            startActivity(intent);
        });

        Button btnViewIncomes = findViewById(R.id.btnViewIncomes);
        btnViewIncomes.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, IncomesActivity.class);
            startActivity(intent);
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_gastos) {
                    Intent intent = new Intent(HomeActivity.this, ExpensesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_ganhos) {
                    Intent intent = new Intent(HomeActivity.this, IncomesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_categorias) {
                    Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_gastos_fixos) {
                    Intent intent = new Intent(HomeActivity.this, FixedExpensesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
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
    }

    public void insertCategories(){
        CategoryDao dao = new CategoryDao(this);

        String[] categories = {"Alimentação","Transporte", "Saúde", "Educação", "Lazer", "Comprar", "Outros"};

        for (String category : categories) {
            CategoryModel model = new CategoryModel(category, "#FF0000", preferences.getInt("KEY_ID", 0));
            int result = dao.Insert(model);
        }
    }
}