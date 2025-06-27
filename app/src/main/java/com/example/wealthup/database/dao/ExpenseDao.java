package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDao extends AbstrataDao{
    public ExpenseDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

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
                int dayE = c.getInt(2);
                int month = c.getInt(3);
                int year = c.getInt(4);

                String date = String.valueOf(dayE) + "/" + String.valueOf(month) + "/" + String.valueOf(year);

                expense.setDate(date);
                expense.setDescription(c.getString(5));
                expense.setAmount(c.getInt(6));
                expense.setId_user(c.getInt(7));

                Date dateConvert = null;
                try {
                    dateConvert = formato.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                long milissegundos = dateConvert.getTime();

                expense.setDateInMillis(milissegundos);

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
             int dayE = c.getInt(2);
             int month = c.getInt(3);
             int year = c.getInt(4);
             expense.setDate(dayE + "/" + month + "/" + year);
             expense.setDescription(c.getString(5));
             expense.setAmount(c.getInt(6));
             expense.setId_user(c.getInt(7));

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

    public List<ExpenseModel> SelectByDay(int day, int userId) {
        List<ExpenseModel> list = new ArrayList<>();
        Open();
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE day = ? AND _id_user = ?", new String[]{String.valueOf(day), String.valueOf(userId)});
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
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE month = ? AND _id_user = ?", new String[]{String.valueOf(month), String.valueOf(userId)});
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
        Cursor c = db.rawQuery("SELECT * FROM " + ExpenseModel.TABLE_NAME + " WHERE day BETWEEN ? AND ? AND month = ? AND _id_user = ?", new String[]{String.valueOf(dayStart), String.valueOf(dayEnd), String.valueOf(month), String.valueOf(userId)});
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

    public int Insert(ExpenseModel model){
        int id = 0;

        Date date = new Date(model.getDateInMillis());
        String texto = formato.format(date);
        int day = Integer.parseInt(texto.substring(0, 2));
        int month = Integer.parseInt(texto.substring(3, 5));
        int year = Integer.parseInt(texto.substring(6, 10));

        Open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ExpenseModel.COLUNA_AMOUNT, model.getAmount());
        contentValues.put(ExpenseModel.COLUNA_DAY, day);
        contentValues.put(ExpenseModel.COLUNA_MONTH, month);
        contentValues.put(ExpenseModel.COLUNA_YEAR, year);
        contentValues.put(ExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(ExpenseModel.COLUNA_DESCRIPTION, model.getDescription());
        contentValues.put(ExpenseModel.COLUNA_ID_USER, model.getId_user());

        long isInsert = db.insert(ExpenseModel.TABLE_NAME, null, contentValues);
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

        Date date = new Date(model.getDateInMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String texto = formato.format(date);
        int day = Integer.parseInt(texto.substring(0, 2));
        int month = Integer.parseInt(texto.substring(3, 5));
        int year = Integer.parseInt(texto.substring(6, 10));

        Open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ExpenseModel.COLUNA_AMOUNT, model.getAmount());
        contentValues.put(ExpenseModel.COLUNA_DAY, day);
        contentValues.put(ExpenseModel.COLUNA_MONTH, month);
        contentValues.put(ExpenseModel.COLUNA_YEAR, year);
        contentValues.put(ExpenseModel.COLUNA_CATEGORY, model.getCategory());
        contentValues.put(ExpenseModel.COLUNA_DESCRIPTION, model.getDescription());
        contentValues.put(ExpenseModel.COLUNA_ID_USER, model.getId_user());

        int result = db.update(ExpenseModel.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(model.getId())});

        Close();

        return result;
    }

}