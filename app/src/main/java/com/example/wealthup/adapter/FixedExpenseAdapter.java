package com.example.wealthup.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.FixedExpenseDao;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.FixedExpenseModel;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FixedExpenseAdapter extends RecyclerView.Adapter<FixedExpenseAdapter.FixedExpenseViewHolder> {
    private List<FixedExpenseModel> fixedExpenseModelList;

    public FixedExpenseAdapter(List<FixedExpenseModel> expenseModelList) {
        this.fixedExpenseModelList = expenseModelList;
    }

    @NonNull
    @Override
    public FixedExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gastos_fixos, parent, false);
        return new FixedExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixedExpenseViewHolder holder, int position) {
        FixedExpenseModel expenseModel = fixedExpenseModelList.get(position);

        String vence = "Vence dia: " + expenseModel.getDueDateMillis();
        holder.text_fixed_expense_due_date.setText(vence);
        holder.text_fixed_expense_value.setText(String.valueOf(expenseModel.getValue()));
        holder.text_fixed_expense_name.setText(expenseModel.getName());
    }

    @Override
    public int getItemCount() {
        return fixedExpenseModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<FixedExpenseModel> newList) {
        this.fixedExpenseModelList = newList;
        notifyDataSetChanged();
    }

    public class FixedExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView text_fixed_expense_name;
        TextView text_fixed_expense_value;
        TextView text_fixed_expense_due_date;
        ImageView icon_delete_fixed_expense;

        public FixedExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            text_fixed_expense_name = itemView.findViewById(R.id.text_fixed_expense_name);
            text_fixed_expense_value = itemView.findViewById(R.id.text_fixed_expense_value);
            text_fixed_expense_due_date = itemView.findViewById(R.id.text_fixed_expense_due_date);
            icon_delete_fixed_expense = itemView.findViewById(R.id.icon_delete_fixed_expense);

            icon_delete_fixed_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FixedExpenseDao dao = new FixedExpenseDao(itemView.getContext());
                        int id = fixedExpenseModelList.get(position).getId();
                        int result = dao.Delete(id);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Gasto fixo removido!", Toast.LENGTH_SHORT).show();
                            fixedExpenseModelList.remove(position);
                            updateList(fixedExpenseModelList);
                        }
                    }
                }
            });
        }
    }
}