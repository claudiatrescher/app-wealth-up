package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends AbstrataDao {

    public CategoryDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public int Insert(CategoryModel model) {
        int id = 0;

        Open();

        ContentValues values = new ContentValues();

        values.put(CategoryModel.COLUNA_NAME, model.getName());
        values.put(CategoryModel.COLUNA_COLOR, model.getColor());

        long isInsert = db.insert(CategoryModel.TABLE_NAME, null, values);
        id = (int) isInsert;

        Close();

        return id;
    }

    public List<CategoryModel> SelectAll() {
        List<CategoryModel> list = new ArrayList<>();
        Open();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoryModel.TABLE_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                CategoryModel category = new CategoryModel();

                category.setId(c.getInt(0));
                category.setName(c.getString(1));
                category.setColor(c.getString(2));
                list.add(category);
            } while (c.moveToNext());

            Close();
        }
        return list;
    }

    public int Update(CategoryModel model) {
        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoryModel.COLUNA_NAME, model.getName());
        contentValues.put(CategoryModel.COLUNA_COLOR, model.getColor());

        int result = db.update(CategoryModel.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(model.getId())});

        Close();

        return result;
    }

    public int Delete(int id) {
        Open();

        int result = db.delete(CategoryModel.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});

        Close();
        return result;
    }
}
