package com.example.wealthup.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthup.R;
import com.example.wealthup.database.dao.CategoryDao;
import com.example.wealthup.database.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ExpenseViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
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
        //ImageView icon_delete_income;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            text_category_name = itemView.findViewById(R.id.text_category_name);
            //icon_delete_income = itemView.findViewById(R.id.icon_delete_fixed_expense);

            /*icon_delete_income.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        CategoryDao dao = new CategoryDao(itemView.getContext());
                        int result = dao.Delete(position);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Gasto fixo removido!", Toast.LENGTH_SHORT).show();
                            categoryModelList.remove(position);
                            updateList(categoryModelList);
                        }
                    }
                }
            });*/
        }
    }
}