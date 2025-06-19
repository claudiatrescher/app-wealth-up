package com.example.wealthup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wealthup.R;
import com.example.wealthup.model.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gastos, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.categoryTextView.setText(expense.getCategory());
        holder.dateTextView.setText(expense.getDate());
        holder.descriptionTextView.setText(expense.getDescription());
        holder.amountTextView.setText(expense.getAmount());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void updateList(List<Expense> newList) {
        this.expenseList = newList;
        notifyDataSetChanged();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView amountTextView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.text_category);
            dateTextView = itemView.findViewById(R.id.text_date);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            amountTextView = itemView.findViewById(R.id.text_amount);
        }
    }
}