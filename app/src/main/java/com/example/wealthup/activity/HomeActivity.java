package com.example.wealthup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.wealthup.view.MyBarChartView;
import com.example.wealthup.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private MyBarChartView myChartView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        myChartView = findViewById(R.id.myChartView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (myChartView != null) {
            List<Float> values = Arrays.asList(0.6f, 0.4f, 0.8f, 0.65f, 0.3f, 0.7f, 0.5f);
            int highlightIndex = 2;

            myChartView.setBarValues(values, highlightIndex);
        } else {
            Log.e("HomeActivity", "myChartView is null!");
        }

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_gastos) {
                    Intent intent = new Intent(HomeActivity.this, ExpensesActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_tarefas) {
                    Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_perfil) {
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}