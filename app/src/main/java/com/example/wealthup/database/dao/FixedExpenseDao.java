package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.FixedExpenseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FixedExpenseDao extends AbstrataDao {
    public FixedExpenseDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }
    public int Insert(FixedExpenseModel model){
        int id = 0;

        Date date = new Date(model.getDueDateMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String texto = formato.format(date);
        int day = Integer.parseInt(texto.substring(0, 2));
        int month = Integer.parseInt(texto.substring(3, 5));
        int year = Integer.parseInt(texto.substring(6, 10));

        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FixedExpenseModel.COLUNA_NAME, model.getName());
        contentValues.put(FixedExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(FixedExpenseModel.COLUNA_VALUE, model.getValue());
        contentValues.put(FixedExpenseModel.COLUNA_DAY, day);
        contentValues.put(FixedExpenseModel.COLUNA_MONTH, month);
        contentValues.put(FixedExpenseModel.COLUNA_YEAR, year);
        contentValues.put(FixedExpenseModel.COLUNA_ID_USER, model.getIdUser());

        long isResult = db.insert(FixedExpenseModel.TABLE_NAME, null, contentValues);
        id = (int) isResult;

        Close();
        return id;
    }

    public int Update(FixedExpenseModel model){

        Date date = new Date(model.getDueDateMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String texto = formato.format(date);
        int day = Integer.parseInt(texto.substring(0, 2));
        int month = Integer.parseInt(texto.substring(3, 5));
        int year = Integer.parseInt(texto.substring(6, 10));

        Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FixedExpenseModel.COLUNA_NAME, model.getName());
        contentValues.put(FixedExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(FixedExpenseModel.COLUNA_VALUE, model.getValue());
        contentValues.put(FixedExpenseModel.COLUNA_DAY, day);
        contentValues.put(FixedExpenseModel.COLUNA_MONTH, month);
        contentValues.put(FixedExpenseModel.COLUNA_YEAR, year);
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

        Cursor c = db.rawQuery("SELECT * FROM " + FixedExpenseModel.TABLE_NAME + " WHERE category = ? AND _user_id = ? ORDER BY day DESC", new String[]{category, String.valueOf(userId)});

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

        Cursor c = db.rawQuery("SELECT * FROM " + FixedExpenseModel.TABLE_NAME + " WHERE  _id_user = ?", new String[]{String.valueOf(userId)});

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

    public List<ExpenseModel> SelectByDay(int day, int userId) {
        List<ExpenseModel> list = new ArrayList<>();
        Open();
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE day = ? AND _id_user = ? ORDER BY day DESC", new String[]{String.valueOf(day), String.valueOf(userId)});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel expense = new ExpenseModel();

                expense.setId(c.getInt(0));
                expense.setCategory(c.getString(1));
                int dayE = c.getInt(2);
                int month = c.getInt(3);
                int year = c.getInt(4);
                expense.setDate(dayE + "/" + month + "/" + year);
                expense.setDescription(c.getString(5));
                expense.setAmount(c.getInt(6));
                expense.setId_user(c.getInt(7));

                list.add(expense);
            } while (c.moveToNext());
        }
        Close();
        return list;
    }

    public List<ExpenseModel> SelectByMonth(int month, int userId) {
        List<ExpenseModel> list = new ArrayList<>();
        Open();
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE month = ? AND _id_user = ? ORDER BY day DESC", new String[]{String.valueOf(month), String.valueOf(userId)});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel expense = new ExpenseModel();
                expense.setId(c.getInt(0));
                expense.setCategory(c.getString(1));
                int day = c.getInt(2);
                int monthE = c.getInt(3);
                int year = c.getInt(4);
                expense.setDate(day + "/" + monthE + "/" + year);
                expense.setDescription(c.getString(5));
                expense.setAmount(c.getInt(6));
                expense.setId_user(c.getInt(7));
                list.add(expense);
            } while (c.moveToNext());
        }
        Close();
        return list;
    }
    public List<ExpenseModel> SelectByWeek(int dayStart, int dayEnd, int month, int userId) {
        List<ExpenseModel> list = new ArrayList<>();
        Open();
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE day BETWEEN ? AND ? AND month = ? AND _id_user = ? ORDER BY day DESC", new String[]{String.valueOf(dayStart), String.valueOf(dayEnd), String.valueOf(month), String.valueOf(userId)});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel expense = new ExpenseModel();
                expense.setId(c.getInt(0));
                expense.setCategory(c.getString(1));
                int day = c.getInt(2);
                int monthE = c.getInt(3);
                int year = c.getInt(4);
                expense.setDate(day + "/" + monthE + "/" + year);
                expense.setDescription(c.getString(5));
                expense.setAmount(c.getInt(6));
                expense.setId_user(c.getInt(7));
                list.add(expense);
            } while (c.moveToNext());
        }
        Close();
        return list;
    }

}
