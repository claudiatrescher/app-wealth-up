package com.example.wealthup.activity;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wealthup.R;
import com.example.wealthup.dao.DatabaseHelper;
import com.example.wealthup.ui.dialog.AddCategoryDialogFragment;

public class CategoryActivity extends AppCompatActivity
        implements AddCategoryDialogFragment.OnCategoryAddedListener {

    private DatabaseHelper dbHelper;

    private ImageButton addCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        dbHelper = new DatabaseHelper(this);

        addCategory = findViewById(R.id.addCategory);

        addCategory.setOnClickListener(v -> showAddCategoryModal());
    }

    private void showAddCategoryModal() {
        AddCategoryDialogFragment dialog = new AddCategoryDialogFragment();
        dialog.setOnCategoryAddedListener(this);
        dialog.show(getSupportFragmentManager(), "AddCategoryModal");
    }

    @Override
    public void onCategoryAdded() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}