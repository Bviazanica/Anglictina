package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "my_vocabulary.db";
    protected static final int DB_VERSION = 1;
    protected static final String TABLE_STRINGS = "strings";
    protected static final String COLUMN_ID = "_id";
    protected static final String COLUMN_STRING_SK = "string_sk";
    protected static final String COLUMN_STRING_ENG = "string_eng";
    protected static final String COLUMN_CATEGORY_ID = "category_id";
    protected static final String TABLE_CATEGORIES = "categories";
    protected static final String COLUMN_CATEGORY_SK = "category_sk";
    protected static final String COLUMN_CATEGORY_ENG = "category_eng";
    private static final String SQL_CREATE_TABLE_STRINGS = "CREATE TABLE " + TABLE_STRINGS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STRING_SK + " TEXT NOT NULL, "
            + COLUMN_STRING_ENG + " TEXT NOT NULL, "
            + COLUMN_CATEGORY_ID + " INTEGER NOT NULL " + ");";
    private static final String SQL_CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY_SK + " TEXT NOT NULL, "
            + COLUMN_CATEGORY_ENG + " TEXT NOT NULL " + ");";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_STRINGS);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DB_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long insertCategory(HashMap<String, String> attributes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, attributes.get(COLUMN_CATEGORY_ID));
        values.put(COLUMN_CATEGORY_SK, attributes.get(COLUMN_CATEGORY_SK));
        values.put(COLUMN_CATEGORY_ENG, attributes.get(COLUMN_CATEGORY_ENG));

        long id = db.insert(TABLE_CATEGORIES, null, values);
        return id;
    }

    public long insertString(HashMap<String, String> attributes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, attributes.get(COLUMN_ID));
        values.put(COLUMN_STRING_SK, attributes.get(COLUMN_STRING_SK));
        values.put(COLUMN_STRING_ENG, attributes.get(COLUMN_STRING_ENG));
        values.put(COLUMN_CATEGORY_ID, attributes.get(COLUMN_CATEGORY_ID));

        long id = db.insert(TABLE_STRINGS, null, values);
        return id;
    }

    public ArrayList<HashMap<String, String>> getAllRecords() {
        ArrayList<HashMap<String, String>> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STRINGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(COLUMN_ID, cursor.getString(0));
                hashMap.put(COLUMN_STRING_SK, cursor.getString(1));
                hashMap.put(COLUMN_STRING_ENG, cursor.getString(2));
                hashMap.put(COLUMN_CATEGORY_ID, cursor.getString(3));
                records.add(hashMap);
            } while (cursor.moveToNext());
        }
        return records;
    }

    public ArrayList<HashMap<String, String>> viewAllCategories() {
        ArrayList<HashMap<String, String>> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(COLUMN_CATEGORY_ID, cursor.getString(0));
                hashMap.put(COLUMN_CATEGORY_SK, cursor.getString(1));
                hashMap.put(COLUMN_CATEGORY_ENG, cursor.getString(2));
                records.add(hashMap);
            } while (cursor.moveToNext());
        }
        return records;
    }

    public ArrayList<HashMap<String, String>> viewCategoryRecords(String id) {
        ArrayList<HashMap<String, String>> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STRINGS + " WHERE category_id=" + id + "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(COLUMN_ID, cursor.getString(0));
                hashMap.put(COLUMN_STRING_SK, cursor.getString(1));
                hashMap.put(COLUMN_STRING_ENG, cursor.getString(2));
                hashMap.put(COLUMN_CATEGORY_ID, cursor.getString(3));
                records.add(hashMap);
            } while (cursor.moveToNext());
        }
        return records;
    }

    public String getId(String value) {
        String id = null;
        String selectQuery = "SELECT * FROM " + TABLE_STRINGS +
                " WHERE string_sk = '" + value + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return id;
    }

    public ArrayList<String> getStringIds() {
        ArrayList<String> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STRINGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                records.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return records;
    }

    public ArrayList<String> getAnswerStringsById(ArrayList<Integer> array, int i) {
        ArrayList<String> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STRINGS +
                " WHERE _id IN ('" + array.get(0) + "', '" + array.get(1) + "', '" + array.get(2) + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                records.add(cursor.getString(i));
            } while (cursor.moveToNext());
        }
        return records;
    }

    public ArrayList<String> findRightAnswer(String phrase, Boolean engLan) {
        ArrayList<String> answer = new ArrayList<>();
        String selectQuery;
        if (!engLan) {
             selectQuery = "SELECT * FROM " + TABLE_STRINGS +
                    " WHERE string_eng IN ('" + phrase + "')";
        } else {
             selectQuery = "SELECT * FROM " + TABLE_STRINGS +
                    " WHERE string_sk IN ('" + phrase + "')";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                answer.add(cursor.getString(1));
                answer.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return answer;
    }

    public JSONArray getResults(String table) {
        SQLiteDatabase db = this.getReadableDatabase();

        String searchQuery = "SELECT  * FROM " + table;
        Cursor cursor = db.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {

                    try {

                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }

            }

            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet;
    }
}