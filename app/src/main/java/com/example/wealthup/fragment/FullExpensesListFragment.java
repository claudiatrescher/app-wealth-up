package com.example.wealthup.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.ExpenseAdapter;
import com.example.wealthup.viewmodel.ExpensesViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FullExpensesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private MaterialButtonToggleGroup timeFilterToggleGroup;
    private EditText editTextSearch;

    private ExpensesViewModel expensesViewModel;

    public FullExpensesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_expenses_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_expenses);
        timeFilterToggleGroup = view.findViewById(R.id.time_filter_toggle_group);
        editTextSearch = view.findViewById(R.id.editTextSearch);

        // Inicialize o ViewModel (compartilhado com a Activity pai)
        expensesViewModel = new ViewModelProvider(requireActivity()).get(ExpensesViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expenseAdapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(expenseAdapter);

        // Observar as mudanças nos dados filtrados do ViewModel
        expensesViewModel.getFilteredExpenses().observe(getViewLifecycleOwner(), expenses -> {
            expenseAdapter.updateList(expenses); // Atualiza a lista completa
        });

        // Listener para os botões de filtro (Dia/Semana/Mês)
        timeFilterToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                String filterType;
                if (checkedId == R.id.button_dia) {
                    filterType = "Dia";
                } else if (checkedId == R.id.button_semana) {
                    filterType = "Semana";
                } else { // R.id.button_mes
                    filterType = "Mês";
                }
                expensesViewModel.setFilter(filterType); // Define o filtro no ViewModel
            }
        });

        // Listener para a barra de pesquisa
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expensesViewModel.setSearchQuery(s.toString()); // Define a query de pesquisa no ViewModel
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Define o filtro inicial para "Mês" (para que a UI seja carregada corretamente)
        timeFilterToggleGroup.check(R.id.button_mes);
    }
}