package com.example.wealthup.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
//import com.example.wealthup.adapter.ExpenseAdapter;
//import com.example.wealthup.viewmodel.ExpensesViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class FullIncomesListFragment extends Fragment {

    private RecyclerView recyclerView;
//    private ExpenseAdapter expenseAdapter;
    private MaterialButtonToggleGroup timeFilterToggleIncome;
    private TextView editIncomeListTextSearch;

//    private ExpensesViewModel expensesViewModel;

    public FullIncomesListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_incomes_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_incomes);
        timeFilterToggleIncome = view.findViewById(R.id.time_filter_toggle_income);
        editIncomeListTextSearch = view.findViewById(R.id.editIncomeListTextSearch);

//        expensesViewModel = new ViewModelProvider(requireActivity()).get(ExpensesViewModel.class);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        expenseAdapter = new ExpenseAdapter(new ArrayList<>());
//        recyclerView.setAdapter(expenseAdapter);
//
//        expensesViewModel.getFilteredExpenses().observe(getViewLifecycleOwner(), expenses -> {
//            expenseAdapter.updateList(expenses);
//        });

//        timeFilterToggleIncome.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
//            if (isChecked) {
//                String filterType;
//                if (checkedId == R.id.button_dia_incomes) {
//                    filterType = "Dia";
//                } else if (checkedId == R.id.button_semana_incomes) {
//                    filterType = "Semana";
//                } else {
//                    filterType = "Mês";
//                }
//                expensesViewModel.setFilter(filterType);
//            }
//        });
//
//        editIncomeListTextSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                expensesViewModel.setSearchQuery(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) { }
//        });

        timeFilterToggleIncome.check(R.id.button_mes_incomes);
    }
}