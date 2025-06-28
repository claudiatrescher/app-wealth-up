package com.example.wealthup.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wealthup.R;
import com.example.wealthup.adapter.CategoryAdapter;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity
    implements AddCategoryDialogFragment.OnCategoryAddedListener {

    private CategoryAdapter previewCategoryAdapter;
    private RecyclerView recycler_view_expenses;
    private ImageButton addCategory;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();

        recycler_view_expenses = findViewById(R.id.recycler_view_category);
        recycler_view_expenses.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        previewCategoryAdapter = new CategoryAdapter(new ArrayList<>());
        recycler_view_expenses.setAdapter(previewCategoryAdapter);
        recycler_view_expenses.setNestedScrollingEnabled(false);

        addCategory = findViewById(R.id.addCategory);

        addCategory.setOnClickListener(v -> showAddCategoryModal());
        bottomNavigationView = findViewById(R.id.bottomNavInclude);

        List<CategoryModel> categoryList = getCategories();
        viewAllCategories(categoryList);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_gastos) {
                    Intent intent = new Intent(CategoryActivity.this, ExpensesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_ganhos) {
                    Intent intent = new Intent(CategoryActivity.this, IncomesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_categorias) {
                    return true;
                } else if (itemId == R.id.nav_gastos_fixos) {
                    Intent intent = new Intent(CategoryActivity.this, FixedExpensesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_categorias);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categories_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public List<CategoryModel> getCategories() {
        CategoryDao dao = new CategoryDao(this);
        return dao.SelectAll(preferences.getInt("KEY_ID", 0));
    }

    public void viewAllCategories(List<CategoryModel> category){
        previewCategoryAdapter.updateList(category);
    }

    private void showAddCategoryModal() {
        AddCategoryDialogFragment dialog = new AddCategoryDialogFragment();
        dialog.setOnCategoryAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddCategoryModal");
    }

    @Override
    public void onCategoryAdded() {
        List<CategoryModel> categorias = getCategories();
        viewAllCategories(categorias);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}