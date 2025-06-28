package com.example.wealthup.activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.CategoryAdapter;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity
        implements AddCategoryDialogFragment.OnCategoryAddedListener {

    private CategoryAdapter previewCategoryAdapter;
    private RecyclerView recycler_view_expenses;
    private ImageButton addCategory;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

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

        List<CategoryModel> categories = getCategories();
        viewAllCategories(categories);
    }

    public void viewAllCategories(List<CategoryModel> category){
        previewCategoryAdapter.updateList(category);
    }

    public List<CategoryModel> getCategories() {
        CategoryDao dao = new CategoryDao(this);
        return dao.SelectAll(preferences.getInt("KEY_ID", 0));
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