package com.example.wealthup.ui.dialog;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.dao.FixedExpenseDao;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.FixedExpenseModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFixedExpenseDialogFragment extends DialogFragment {

    private EditText editTextName, editTextValue, editTextDueDate;
    private Button buttonCancel, buttonSave;
    private Calendar selectedDate;

    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    public interface OnFixedExpenseAddedListener {
        void onFixedExpenseAdded();
    }

    private OnFixedExpenseAddedListener listener;

    public void setOnFixedExpenseAddedListener(OnFixedExpenseAddedListener listener) {
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
        View view = inflater.inflate(R.layout.activity_add_fixed_expense_modal, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        editTextName = view.findViewById(R.id.editTextFixedExpenseName);
        editTextValue = view.findViewById(R.id.editTextFixedExpenseValue);
        editTextDueDate = view.findViewById(R.id.editTextFixedExpenseDueDate);
        buttonCancel = view.findViewById(R.id.buttonCancelFixedExpense);
        buttonSave = view.findViewById(R.id.buttonSaveFixedExpense);

        selectedDate = Calendar.getInstance();

        editTextDueDate.setOnClickListener(v -> showDatePickerDialog());
        editTextDueDate.setFocusable(false);

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String valueStr = editTextValue.getText().toString().trim();

            if (name.isEmpty()) {
                editTextName.setError("Nome é obrigatório");
                return;
            }
            if (valueStr.isEmpty()) {
                editTextValue.setError("Valor é obrigatório");
                return;
            }
            if (editTextDueDate.getText().toString().isEmpty()) {
                editTextDueDate.setError("Data de cobrança é obrigatória");
                return;
            }

            try {
                double value = Double.parseDouble(valueStr);
                long dueDateMillis = selectedDate.getTimeInMillis();

                FixedExpenseDao expenseDao = new FixedExpenseDao(getContext());
                FixedExpenseModel newExpense = new FixedExpenseModel(name, value, dueDateMillis, "Gasto Fixo", preferences.getInt("KEY_ID", 0));

                int result = expenseDao.Insert(newExpense);
                if (result != -1) {
                    Toast.makeText(getContext(), "Gasto fixo adicionado!", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onFixedExpenseAdded();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar gasto fixo.", Toast.LENGTH_SHORT).show();
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
                    editTextDueDate.setText(sdf.format(selectedDate.getTime()));
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}