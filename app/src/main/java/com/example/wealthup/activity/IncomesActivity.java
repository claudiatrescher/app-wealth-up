package com.example.wealthup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.wealthup.fragment.FullIncomesListFragment;
import com.example.wealthup.fragment.IncomeChartFragment;
import com.example.wealthup.ui.dialog.AddIncomeDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomesActivity extends AppCompatActivity implements
        IncomeChartFragment.OnSeeAllIncomesClickListener {

    private FloatingActionButton fabAddIncome;
    private BottomNavigationView bottomNavigationView;
    private TextView nameUserTextInHeader;
    private ImageView profileImageInHeader;
    private ImageView buttonExitApp;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_incomes);

        fabAddIncome = findViewById(R.id.fab_add_income);
        bottomNavigationView = findViewById(R.id.bottomNavInclude);

        if (savedInstanceState == null) {
            loadFragment(new IncomeChartFragment(), false);
        }

        fabAddIncome.setOnClickListener(v -> {
            AddIncomeDialogFragment dialogFragment = new AddIncomeDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddIncomeDialogFragment");
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_gastos) {
                    Intent intent = new Intent(IncomesActivity.this, ExpensesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(IncomesActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_ganhos) {
                    return true;
                } else if (itemId == R.id.nav_categorias) {
                    Intent intent = new Intent(IncomesActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_gastos_fixos) {
                    Intent intent = new Intent(IncomesActivity.this, FixedExpensesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.nav_ganhos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.incomes_fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.incomes_fragment_container, fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onSeeAllIncomesClick() {
        loadFragment(new FullIncomesListFragment(), true);
    }

}