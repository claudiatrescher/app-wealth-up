package com.example.wealthup.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.wealthup.R;
import com.example.wealthup.database.model.FixedExpenseModel;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;
import com.example.wealthup.ui.dialog.AddFixedExpenseDialogFragment;
import com.example.wealthup.ui.dialog.AddGoalDialogFragment;

import java.util.concurrent.TimeUnit;

public class FixedExpensesActivity extends AppCompatActivity
        implements AddFixedExpenseDialogFragment.OnFixedExpenseAddedListener {

    private ImageButton addFixedExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_expenses_list);

        addFixedExpense = findViewById(R.id.addFixedExpense);

        addFixedExpense.setOnClickListener(v -> showAddFixedExpenseModal());

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

    }
}