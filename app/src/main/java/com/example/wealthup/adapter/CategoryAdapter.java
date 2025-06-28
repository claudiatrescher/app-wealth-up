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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private static List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.text_category_name.setText(categoryModel.getName());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<CategoryModel> newList) {
        categoryModelList = newList;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView text_category_name;
        ImageView icon_delete_income;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            text_category_name = itemView.findViewById(R.id.text_category_name);
            icon_delete_income = itemView.findViewById(R.id.icon_delete_category);

            icon_delete_income.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        CategoryDao dao = new CategoryDao(itemView.getContext());
                        int id = categoryModelList.get(position).getId();
                        int result = dao.Delete(id);
                        if(result != -1){
                            Toast.makeText(itemView.getContext(), "Categoria removida!", Toast.LENGTH_SHORT).show();
                            categoryModelList.remove(position);
                            updateList(categoryModelList);
                        }
                    }
                }
            });
        }
    }
}