package com.example.wealthup.adapter;

import android.annotation.SuppressLint;
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

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private static List<IncomeModel> incomeModelList;

    public IncomeAdapter(List<IncomeModel> expenseModelList) {
        this.incomeModelList = expenseModelList;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganhos, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
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

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<IncomeModel> newList) {
        incomeModelList = newList;
        notifyDataSetChanged();
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView amountTextView;
        ImageView icon_delete_income;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.text_income_category);
            dateTextView = itemView.findViewById(R.id.text_income_date);
            descriptionTextView = itemView.findViewById(R.id.text_income_description);
            amountTextView = itemView.findViewById(R.id.text_income_amount);
            icon_delete_income = itemView.findViewById(R.id.icon_delete_income);

            icon_delete_income.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        IncomeDao dao = new IncomeDao(itemView.getContext());
                        int id = incomeModelList.get(position).getId();
                        int result = dao.Delete(id);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Ganho removido!", Toast.LENGTH_SHORT).show();
                            incomeModelList.remove(position);
                            updateList(incomeModelList);
                        }
                    }
                }
            });
        }
    }
}