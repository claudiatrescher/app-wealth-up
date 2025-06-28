package com.example.wealthup.ui.dialog;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.dao.IncomeDao;
import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.IncomeModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddIncomeDialogFragment extends DialogFragment {

    private TextInputEditText editTextIncomeName, editTextIncomeValue, editTextIncomeDate, editTextIncomeCategory;
    private Button buttonCancelIncome, buttonSaveIncome;
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
        View view = inflater.inflate(R.layout.activity_add_income, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        editTextIncomeName = view.findViewById(R.id.editTextIncomeName);
        editTextIncomeValue = view.findViewById(R.id.editTextIncomeValue);
        editTextIncomeDate = view.findViewById(R.id.editTextIncomeDate);
        editTextIncomeCategory = view.findViewById(R.id.editTextIncomeCategory);
        buttonCancelIncome = view.findViewById(R.id.buttonCancelIncome);
        buttonSaveIncome = view.findViewById(R.id.buttonSaveIncome);

        selectedDate = Calendar.getInstance();

        editTextIncomeDate.setOnClickListener(v -> showDatePickerDialog());
        editTextIncomeDate.setFocusable(false);
        editTextIncomeDate.setKeyListener(null);

        editTextIncomeCategory.setOnClickListener(v -> showCategoryPickerDialog());
        editTextIncomeCategory.setFocusable(false);
        editTextIncomeCategory.setKeyListener(null);

        buttonCancelIncome.setOnClickListener(v -> dismiss());

        buttonSaveIncome.setOnClickListener(v -> {
            String name = editTextIncomeName.getText().toString().trim();
            String valueStr = editTextIncomeValue.getText().toString().trim();
            String category = editTextIncomeCategory.getText().toString().trim();

            if (name.isEmpty()) {
                editTextIncomeName.setError("Descrição é obrigatória");
                return;
            }
            if (valueStr.isEmpty()) {
                editTextIncomeValue.setError("Valor é obrigatório");
                return;
            }
            if (editTextIncomeDate.getText().toString().isEmpty()) {
                editTextIncomeDate.setError("Data do gasto é obrigatória");
                return;
            }
            if (category.isEmpty()) {
                editTextIncomeCategory.setError("Categoria é obrigatória");
                return;
            }

            try {
                double value = Double.parseDouble(valueStr);
                long dateMillis = selectedDate.getTimeInMillis();
                IncomeModel newIncome = new IncomeModel(name, value, dateMillis, category, preferences.getInt("KEY_ID", 0));
                IncomeDao dao = new IncomeDao(getContext());

                long result = dao.Insert(newIncome);

                if (result != -1) {
                    Toast.makeText(getContext(), "Ganho adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onExpenseAdded();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar ganho.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                editTextIncomeValue.setError("Valor inválido");
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
                    editTextIncomeDate.setText(sdf.format(selectedDate.getTime()));
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
                    editTextIncomeCategory.setText(categoriesArray[which]);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}