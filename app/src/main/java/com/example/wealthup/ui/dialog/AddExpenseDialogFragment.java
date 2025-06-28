package com.example.wealthup.ui.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.ExpenseModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExpenseDialogFragment extends DialogFragment {

    private static final String TAG = "AddExpenseDialog";
    private TextInputEditText editTextName, editTextValue, editTextDate, editTextCategory;
    private Button buttonCancel, buttonSave;
    private Calendar selectedDate;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;



    public interface OnExpenseAddedListener {
        void onExpenseAdded();
    }

    private OnExpenseAddedListener listener;

    public void setOnExpenseAddedListener(OnExpenseAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_expense, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        editTextName = view.findViewById(R.id.editTextExpenseName);
        editTextValue = view.findViewById(R.id.editTextExpenseValue);
        editTextDate = view.findViewById(R.id.editTextExpenseDate);
        editTextCategory = view.findViewById(R.id.editTextExpenseCategory);
        buttonCancel = view.findViewById(R.id.buttonCancelExpense);
        buttonSave = view.findViewById(R.id.buttonSaveExpense);

        selectedDate = Calendar.getInstance();

        editTextDate.setOnClickListener(v -> showDatePickerDialog());
        editTextDate.setFocusable(false);
        editTextDate.setKeyListener(null);

        editTextCategory.setOnClickListener(v -> showCategoryPickerDialog());
        editTextCategory.setFocusable(false);
        editTextCategory.setKeyListener(null);

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String valueStr = editTextValue.getText().toString().trim();
            String category = editTextCategory.getText().toString().trim();

            if (name.isEmpty()) {
                editTextName.setError("Descrição é obrigatória");
                return;
            }
            if (valueStr.isEmpty()) {
                editTextValue.setError("Valor é obrigatório");
                return;
            }
            if (editTextDate.getText().toString().isEmpty()) {
                editTextDate.setError("Data do gasto é obrigatória");
                return;
            }
            if (category.isEmpty()) {
                editTextCategory.setError("Categoria é obrigatória");
                return;
            }

            try {
                double value = Double.parseDouble(valueStr);
                long dateMillis = selectedDate.getTimeInMillis();

                ExpenseDao expenseDao = new ExpenseDao(getContext());
                ExpenseModel newExpense = new ExpenseModel(name, value, dateMillis, category, preferences.getInt("KEY_ID", 0));

                int result = expenseDao.Insert(newExpense);
                Log.d(TAG, "Resultado da inserção: " + result);
                if (result != -1) {
                    Toast.makeText(getContext(), "Gasto adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onExpenseAdded();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar gasto.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                editTextValue.setError("Valor inválido");
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editTextDate.setText(sdf.format(selectedDate.getTime()));
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showCategoryPickerDialog() {
        final List<String> categories = new ArrayList<>();

        CategoryDao dao = new CategoryDao(getContext());

        List<CategoryModel> categoriesList = dao.SelectAll(preferences.getInt("KEY_ID", 0));
        for (CategoryModel category : categoriesList) {
            categories.add(category.getName());
        }
        String[] categoriesArray = categories.toArray(new String[0]);

        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Selecione a Categoria")
                .setItems(categoriesArray, (dialog, which) -> {
                    editTextCategory.setText(categoriesArray[which]);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}