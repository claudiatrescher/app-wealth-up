package com.example.wealthup.ui.dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.model.CategoryModel;


public class AddCategoryDialogFragment extends DialogFragment {

    private EditText editTextCategoryName;
    private RadioGroup radioGroupColors;
    private Button buttonCancel, buttonSave;

    SharedPreferences.Editor edit;
    SharedPreferences preferences;


    public interface OnCategoryAddedListener {
        void onCategoryAdded();
    }

    private OnCategoryAddedListener listener;

    public void setOnCategoryAddedListener(OnCategoryAddedListener listener) {
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
        View view = inflater.inflate(R.layout.activity_add_category_modal, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        editTextCategoryName = view.findViewById(R.id.editTextCategoryName);
        radioGroupColors = view.findViewById(R.id.radioGroupColors);
        buttonCancel = view.findViewById(R.id.buttonCancelCategory);
        buttonSave = view.findViewById(R.id.buttonSaveCategory);

        radioGroupColors.check(R.id.radioColorRed);

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> {
            String categoryName = editTextCategoryName.getText().toString().trim();
            String selectedColor = "";

            int selectedId = radioGroupColors.getCheckedRadioButtonId();
            if (selectedId == R.id.radioColorRed) {
                selectedColor = "#FF5733";
            } else if (selectedId == R.id.radioColorBlue) {
                selectedColor = "#3366FF";
            } else if (selectedId == R.id.radioColorGreen) {
                selectedColor = "#33CC33";
            }

            if (categoryName.isEmpty()) {
                editTextCategoryName.setError("Nome da categoria é obrigatório");
                return;
            }

            CategoryModel newCategory = new CategoryModel(categoryName, selectedColor, preferences.getInt("KEY_ID", 0));
            CategoryDao dao = new CategoryDao(getContext());

            long result = dao.Insert(newCategory);

            if (result != -1) {
                Toast.makeText(getContext(), "Categoria adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onCategoryAdded();
                }
                dismiss();
            } else {
                Toast.makeText(getContext(), "Erro ao adicionar categoria. Talvez já exista um nome igual?", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}