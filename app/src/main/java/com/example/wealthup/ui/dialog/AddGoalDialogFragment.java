package com.example.wealthup.ui.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wealthup.R;
import com.example.wealthup.dao.DatabaseHelper;
import com.example.wealthup.model.Goal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGoalDialogFragment extends DialogFragment {

    private EditText editTextName, editTextValue, editTextStartDate, editTextEndDate;
    private CheckBox checkBoxDividedMonthly;
    private Button buttonCancel, buttonSave;
    private DatabaseHelper dbHelper;
    private Calendar selectedStartDate, selectedEndDate;

    public interface OnGoalAddedListener {
        void onGoalAdded();
    }

    private OnGoalAddedListener listener;

    public void setOnGoalAddedListener(OnGoalAddedListener listener) {
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
        View view = inflater.inflate(R.layout.activity_add_goal_modal, container, false);

        dbHelper = new DatabaseHelper(getContext());

        editTextName = view.findViewById(R.id.editTextGoalName);
        editTextValue = view.findViewById(R.id.editTextGoalValue);
        editTextStartDate = view.findViewById(R.id.editTextGoalStartDate);
        editTextEndDate = view.findViewById(R.id.editTextGoalEndDate);
        checkBoxDividedMonthly = view.findViewById(R.id.checkBoxDividedMonthly);
        buttonCancel = view.findViewById(R.id.buttonCancelGoal);
        buttonSave = view.findViewById(R.id.buttonSaveGoal);

        selectedStartDate = Calendar.getInstance();
        selectedEndDate = Calendar.getInstance();
        selectedEndDate.add(Calendar.MONTH, 1);

        editTextStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        editTextStartDate.setFocusable(false);
        editTextStartDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedStartDate.getTime()));

        editTextEndDate.setOnClickListener(v -> showDatePickerDialog(false));
        editTextEndDate.setFocusable(false);
        editTextEndDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedEndDate.getTime()));


        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String valueStr = editTextValue.getText().toString().trim();
            boolean dividedMonthly = checkBoxDividedMonthly.isChecked();

            if (name.isEmpty()) {
                editTextName.setError("Nome da meta é obrigatório");
                return;
            }
            if (valueStr.isEmpty()) {
                editTextValue.setError("Valor da meta é obrigatório");
                return;
            }
            if (selectedStartDate.after(selectedEndDate)) {
                Toast.makeText(getContext(), "Data de início não pode ser depois da data de conclusão.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                double targetAmount = Double.parseDouble(valueStr);
                long startDateMillis = selectedStartDate.getTimeInMillis();
                long endDateMillis = selectedEndDate.getTimeInMillis();

                Goal newGoal = new Goal(0, name, targetAmount, startDateMillis, endDateMillis, dividedMonthly);
                long result = dbHelper.addGoal(newGoal);

                if (result != -1) {
                    Toast.makeText(getContext(), "Meta adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onGoalAdded();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar meta.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                editTextValue.setError("Valor inválido");
            }
        });

        return view;
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        Calendar currentCalendar = isStartDate ? selectedStartDate : selectedEndDate;
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    currentCalendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    if (isStartDate) {
                        editTextStartDate.setText(sdf.format(currentCalendar.getTime()));
                    } else {
                        editTextEndDate.setText(sdf.format(currentCalendar.getTime()));
                    }
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
