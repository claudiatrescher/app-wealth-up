package com.example.wealthup.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.database.model.CategoryModel;

import java.util.List;

public class CategoryExpenseAdapter extends RecyclerView.Adapter<CategoryExpenseAdapter.ExpenseViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryExpenseAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.text_category_name.setText(categoryModel.getName());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<CategoryModel> newList) {
        Log.d("DEBUG", "updateList: novos itens = " + newList.size());
        this.categoryModelList = newList;
        notifyDataSetChanged();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView text_category_name;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            text_category_name = itemView.findViewById(R.id.text_category_name);
        }
    }
}