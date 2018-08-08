package com.bignerdranch.android.weightlosstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WeightLossHistory.db";
    private static final int DATABASE_VERSION = 1;

    public static final String WEIGHTLOSS_TABLE_NAME = "WeightLossHistoryTable";
    public static final String WEIGHTLOSS_COLUMN_ID = "id";
    public static final String WEIGHTLOSS_COLUMN_WEIGHT = "weight";
    public static final String WEIGHTLOSS_COLUMN_DATE = "date";

    public static final String SETTINGS_TABLE_NAME = "SettingsTable";
    public static final String SETTINGS_COLUMN_ID = "id";
    public static final String SETTINGS_COLUMN_NAME = "name";
    public static final String SETTINGS_COLUMN_VALUE = "value";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + WEIGHTLOSS_TABLE_NAME +
                        "(" + WEIGHTLOSS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WEIGHTLOSS_COLUMN_WEIGHT + " TEXT, " +
                        WEIGHTLOSS_COLUMN_DATE + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + SETTINGS_TABLE_NAME +
                        "(" + SETTINGS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        SETTINGS_COLUMN_NAME + " TEXT, " +
                        SETTINGS_COLUMN_VALUE + " TEXT)"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WEIGHTLOSS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertSettingsRow(String name, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SETTINGS_COLUMN_NAME, name);
        contentValues.put(SETTINGS_COLUMN_VALUE, value);

        db.insert(SETTINGS_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean deleteAllSettingsRow(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(SETTINGS_TABLE_NAME, null, null);
        return true;
    }

    public Integer deleteSettingsRow(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SETTINGS_TABLE_NAME,
                SETTINGS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public String getSettingsRow(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + SETTINGS_TABLE_NAME + " WHERE " +
                SETTINGS_COLUMN_NAME + "=?", new String[]{name});
        if (res != null)
            res.moveToFirst();

        if (res.getCount() == 0){
            return "";
        }
        else{
            return res.getString(res.getColumnIndex("value"));
        }


    }


    public boolean insertRow(String weight, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(WEIGHTLOSS_COLUMN_WEIGHT, weight);
        contentValues.put(WEIGHTLOSS_COLUMN_DATE, date);

        db.insert(WEIGHTLOSS_TABLE_NAME, null, contentValues);
        return true;
    }


    public Integer deleteRow(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(WEIGHTLOSS_TABLE_NAME,
                WEIGHTLOSS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + WEIGHTLOSS_TABLE_NAME + " WHERE " +
                WEIGHTLOSS_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + WEIGHTLOSS_TABLE_NAME, null );
        return res;
    }

    public String getLatestWeightDiff(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + WEIGHTLOSS_TABLE_NAME + " ORDER BY " + WEIGHTLOSS_COLUMN_ID + " DESC LIMIT 2", null );

        double weightdiff = 0;
        if (res.moveToFirst()){
            while(!res.isAfterLast()) {
                String weight = res.getString(res.getColumnIndex("weight"));

                if (weightdiff == 0) {
                    weightdiff = Double.parseDouble((weight));
                } else {
                    weightdiff = weightdiff - Double.parseDouble((weight));
                }

                res.moveToNext();
            }
        }
        res.close();

        return String.valueOf(weightdiff);
    }


}