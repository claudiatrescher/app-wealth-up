package com.example.wealthup.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wealthup.R;
import com.example.wealthup.fragment.FullIncomesListFragment;
import com.example.wealthup.fragment.IncomeChartFragment;
import com.example.wealthup.ui.dialog.AddIncomeDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomesActivity extends AppCompatActivity implements
        IncomeChartFragment.OnSeeAllIncomesClickListener {

    private FloatingActionButton fabAddIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_incomes);

        fabAddIncome = findViewById(R.id.fab_add_income);

        if (savedInstanceState == null) {
            loadFragment(new IncomeChartFragment(), false);
        }

        fabAddIncome.setOnClickListener(v -> {
            AddIncomeDialogFragment dialogFragment = new AddIncomeDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddIncomeDialogFragment");
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