package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDao extends AbstrataDao{
    public ExpenseDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public List<ExpenseModel> SelectAll(int id_user){
        List<ExpenseModel> list = new ArrayList<>();

        Open();
        Cursor c = db.rawQuery("SELECT * FROM "+ExpenseModel.TABLE_NAME+" WHERE _id_user = ?", new String[]{String.valueOf(id_user)});
        if(c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel expense = new ExpenseModel();

                expense.setId(c.getInt(0));
                expense.setCategory(c.getString(1));
                expense.setDate(c.getString(2));
                expense.setDescription(c.getString(3));
                expense.setAmount(c.getInt(4));
                expense.setId_user(c.getInt(5));

                list.add(expense);
            } while (c.moveToNext());
        }
        Close();

        return list;
    }

    public ExpenseModel SelectExpense(int id) {
        Open();

        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(id)});
         if(c.getCount() > 0) {
            c.moveToFirst();

            ExpenseModel expense = new ExpenseModel();

            expense.setId(c.getInt(0));
            expense.setCategory(c.getString(1));
            expense.setDate(c.getString(2));
            expense.setDescription(c.getString(3));
            expense.setAmount(c.getInt(4));
            expense.setId_user(c.getInt(5));

            Close();

            return expense;
        }
         return null;
    }

    public List<ExpenseModel> SelectByCategory(String category, int userId) {
        List<ExpenseModel> list = new ArrayList<>();

        Open();

        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE category = ? AND _user_id = ?", new String[]{category, String.valueOf(userId)});

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel expense = new ExpenseModel();

                expense.setId(c.getInt(0));
                expense.setCategory(c.getString(1));
                expense.setDate(c.getString(2));
                expense.setDescription(c.getString(3));
                expense.setAmount(c.getInt(4));
                expense.setId_user(c.getInt(5));

                list.add(expense);
            } while (c.moveToNext());
        }
        Close();

        return list;
    }

    public int Insert(ExpenseModel model){
        int id = 0;

        Open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ExpenseModel.COLUNA_AMOUNT, model.getAmount());
        contentValues.put(ExpenseModel.COLUNA_DATE, model.getDate());
        contentValues.put(ExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(ExpenseModel.COLUNA_DESCRIPTION, model.getDescription());
        contentValues.put(ExpenseModel.COLUNA_ID_USER, model.getId_user());

        long isInsert = db.insert(UserModel.TABLE_NAME, null, contentValues);
        id = (int) isInsert;

        Close();

        return id;
    }

    public int Delete(int id) {
        Open();

        int result = db.delete(ExpenseModel.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});

        Close();

        return result;
    }

    public int Update(ExpenseModel model) {
        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseModel.COLUNA_AMOUNT, model.getAmount());
        contentValues.put(ExpenseModel.COLUNA_DATE, model.getDate());
        contentValues.put(ExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(ExpenseModel.COLUNA_DESCRIPTION, model.getDescription());
        contentValues.put(ExpenseModel.COLUNA_ID_USER, model.getId_user());

        int result = db.update(ExpenseModel.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(model.getId())});

        Close();

        return result;
    }

}