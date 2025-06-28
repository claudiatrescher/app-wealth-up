package com.example.wealthup.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.wealthup.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private ConstraintLayout rootLayout;
    private TextView tvMonthHeader;
    private TextView tvSelectedDateHeader;
    private BottomNavigationView bottomNavigationView;
    private MaterialButtonToggleGroup filterTypeToggleGroup;
    private MaterialButton buttonFilterGastos;
    private MaterialButton buttonFilterGanhos;
    private ImageView imgArrowLeft;
    private ImageView imgArrowRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        rootLayout = findViewById(R.id.rootLayoutCalendar);

        tvMonthHeader = findViewById(R.id.tvMonthHeader);
        tvSelectedDateHeader = findViewById(R.id.tvSelectedDateHeader);
        calendarView = findViewById(R.id.calendarView);

        filterTypeToggleGroup = findViewById(R.id.filter_type_toggle_group);
        buttonFilterGastos = findViewById(R.id.button_filter_gastos);
        buttonFilterGanhos = findViewById(R.id.button_filter_ganhos);



        Calendar currentCalendar = Calendar.getInstance();
        updateMonthHeader(currentCalendar);
        updateSelectedDateHeader(currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                updateSelectedDateHeader(year, month, dayOfMonth);

                String selectedDateString = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                Snackbar.make(rootLayout, "Data selecionada: " + selectedDateString, Snackbar.LENGTH_LONG).show();
            }
        });


        filterTypeToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.button_filter_gastos) {
                        Snackbar.make(rootLayout, "Filtro: Gastos", Snackbar.LENGTH_SHORT).show();
                    } else if (checkedId == R.id.button_filter_ganhos) {
                        Snackbar.make(rootLayout, "Filtro: Ganhos", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imgArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, -1);
                updateMonthHeader(currentCalendar);
            }
        });

        imgArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, 1);
                updateMonthHeader(currentCalendar);
            }
        });
    }

    private void updateMonthHeader(Calendar cal) {
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", new Locale("pt", "BR"));
        tvMonthHeader.setText(monthYearFormat.format(cal.getTime()));
    }

    private void updateSelectedDateHeader(int year, int month, int dayOfMonth) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.set(year, month, dayOfMonth);
        SimpleDateFormat dayOfWeekDateFormat = new SimpleDateFormat("EEE, dd 'de' MMMM", new Locale("pt", "BR"));
        tvSelectedDateHeader.setText(dayOfWeekDateFormat.format(selectedCal.getTime()));
    }
}