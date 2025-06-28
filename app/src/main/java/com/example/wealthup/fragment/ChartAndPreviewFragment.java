package com.example.wealthup.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.viewmodel.ExpensesViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChartAndPreviewFragment extends Fragment {

    private TextView textViewDate;
    private TextView textViewTotalExpenses;
    private MaterialButtonToggleGroup timeFilterToggleGroup;
    private RecyclerView recyclerViewPreviewExpenses;
    private ExpenseAdapter previewExpenseAdapter;
    private ImageView imageViewSeeAllExpenses;
    private ExpensesViewModel expensesViewModel;
    private SimpleDateFormat uiDateFormatMonth = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
    private SimpleDateFormat uiDateFormatDay = new SimpleDateFormat("EEE, dd 'de' MMM", new Locale("pt", "BR"));

    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    public interface OnSeeAllExpensesClickListener {
        void onSeeAllExpensesClick();
    }

    private OnSeeAllExpensesClickListener seeAllExpensesClickListener;

    public ChartAndPreviewFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSeeAllExpensesClickListener) {
            seeAllExpensesClickListener = (OnSeeAllExpensesClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSeeAllExpensesClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart_and_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewDate = view.findViewById(R.id.textViewDate);
        textViewTotalExpenses = view.findViewById(R.id.textViewTotalExpenses);
        timeFilterToggleGroup = view.findViewById(R.id.time_filter_toggle_group_gastos);
        recyclerViewPreviewExpenses = view.findViewById(R.id.recyclerViewPreviewExpenses);
        imageViewSeeAllExpenses = view.findViewById(R.id.imageViewSeeAllExpenses);

        expensesViewModel = new ViewModelProvider(requireActivity()).get(ExpensesViewModel.class);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        recyclerViewPreviewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        previewExpenseAdapter = new ExpenseAdapter(new ArrayList<>());
        recyclerViewPreviewExpenses.setAdapter(previewExpenseAdapter);
        recyclerViewPreviewExpenses.setNestedScrollingEnabled(false);

        listExpanses("Mês", null, null);

        expensesViewModel.getFilteredExpenses().observe(getViewLifecycleOwner(), expenses -> {
            updateChartAndPreview(expenses);
        });

        timeFilterToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                String filterType;
                if (checkedId == R.id.button_dia_ganhos2) {
                    filterType = "Dia";
                } else if (checkedId == R.id.button_semana_ganhos2) {
                    filterType = "Semana";
                } else {
                    filterType = "Mês";
                }
                expensesViewModel.setFilter(filterType);
            }
        });

        imageViewSeeAllExpenses.setOnClickListener(v -> {
            if (seeAllExpensesClickListener != null) {
                seeAllExpensesClickListener.onSeeAllExpensesClick();
            }
        });

        timeFilterToggleGroup.check(R.id.button_mes_gastos);
    }

    private void updateChartAndPreview(List<ExpenseModel> expenses) {
        double totalPeriodExpenses = 0.0;
        List<Float> chartData = new ArrayList<>();

        for (ExpenseModel expense : expenses) {
            totalPeriodExpenses += expense.getAmount();
            chartData.add((float) expense.getAmount());
        }

        String currentFilter = expensesViewModel.getCurrentFilter().getValue();
        Calendar calendar = Calendar.getInstance();

        if ("Dia".equals(currentFilter)) {
            textViewDate.setText(uiDateFormatDay.format(calendar.getTime()));
            listExpanses(currentFilter, null, null);
        } else if ("Semana".equals(currentFilter)) {
            Calendar startOfWeekCal = (Calendar) calendar.clone();
            startOfWeekCal.set(Calendar.DAY_OF_WEEK, startOfWeekCal.getFirstDayOfWeek());
            String startOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(startOfWeekCal.getTime());

            Calendar endOfWeekCal = (Calendar) startOfWeekCal.clone();
            endOfWeekCal.add(Calendar.DAY_OF_YEAR, 6);
            String endOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(endOfWeekCal.getTime());

            textViewDate.setText("Semana " + startOfWeek + " - " + endOfWeek);
            listExpanses(currentFilter, startOfWeek, endOfWeek);
        } else {
            textViewDate.setText(uiDateFormatMonth.format(calendar.getTime()));
            listExpanses(currentFilter, null, null);
        }
        textViewTotalExpenses.setText(String.format(Locale.getDefault(), "R$ %.2f", totalPeriodExpenses));

        /*if (barChartView != null) {
           barChartView.setChartData(chartData);
        }*/

        List<ExpenseModel> previewList = new ArrayList<>();
        for (int i = 0; i < Math.min(expenses.size(), 3); i++) {
            previewList.add(expenses.get(i));
        }
        previewExpenseAdapter.updateList(previewList);
    }

    private void listExpanses(String currentFilter, String startOfWeek, String endOfWeek) {
        Calendar calendar = Calendar.getInstance();
        List<ExpenseModel> expensesList = new ArrayList<>();
        ExpenseDao dao = new ExpenseDao(getContext());

        if ("Dia".equals(currentFilter)) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            expensesList = dao.SelectByDay(day, preferences.getInt("KEY_ID", 0));
        } else if ("Semana".equals(currentFilter)) {
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayOfWeek = Integer.parseInt(startOfWeek.substring(0, 2));
            int endOfWeekDay = Integer.parseInt(endOfWeek.substring(0, 2));

            expensesList = dao.SelectByWeek(dayOfWeek, endOfWeekDay, month, preferences.getInt("KEY_ID", 0));
        } else {
            int month = calendar.get(Calendar.MONTH) + 1;

            expensesList = dao.SelectByMonth(month, preferences.getInt("KEY_ID", 0));
        }
        ExpenseAdapter adapter = new ExpenseAdapter(expensesList);
        recyclerViewPreviewExpenses.setAdapter(adapter);
        }
    }
