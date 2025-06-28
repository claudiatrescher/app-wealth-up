package com.example.wealthup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wealthup.R;
import com.example.wealthup.database.dao.ExpenseDao;
import com.example.wealthup.database.model.ExpenseModel;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ExpenseModel> expenseModelList;

    public ExpenseAdapter(List<ExpenseModel> expenseModelList) {
        this.expenseModelList = expenseModelList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gastos, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseModel expenseModel = expenseModelList.get(position);
        holder.categoryTextView.setText(expenseModel.getCategory());
        holder.dateTextView.setText(expenseModel.getDate());
        holder.descriptionTextView.setText(expenseModel.getDescription());
        holder.amountTextView.setText(String.valueOf(expenseModel.getAmount()));
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public void updateList(List<ExpenseModel> newList) {
        this.expenseModelList = newList;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView amountTextView;
        ImageView icon_delete_expense;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.text_category);
            dateTextView = itemView.findViewById(R.id.text_date);
            descriptionTextView = itemView.findViewById(R.id.text_income_description);
            amountTextView = itemView.findViewById(R.id.text_amount);
            //icon_delete_expense = itemView.findViewById(R.id.icon_delete_expense);

            /*icon_delete_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ExpenseDao dao = new ExpenseDao(itemView.getContext());
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