package com.example.wealthup.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Path;

import com.example.wealthup.database.DBOpenHelper;
import com.example.wealthup.database.model.UserModel;

import java.util.ArrayList;

public class UserDao extends AbstrataDao{

    public UserDao(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public int Insert(UserModel model){
        int id = 0;

        Open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(UserModel.COLUNA_NAME, model.getName());
        contentValues.put(UserModel.COLUNA_EMAIL, model.getEmail());
        contentValues.put(UserModel.COLUNA_PASSWORD, model.getPassword());

        long isInsert = db.insert(UserModel.TABLE_NAME, null, contentValues);
        id = (int) isInsert;

        Close();

        return id;
    }

    public UserModel SelectLogin(String email, String password){

        UserModel user = new UserModel();

        Open();

        Cursor c = db.rawQuery("SELECT * FROM "+UserModel.TABLE_NAME+" WHERE email = '"+email+"'"
                + " AND password ='"+password+"'", null);

        if(c.getCount() > 0){
            c.moveToFirst();

            user.setId(c.getInt(0));
            user.setName(c.getString(1));
            user.setEmail(c.getString(2));
            user.setPassword(c.getString(3));
        }

        Close();

        return user;

    }

}