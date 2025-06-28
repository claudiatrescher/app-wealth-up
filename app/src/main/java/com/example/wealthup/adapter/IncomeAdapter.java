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
import com.example.wealthup.database.dao.FixedExpenseDao;
import com.example.wealthup.database.dao.IncomeDao;
import com.example.wealthup.database.model.IncomeModel;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ExpenseViewHolder> {

    private List<IncomeModel> incomeModelList;

    public IncomeAdapter(List<IncomeModel> expenseModelList) {
        this.incomeModelList = expenseModelList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganhos, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        IncomeModel incomeModel = incomeModelList.get(position);
        holder.categoryTextView.setText(incomeModel.getCategory());
        holder.dateTextView.setText(incomeModel.getDate());
        holder.descriptionTextView.setText(incomeModel.getDescription());
        holder.amountTextView.setText(String.valueOf(incomeModel.getAmount()));
    }

    @Override
    public int getItemCount() {
        return incomeModelList.size();
    }

    public void updateList(List<IncomeModel> newList) {
        this.incomeModelList = newList;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView amountTextView;
        //ImageView icon_delete_income;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.text_income_category);
            dateTextView = itemView.findViewById(R.id.text_income_date);
            descriptionTextView = itemView.findViewById(R.id.text_income_description);
            amountTextView = itemView.findViewById(R.id.text_income_amount);
            /*icon_delete_income = itemView.findViewById(R.id.icon_delete_income);

            icon_delete_fixed_expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        IncomeDao dao = new IncomeDao(itemView.getContext());
                        int result = dao.Delete(position);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Gasto fixo removido!", Toast.LENGTH_SHORT).show();
                            incomeModelList.remove(position);
                            updateList(incomeModelList);
                        }
                    }
                }
            });*/
        }
    }
}