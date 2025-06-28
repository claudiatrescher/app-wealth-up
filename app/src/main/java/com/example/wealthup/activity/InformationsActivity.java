package com.example.wealthup.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.CategoryExpenseAdapter;
import com.example.wealthup.adapter.FixedExpenseAdapter;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.dao.FixedExpenseDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.FixedExpenseModel;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;
import com.example.wealthup.ui.dialog.AddFixedExpenseDialogFragment;
import com.example.wealthup.ui.dialog.AddGoalDialogFragment;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InformationsActivity extends AppCompatActivity
        implements AddCategoryDialogFragment.OnCategoryAddedListener,
        AddFixedExpenseDialogFragment.OnFixedExpenseAddedListener,
        AddGoalDialogFragment.OnGoalAddedListener {

    private CardView notificationCard;
    private TextView notificationMessageTextView;
    private TextView notificationItemNameTextView;
    private CategoryExpenseAdapter previewCategoryAdapter;
    private RecyclerView recycler_view_expenses;
    private ImageButton btnCategory, btnFixedExpenses, btnGoals;
    private MaterialButtonToggleGroup time_filter_toggle_group;
    private String filterType;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();

        filterType = "Goals";

        notificationCard = findViewById(R.id.notificationCard);
        notificationMessageTextView = findViewById(R.id.notificationMessageTextView);
        notificationItemNameTextView = findViewById(R.id.notificationItemNameTextView);

        btnCategory = findViewById(R.id.btnCategoryList);
        btnFixedExpenses = findViewById(R.id.btnFixedExpenses);
        btnGoals = findViewById(R.id.btnGoalsList);

        time_filter_toggle_group = findViewById(R.id.time_filter_toggle_group);

        recycler_view_expenses = findViewById(R.id.recycler_view_expenses);

        recycler_view_expenses.setLayoutManager(new LinearLayoutManager(InformationsActivity.this));

        previewCategoryAdapter = new CategoryExpenseAdapter(new ArrayList<>());

        recycler_view_expenses.setAdapter(previewCategoryAdapter);
        recycler_view_expenses.setNestedScrollingEnabled(false);

        btnCategory.setOnClickListener(v -> showAddCategoryModal());
        btnFixedExpenses.setOnClickListener(v -> showAddFixedExpenseModal());
        btnGoals.setOnClickListener(v -> showAddGoalModal());

        loadNearestFixedExpenseNotification();

        time_filter_toggle_group.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnCategoryList) {
                    filterType = "Category";
                    Log.d("DEBUG", "onCreate: Category");
                    recycler_view_expenses.setAdapter(previewCategoryAdapter);
                    List<CategoryModel> categories = getCategories();
                    viewCategories(categories);

                } else if (checkedId == R.id.btnFixedExpensesList) {
                    filterType = "Expenses";
                } else {
                    Log.d("DEBUG", "onCreate: A");
                    filterType = "Goals";
                    viewCategories(new ArrayList<>());
                }
            }
        });
    }

    public void viewCategories(List<CategoryModel> category){
        List<CategoryModel> previewList = new ArrayList<>();
        int itemsToShow = Math.min(category.size(), 3);
        for (int i = 0; i < itemsToShow; i++) {
            previewList.add(category.get(i));
        }
        previewCategoryAdapter.updateList(previewList);
    }

    public List<CategoryModel> getCategories() {
        CategoryDao dao = new CategoryDao(this);
        return dao.SelectAll(preferences.getInt("KEY_ID", 0));
    }

    private void loadNearestFixedExpenseNotification() {
        int daysToCheck = 30;
        FixedExpenseModel nearestExpense = null;// dbHelper.getNearestUpcomingFixedExpense(daysToCheck);

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
    public void onFixedExpenseAdded() {
        loadNearestFixedExpenseNotification();
    }

    @Override
    public void onGoalAdded() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCategoryAdded() {

    }
}