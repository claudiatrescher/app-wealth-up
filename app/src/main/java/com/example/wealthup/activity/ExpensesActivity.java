package com.example.wealthup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wealthup.R;
import com.example.wealthup.fragment.ChartAndPreviewFragment;
import com.example.wealthup.fragment.FullExpensesListFragment;
import com.example.wealthup.ui.dialog.AddExpenseDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpensesActivity extends AppCompatActivity implements
        ChartAndPreviewFragment.OnSeeAllExpensesClickListener {

    private FloatingActionButton fabAddExpense;
    private BottomNavigationView bottomNavigationView;
    private TextView nameUserTextInHeader;
    private ImageView profileImageInHeader;
    private ImageView buttonExitApp;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_expenses);

        fabAddExpense = findViewById(R.id.fab_add_expense);
        bottomNavigationView = findViewById(R.id.bottomNavInclude);

        if (savedInstanceState == null) {
            loadFragment(new ChartAndPreviewFragment(), false);
        }

        fabAddExpense.setOnClickListener(v -> {
            AddExpenseDialogFragment dialogFragment = new AddExpenseDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddExpenseDialogFragment");
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_gastos) {
                    return true;
                } else if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(ExpensesActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_ganhos) {
                    Intent intent = new Intent(ExpensesActivity.this, IncomesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_categorias) {
                    Intent intent = new Intent(ExpensesActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_gastos_fixos) {
                    Intent intent = new Intent(ExpensesActivity.this, FixedExpensesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.nav_gastos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.expenses_fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.expenses_fragment_container, fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }



    @Override
    public void onSeeAllExpensesClick() {
        loadFragment(new FullExpensesListFragment(), true);
    }

}