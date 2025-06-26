package com.example.wealthup.database.dao;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.wealthup.database.DBOpenHelper;

public abstract class AbstrataDao {
    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected  final void Open()  throws SQLException {
        db = db_helper.getWritableDatabase();
    }

    protected  final void Close() throws SQLException {
        db_helper.close();
    }
}