package com.example.wealthup.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.wealthup.R;
import com.example.wealthup.dao.DatabaseHelper;
import com.example.wealthup.model.FixedExpense;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;
import com.example.wealthup.ui.dialog.AddFixedExpenseDialogFragment;
import com.example.wealthup.ui.dialog.AddGoalDialogFragment;

import java.util.concurrent.TimeUnit;

public class InformationsActivity extends AppCompatActivity
        implements AddCategoryDialogFragment.OnCategoryAddedListener,
        AddFixedExpenseDialogFragment.OnFixedExpenseAddedListener,
        AddGoalDialogFragment.OnGoalAddedListener {

    private DatabaseHelper dbHelper;
    private CardView notificationCard;
    private TextView notificationMessageTextView;
    private TextView notificationItemNameTextView;

    private ImageButton btnCategory, btnFixedExpenses, btnGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        dbHelper = new DatabaseHelper(this);

        notificationCard = findViewById(R.id.notificationCard);
        notificationMessageTextView = findViewById(R.id.notificationMessageTextView);
        notificationItemNameTextView = findViewById(R.id.notificationItemNameTextView);

        btnCategory = findViewById(R.id.btnCalendar);
        btnFixedExpenses = findViewById(R.id.btnFixedExpenses);
        btnGoals = findViewById(R.id.btnGoals);

        btnCategory.setOnClickListener(v -> showAddCategoryModal());
        btnFixedExpenses.setOnClickListener(v -> showAddFixedExpenseModal());
        btnGoals.setOnClickListener(v -> showAddGoalModal());

        loadNearestFixedExpenseNotification();
    }

    private void loadNearestFixedExpenseNotification() {
        int daysToCheck = 30;
        FixedExpense nearestExpense = dbHelper.getNearestUpcomingFixedExpense(daysToCheck);

        if (nearestExpense != null) {
            long diffMillis = nearestExpense.getDueDateMillis() - System.currentTimeMillis();
            long daysUntilDue = TimeUnit.MILLISECONDS.toDays(diffMillis);

            String message;
            if (daysUntilDue == 0) {
                message = "A despesa fixa será cobrada HOJE";
            } else if (daysUntilDue == 1) {
                message = "A despesa fixa será cobrada em 1 dia";
            } else {
                message = "A despesa fixa será cobrada em " + daysUntilDue + " dias";
            }

            notificationMessageTextView.setText(message);
            notificationItemNameTextView.setText(nearestExpense.getName());
            notificationCard.setVisibility(View.VISIBLE);
        } else {
            notificationCard.setVisibility(View.GONE);
        }
    }

    private void showAddCategoryModal() {
        AddCategoryDialogFragment dialog = new AddCategoryDialogFragment();
        dialog.setOnCategoryAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddCategoryModal");
    }

    private void showAddFixedExpenseModal() {
        AddFixedExpenseDialogFragment dialog = new AddFixedExpenseDialogFragment();
        dialog.setOnFixedExpenseAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddFixedExpenseModal");
    }

    private void showAddGoalModal() {
        AddGoalDialogFragment dialog = new AddGoalDialogFragment();
        dialog.setOnGoalAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddGoalModal");
    }

    @Override
    public void onCategoryAdded() {
    }

    @Override
    public void onFixedExpenseAdded() {
        loadNearestFixedExpenseNotification();
    }

    @Override
    public void onGoalAdded() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}