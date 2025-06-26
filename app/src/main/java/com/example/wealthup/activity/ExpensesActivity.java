package com.example.wealthup.activity; // Ou o pacote onde você deseja que esta Activity esteja

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wealthup.R;
import com.example.wealthup.fragment.ChartAndPreviewFragment;
import com.example.wealthup.fragment.FullExpensesListFragment;
import com.example.wealthup.ui.dialog.AddExpenseDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpensesActivity extends AppCompatActivity implements
        ChartAndPreviewFragment.OnSeeAllExpensesClickListener {

    private FloatingActionButton fabAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_expenses);

        fabAddExpense = findViewById(R.id.fab_add_expense); // Encontre o FAB

        if (savedInstanceState == null) {
            loadFragment(new ChartAndPreviewFragment(), false); // false para não adicionar na back stack inicial
        }

        fabAddExpense.setOnClickListener(v -> {
            AddExpenseDialogFragment dialogFragment = new AddExpenseDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddExpenseDialogFragment");
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