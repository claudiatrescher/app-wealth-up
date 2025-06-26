package com.example.wealthup.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wealthup.database.model.Category;
import com.example.wealthup.database.model.FixedExpense;
import com.example.wealthup.database.model.Goal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wealthup.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FIXED_EXPENSES = "fixed_expenses";
    private static final String COLUMN_FIXED_EXPENSE_ID = "id";
    private static final String COLUMN_FIXED_EXPENSE_NAME = "name";
    private static final String COLUMN_FIXED_EXPENSE_VALUE = "value";
    private static final String COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS = "due_date_millis";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_COLOR = "color";

    private static final String TABLE_GOALS = "goals";
    private static final String COLUMN_GOAL_ID = "id";
    private static final String COLUMN_GOAL_NAME = "name";
    private static final String COLUMN_GOAL_TARGET_AMOUNT = "target_amount";
    private static final String COLUMN_GOAL_START_DATE = "start_date";
    private static final String COLUMN_GOAL_END_DATE = "end_date";
    private static final String COLUMN_GOAL_DIVIDED_MONTHLY = "divided_monthly";

    private static final String CREATE_TABLE_FIXED_EXPENSES =
            "CREATE TABLE " + TABLE_FIXED_EXPENSES + "(" +
                    COLUMN_FIXED_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FIXED_EXPENSE_NAME + " TEXT NOT NULL," +
                    COLUMN_FIXED_EXPENSE_VALUE + " REAL NOT NULL," +
                    COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS + " INTEGER NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CATEGORY_NAME + " TEXT UNIQUE NOT NULL," +
                    COLUMN_CATEGORY_COLOR + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_GOALS =
            "CREATE TABLE " + TABLE_GOALS + "(" +
                    COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_GOAL_NAME + " TEXT NOT NULL," +
                    COLUMN_GOAL_TARGET_AMOUNT + " REAL NOT NULL," +
                    COLUMN_GOAL_START_DATE + " INTEGER NOT NULL," +
                    COLUMN_GOAL_END_DATE + " INTEGER NOT NULL," +
                    COLUMN_GOAL_DIVIDED_MONTHLY + " INTEGER NOT NULL" +
                    ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FIXED_EXPENSES);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_GOALS);
        Log.d("DatabaseHelper", "Database created with tables: " + TABLE_FIXED_EXPENSES + ", " + TABLE_CATEGORIES + ", " + TABLE_GOALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIXED_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        onCreate(db);
    }

    public long addFixedExpense(FixedExpense fixedExpense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIXED_EXPENSE_NAME, fixedExpense.getName());
        values.put(COLUMN_FIXED_EXPENSE_VALUE, fixedExpense.getValue());
        values.put(COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS, fixedExpense.getDueDateMillis());

        long id = db.insert(TABLE_FIXED_EXPENSES, null, values);
        db.close();
        Log.d("DatabaseHelper", "FixedExpense added with ID: " + id);
        return id;
    }

    public FixedExpense getNearestUpcomingFixedExpense(int days) {
        SQLiteDatabase db = this.getReadableDatabase();
        FixedExpense nearestExpense = null;
        long currentTimeMillis = System.currentTimeMillis();
        long futureTimeMillis = currentTimeMillis + (days * 24 * 60 * 60 * 1000L);

        String selectQuery = "SELECT * FROM " + TABLE_FIXED_EXPENSES +
                " WHERE " + COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS + " BETWEEN " + currentTimeMillis + " AND " + futureTimeMillis +
                " ORDER BY " + COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS + " ASC LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_FIXED_EXPENSE_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_FIXED_EXPENSE_NAME);
            int valueIndex = cursor.getColumnIndex(COLUMN_FIXED_EXPENSE_VALUE);
            int dueDateIndex = cursor.getColumnIndex(COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS);

            if (idIndex != -1 && nameIndex != -1 && valueIndex != -1 && dueDateIndex != -1) {
                nearestExpense = new FixedExpense(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getDouble(valueIndex),
                        cursor.getLong(dueDateIndex)
                );
            } else {
                Log.e("DatabaseHelper", "One or more columns not found in FixedExpense table.");
            }
        }
        cursor.close();
        db.close();
        return nearestExpense;
    }

    public long addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, category.getName());
        values.put(COLUMN_CATEGORY_COLOR, category.getColor());

        long id = db.insert(TABLE_CATEGORIES, null, values);
        db.close();
        Log.d("DatabaseHelper", "Category added with ID: " + id);
        return id;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES + " ORDER BY " + COLUMN_CATEGORY_NAME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_CATEGORY_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_CATEGORY_NAME);
                int colorIndex = cursor.getColumnIndex(COLUMN_CATEGORY_COLOR);

                if (idIndex != -1 && nameIndex != -1 && colorIndex != -1) {
                    Category category = new Category(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(colorIndex)
                    );
                    categoryList.add(category);
                } else {
                    Log.e("DatabaseHelper", "One or more columns not found in Category table.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }

    public long addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GOAL_NAME, goal.getName());
        values.put(COLUMN_GOAL_TARGET_AMOUNT, goal.getTargetAmount());
        values.put(COLUMN_GOAL_START_DATE, goal.getStartDateMillis());
        values.put(COLUMN_GOAL_END_DATE, goal.getEndDateMillis());
        values.put(COLUMN_GOAL_DIVIDED_MONTHLY, goal.isDividedMonthly() ? 1 : 0);

        long id = db.insert(TABLE_GOALS, null, values);
        db.close();
        Log.d("DatabaseHelper", "Goal added with ID: " + id);
        return id;
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues fe1 = new ContentValues();
        fe1.put(COLUMN_FIXED_EXPENSE_NAME, "Mensalidade Spotify");
        fe1.put(COLUMN_FIXED_EXPENSE_VALUE, 29.90);
        fe1.put(COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS, System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000L));
        db.insert(TABLE_FIXED_EXPENSES, null, fe1);

        ContentValues fe2 = new ContentValues();
        fe2.put(COLUMN_FIXED_EXPENSE_NAME, "Aluguel");
        fe2.put(COLUMN_FIXED_EXPENSE_VALUE, 1500.00);
        fe2.put(COLUMN_FIXED_EXPENSE_DUE_DATE_MILLIS, System.currentTimeMillis() + (10 * 24 * 60 * 60 * 1000L));
        db.insert(TABLE_FIXED_EXPENSES, null, fe2);

        ContentValues cat1 = new ContentValues();
        cat1.put(COLUMN_CATEGORY_NAME, "Alimentação");
        cat1.put(COLUMN_CATEGORY_COLOR, "#FF5733");
        db.insert(TABLE_CATEGORIES, null, cat1);

        ContentValues cat2 = new ContentValues();
        cat2.put(COLUMN_CATEGORY_NAME, "Transporte");
        cat2.put(COLUMN_CATEGORY_COLOR, "#3366FF");
        db.insert(TABLE_CATEGORIES, null, cat2);

        ContentValues goal1 = new ContentValues();
        goal1.put(COLUMN_GOAL_NAME, "Viagem Europa");
        goal1.put(COLUMN_GOAL_TARGET_AMOUNT, 8000.00);
        goal1.put(COLUMN_GOAL_START_DATE, System.currentTimeMillis());
        goal1.put(COLUMN_GOAL_END_DATE, System.currentTimeMillis() + (180L * 24 * 60 * 60 * 1000));
        goal1.put(COLUMN_GOAL_DIVIDED_MONTHLY, 1);
        db.insert(TABLE_GOALS, null, goal1);
    }
}