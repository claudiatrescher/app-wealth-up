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
import com.example.wealthup.adapter.IncomeAdapter;
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.dao.IncomeDao;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.IncomeModel;
import com.example.wealthup.viewmodel.ExpensesViewModel;
import com.example.wealthup.viewmodel.IncomeViewModel;
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
    private IncomeAdapter previewIncomeAdapter;
    //private ImageView imageViewSeeAllIncomes;
    private IncomeViewModel incomeViewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
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
        textViewTotalIncomes = view.findViewById(R.id.textViewTotalIncomes);
        timeFilterToggleGroup = view.findViewById(R.id.time_filter_toggle_group_incomes2);
        recyclerViewPreviewIncomes = view.findViewById(R.id.recyclerViewPreviewIncomes);
        //imageViewSeeAllIncomes = view.findViewById(R.id.imageViewSeeAllExpenses);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = preferences.edit();

        incomeViewModel = new ViewModelProvider(requireActivity()).get(IncomeViewModel.class);

        recyclerViewPreviewIncomes.setLayoutManager(new LinearLayoutManager(getContext()));
        previewIncomeAdapter = new IncomeAdapter(new ArrayList<>());
        recyclerViewPreviewIncomes.setAdapter(previewIncomeAdapter);
        recyclerViewPreviewIncomes.setNestedScrollingEnabled(false);

        listExpanses("Mês", null, null);

        incomeViewModel.getFilteredExpenses().observe(getViewLifecycleOwner(), income -> {
            updateChartAndPreview(income);
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
                incomeViewModel.setFilter(filterType);
            }
        });

        /*imageViewSeeAllIncomes.setOnClickListener(v -> {
            if (seeAllIncomesClickListener != null) {
                seeAllIncomesClickListener.onSeeAllIncomesClick();
            }
        });*/

        timeFilterToggleGroup.check(R.id.button_mes_ganhos2);
    }

    private void updateChartAndPreview(List<IncomeModel> expenses) {
        double totalPeriodExpenses = 0.0;
        List<Float> chartData = new ArrayList<>();

        for (IncomeModel expense : expenses) {
            totalPeriodExpenses += expense.getAmount();
            chartData.add((float) expense.getAmount());
        }

        String currentFilter = incomeViewModel.getCurrentFilter().getValue();
        Calendar calendar = Calendar.getInstance();

        if ("Dia".equals(currentFilter)) {
            textViewIncomeDate.setText(uiDateFormatDay.format(calendar.getTime()));
            listExpanses(currentFilter, null, null);
        } else if ("Semana".equals(currentFilter)) {
            Calendar startOfWeekCal = (Calendar) calendar.clone();
            startOfWeekCal.set(Calendar.DAY_OF_WEEK, startOfWeekCal.getFirstDayOfWeek());
            String startOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(startOfWeekCal.getTime());

            Calendar endOfWeekCal = (Calendar) startOfWeekCal.clone();
            endOfWeekCal.add(Calendar.DAY_OF_YEAR, 6);
            String endOfWeek = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(endOfWeekCal.getTime());

            textViewIncomeDate.setText("Semana " + startOfWeek + " - " + endOfWeek);
            listExpanses(currentFilter, startOfWeek, endOfWeek);
        } else {
            textViewIncomeDate.setText(uiDateFormatMonth.format(calendar.getTime()));
            listExpanses(currentFilter, null, null);
        }
        textViewTotalIncomes.setText(String.format(Locale.getDefault(), "R$ %.2f", totalPeriodExpenses));

        //if (barChartView != null) {
         //  barChartView.setChartData(chartData);
         //}

        List<IncomeModel> previewList = new ArrayList<>();
        for (int i = 0; i < Math.min(expenses.size(), 3); i++) {
            previewList.add(expenses.get(i));
        }
        previewIncomeAdapter.updateList(previewList);
    }

    private void listExpanses(String currentFilter, String startOfWeek, String endOfWeek) {
        Calendar calendar = Calendar.getInstance();
        List<IncomeModel> incomeList = new ArrayList<>();
        IncomeDao dao = new IncomeDao(getContext());

        if ("Dia".equals(currentFilter)) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            incomeList = dao.SelectByDay(day, preferences.getInt("KEY_ID", 0));
        } else if ("Semana".equals(currentFilter)) {
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayOfWeek = Integer.parseInt(startOfWeek.substring(0, 2));
            int endOfWeekDay = Integer.parseInt(endOfWeek.substring(0, 2));

            incomeList = dao.SelectByWeek(dayOfWeek, endOfWeekDay, month, preferences.getInt("KEY_ID", 0));
        } else {
            int month = calendar.get(Calendar.MONTH) + 1;

            incomeList = dao.SelectByMonth(month, preferences.getInt("KEY_ID", 0));
        }
        IncomeAdapter adapter = new IncomeAdapter(incomeList);
        recyclerViewPreviewIncomes.setAdapter(adapter);
    }
}