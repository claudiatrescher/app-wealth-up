package com.example.wealthup.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wealthup.database.dao.IncomeDao;
import com.example.wealthup.database.model.IncomeModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IncomeViewModel extends AndroidViewModel {

    private IncomeDao incomeDao;
    private MutableLiveData<List<IncomeModel>> allExpenses = new MutableLiveData<>();
    private MutableLiveData<List<IncomeModel>> filteredExpenses = new MutableLiveData<>();
    private MutableLiveData<String> currentFilter = new MutableLiveData<>("Mês");
    private MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    private ListView recyclerViewPreviewExpenses;

    private ExecutorService executorService;

    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeDao = new IncomeDao(IncomeViewModel.this.getApplication());
        executorService = Executors.newSingleThreadExecutor();
        preferences = PreferenceManager.getDefaultSharedPreferences(IncomeViewModel.this.getApplication());
        edit = preferences.edit();
        loadAllExpenses();
    }

    public LiveData<List<IncomeModel>> getFilteredExpenses() {
        return filteredExpenses;
    }

    public LiveData<String> getCurrentFilter() {
        return currentFilter;
    }

    public void setFilter(String filterType) {
        currentFilter.setValue(filterType);
        applyFilters();
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
        applyFilters();
    }

    public void loadAllExpenses() {
       executorService.execute(() -> {
            List<IncomeModel> income = incomeDao.SelectAll(preferences.getInt("KEY_ID", 0));
            allExpenses.postValue(income);
            applyFilters();
        });
    }

    private void applyFilters() {

        loadAllExpenses();

        List<IncomeModel> currentAllExpenses = allExpenses.getValue();
        if (currentAllExpenses == null) {
            currentAllExpenses = new ArrayList<>();
        }

        String filterType = currentFilter.getValue() != null ? currentFilter.getValue() : "Mês";
        String query = searchQuery.getValue() != null ? searchQuery.getValue() : "";

        List<IncomeModel> tempFilteredList = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        long startTimeFilter = 0;

        switch (filterType) {
            case "Dia":
                calendar.setTimeInMillis(currentTimeMillis);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTimeFilter = calendar.getTimeInMillis();
                break;
            case "Semana":
                calendar.setTimeInMillis(currentTimeMillis);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTimeFilter = calendar.getTimeInMillis();
                break;
            case "Mês":
                calendar.setTimeInMillis(currentTimeMillis);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTimeFilter = calendar.getTimeInMillis();
                break;
        }

        for (IncomeModel expense : currentAllExpenses) {
            boolean matchesTimeFilter = (expense.getDateInMillis() >= startTimeFilter);
            boolean matchesSearchText = query.isEmpty() ||
                    expense.getCategory().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault())) ||
                    expense.getCategory().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()));

            if (matchesTimeFilter && matchesSearchText) {
                tempFilteredList.add(expense);
            }
        }
        tempFilteredList.sort((e1, e2) -> Long.compare(e2.getDateInMillis(), e1.getDateInMillis()));
        filteredExpenses.postValue(tempFilteredList);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}