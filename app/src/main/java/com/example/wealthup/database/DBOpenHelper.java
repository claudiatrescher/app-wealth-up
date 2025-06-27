package com.example.wealthup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wealthup.database.model.CategoryModel;
import com.example.wealthup.database.model.ExpenseModel;
import com.example.wealthup.database.model.FixedExpenseModel;
import com.example.wealthup.database.model.IncomeModel;
import com.example.wealthup.database.model.UserModel;


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NOME="wealthUp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBOpenHelper";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Creating database tables...");

        try {
            sqLiteDatabase.execSQL(UserModel.CREATE_TABLE);
            sqLiteDatabase.execSQL(ExpenseModel.CREATE_TABLE);
            sqLiteDatabase.execSQL(CategoryModel.CREATE_TABLE);
            sqLiteDatabase.execSQL(FixedExpenseModel.CREATE_TABLE);
            sqLiteDatabase.execSQL(IncomeModel.CREATE_TABLE);
        } catch (Exception e) {
            Log.e(TAG, "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
