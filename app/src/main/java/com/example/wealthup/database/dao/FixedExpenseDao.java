package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.FixedExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class FixedExpenseDao extends AbstrataDao {
    public FixedExpenseDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }
    public int Insert(FixedExpenseModel model){
        int id = 0;
        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FixedExpenseModel.COLUNA_NAME, model.getName());
        contentValues.put(FixedExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(FixedExpenseModel.COLUNA_VALUE, model.getValue());
        contentValues.put(FixedExpenseModel.COLUNA_DATEMILIS, model.getDueDateMillis());
        contentValues.put(FixedExpenseModel.COLUNA_ID_USER, model.getIdUser());

        long isResult = db.insert(FixedExpenseModel.TABLE_NAME, null, contentValues);
        id = (int) isResult;

        Close();
        return id;
    }

    public int Update(FixedExpenseModel model){
        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FixedExpenseModel.COLUNA_NAME, model.getName());
        contentValues.put(FixedExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(FixedExpenseModel.COLUNA_VALUE, model.getValue());
        contentValues.put(FixedExpenseModel.COLUNA_DATEMILIS, model.getDueDateMillis());
        contentValues.put(FixedExpenseModel.COLUNA_ID_USER, model.getIdUser());

        int result = db.update(FixedExpenseModel.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(model.getId())});

        Close();

        return result;

    }

    public int Delete(int id){
        Open();

        int result = db.delete(FixedExpenseModel.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});

        Close();

        return result;
    }

    public List<FixedExpenseModel> SelectByCategory(String category, int userId) {
        List<FixedExpenseModel> list = new ArrayList<>();

        Open();

        Cursor c = db.rawQuery("SELECT * FROM " + FixedExpenseModel.TABLE_NAME + " WHERE category = ? AND _user_id = ?", new String[]{category, String.valueOf(userId)});

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                FixedExpenseModel fixedExpense = new FixedExpenseModel();

                fixedExpense.setId(c.getInt(0));
                fixedExpense.setName(c.getString(1));
                fixedExpense.setCategory(c.getString(2));
                fixedExpense.setDueDateMillis(c.getLong(3));
                fixedExpense.setValue(c.getInt(4));
                fixedExpense.setIdUser(c.getInt(5));

                list.add(fixedExpense);
            } while (c.moveToNext());
        }
        Close();

        return list;
    }

    public List<FixedExpenseModel> SelectAll(int userId) {
        List<FixedExpenseModel> list = new ArrayList<>();

        Open();

        Cursor c = db.rawQuery("SELECT * FROM " + FixedExpenseModel.TABLE_NAME + " WHERE  _user_id = ?", new String[]{String.valueOf(userId)});

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                FixedExpenseModel fixedExpense = new FixedExpenseModel();

                fixedExpense.setId(c.getInt(0));
                fixedExpense.setName(c.getString(1));
                fixedExpense.setCategory(c.getString(2));
                fixedExpense.setDueDateMillis(c.getLong(3));
                fixedExpense.setValue(c.getInt(4));
                fixedExpense.setIdUser(c.getInt(5));

                list.add(fixedExpense);
            } while (c.moveToNext());
        }
        Close();

        return list;
    }

    public FixedExpenseModel SelectFixedExpanse(int id) {
        Open();

        FixedExpenseModel fixedExpense = new FixedExpenseModel();

        Cursor c = db.rawQuery("SELECT * FROM " + FixedExpenseModel.TABLE_NAME + " WHERE _id = ?", new String[]{String.valueOf(id)});

        if (c.getCount() > 0) {
            c.moveToFirst();

            fixedExpense.setId(c.getInt(0));
            fixedExpense.setName(c.getString(1));
            fixedExpense.setCategory(c.getString(2));
            fixedExpense.setDueDateMillis(c.getLong(3));
            fixedExpense.setValue(c.getInt(4));
            fixedExpense.setIdUser(c.getInt(5));

        }
        Close();
        return fixedExpense;
    }

}
