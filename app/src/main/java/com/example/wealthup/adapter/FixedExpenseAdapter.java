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

public class FixedExpenseAdapter extends RecyclerView.Adapter<FixedExpenseAdapter.ExpenseViewHolder> {
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private List<FixedExpenseModel> expenseModelList;
    private Context context;

    public FixedExpenseAdapter(List<FixedExpenseModel> expenseModelList) {
        this.expenseModelList = expenseModelList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gastos_fixos, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        FixedExpenseModel expenseModel = expenseModelList.get(position);

        String vence = "Vence dia: " + expenseModel.getDueDateMillis();
        holder.text_fixed_expense_due_date.setText(vence);
        holder.text_fixed_expense_value.setText(String.valueOf(expenseModel.getValue()));
        holder.text_fixed_expense_name.setText(expenseModel.getName());
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<FixedExpenseModel> newList) {
        this.expenseModelList = newList;
        notifyDataSetChanged();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView text_fixed_expense_name;
        TextView text_fixed_expense_value;
        TextView text_fixed_expense_due_date;
        //ImageView icon_delete_fixed_expense;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            text_fixed_expense_name = itemView.findViewById(R.id.text_fixed_expense_name);
            text_fixed_expense_value = itemView.findViewById(R.id.text_fixed_expense_value);
            text_fixed_expense_due_date = itemView.findViewById(R.id.text_fixed_expense_due_date);
            //icon_delete_fixed_expense = itemView.findViewById(R.id.icon_delete_fixed_expense);

            /*icon_delete_fixed_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FixedExpenseDao dao = new FixedExpenseDao(itemView.getContext());
                        int result = dao.Delete(position);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Gasto fixo removido!", Toast.LENGTH_SHORT).show();
                            expenseModelList.remove(position);
                            updateList(expenseModelList);
                        }
                    }
                }
            });*/
        }
    }
}