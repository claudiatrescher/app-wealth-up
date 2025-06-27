package com.example.wealthup.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.adapter.ExpenseAdapter;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.viewmodel.ExpensesViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IncomeChartFragment extends Fragment {

    private TextView textViewIncomeDate;
    private TextView textViewTotalIncomes;
    private MaterialButtonToggleGroup timeFilterToggleGroup;
    private RecyclerView recyclerViewPreviewIncomes;
//    private ExpenseAdapter previewExpenseAdapter;
//    private ImageView imageViewSeeAllExpenses;

    private ExpensesViewModel expensesViewModel;
    private SimpleDateFormat uiDateFormatMonth = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
    private SimpleDateFormat uiDateFormatDay = new SimpleDateFormat("EEE, dd 'de' MMM", new Locale("pt", "BR"));

    public interface OnSeeAllIncomesClickListener {
        void onSeeAllIncomesClick();
    }

    private OnSeeAllIncomesClickListener seeAllIncomesClickListener;

    public IncomeChartFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSeeAllIncomesClickListener) {
            seeAllIncomesClickListener = (OnSeeAllIncomesClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSeeAllIncomesClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.income_chart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewIncomeDate = view.findViewById(R.id.textViewIncomeDate);
        textViewTotalIncomes = view.findViewById(R.id.textViewTotalExpenses);
        timeFilterToggleGroup = view.findViewById(R.id.time_filter_toggle_group_incomes2);
        recyclerViewPreviewIncomes = view.findViewById(R.id.recyclerViewPreviewIncomes);
//        imageViewSeeAllExpenses = view.findViewById(R.id.imageViewSeeAllExpenses);

//        expensesViewModel = new ViewModelProvider(requireActivity()).get(ExpensesViewModel.class);

//        recyclerViewPreviewIncomes.setLayoutManager(new LinearLayoutManager(getContext()));
//        previewExpenseAdapter = new ExpenseAdapter(new ArrayList<>());
//        recyclerViewPreviewIncomes.setAdapter(previewExpenseAdapter);
//        recyclerViewPreviewIncomes.setNestedScrollingEnabled(false);

//        expensesViewModel.getFilteredExpenses().observe(getViewLifecycleOwner(), expenses -> {
//            updateChartAndPreview(expenses);
//        });
//
//        timeFilterToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
//            if (isChecked) {
//                String filterType;
//                if (checkedId == R.id.button_dia_ganhos) {
//                    filterType = "Dia";
//                } else if (checkedId == R.id.button_semana_ganhos) {
//                    filterType = "Semana";
//                } else {
//                    filterType = "Mês";
//                }
//                expensesViewModel.setFilter(filterType);
//            }
//        });
//
//        imageViewSeeAllExpenses.setOnClickListener(v -> {
//            if (seeAllIncomesClickListener != null) {
//                seeAllIncomesClickListener.onSeeAllIncomesClick();
//            }
//        });
//
//        timeFilterToggleGroup.check(R.id.button_mes_ganhos);
//    }
//
//    private void updateChartAndPreview(List<ExpenseModel> expenses) {
//        double totalPeriodExpenses = 0.0;
//        List<Float> chartData = new ArrayList<>();
//
//        for (ExpenseModel expense : expenses) {
//            totalPeriodExpenses += expense.getAmount();
//            chartData.add((float) expense.getAmount());
//        }
//
//        String currentFilter = expensesViewModel.getCurrentFilter().getValue();
//        Calendar calendar = Calendar.getInstance();
//
//        if ("Dia".equals(currentFilter)) {
//            textViewIncomeDate.setText(uiDateFormatDay.format(calendar.getTime()));
//        } else if ("Semana".equals(currentFilter)) {
//            Calendar startOfWeekCal = (Calendar) calendar.clone();
//            startOfWeekCal.set(Calendar.DAY_OF_WEEK, startOfWeekCal.getFirstDayOfWeek());
//            String startOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(startOfWeekCal.getTime());
//
//            Calendar endOfWeekCal = (Calendar) startOfWeekCal.clone();
//            endOfWeekCal.add(Calendar.DAY_OF_YEAR, 6);
//            String endOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(endOfWeekCal.getTime());
//
//            textViewIncomeDate.setText("Semana " + startOfWeek + " - " + endOfWeek);
//        } else {
//            textViewIncomeDate.setText(uiDateFormatMonth.format(calendar.getTime()));
//        }
//        textViewTotalIncomes.setText(String.format(Locale.getDefault(), "R$ %.2f", totalPeriodExpenses));
//
//        // if (barChartView != null) {
//        //    barChartView.setChartData(chartData);
//        // }
//
//        List<ExpenseModel> previewList = new ArrayList<>();
//        for (int i = 0; i < Math.min(expenses.size(), 3); i++) {
//            previewList.add(expenses.get(i));
//        }
//        previewExpenseAdapter.updateList(previewList);
    }
}