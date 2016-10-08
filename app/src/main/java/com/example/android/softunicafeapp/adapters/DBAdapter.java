package com.example.android.softunicafeapp.adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBAdapter {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";
    static final String DATABASE_NAME = "login";
    static final String TABLE_NAME = "user";
    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_SURNAME + " TEXT NOT NULL, " + COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_PHONE + " TEXT NOT NULL, " +
            COLUMN_PASSWORD + " TEXT NOT NULL );";
    static final int DATABASE_VERSION = 1;
    Context c;
    // Variable to hold the database instance
    SQLiteDatabase db;
    helper h;

    public DBAdapter(Activity activity) {

        c = activity;
    }

    public void open() {

        h = new helper(c);
        db = h.getWritableDatabase();
    }

    public void insert(String name, String surName, String email, String phone, String password) {

        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_NAME, name);
        newValues.put(COLUMN_SURNAME, surName);
        newValues.put(COLUMN_EMAIL, email);
        newValues.put(COLUMN_PHONE, phone);
        newValues.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_NAME, null, newValues);
    }

    public void close() {

        db.close();
    }

    public String get() {

        h = new helper(c);
        db = h.getReadableDatabase();
        String txt = "";
        String[] col = {COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_PASSWORD};
        Cursor c = db.query(TABLE_NAME, col, null, null, null, null, null);    //fetching data from database
        c.moveToFirst();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            txt = txt + c.getString(0) + " " + c.getString(1) + " "
                    + c.getString(2) + " " + c.getString(3) + " "
                    + c.getString(4) + " " + c.getString(5) + " " + "\n";

        }

        return txt;
    }

    public String getLoginPass(String insertedPass) {
        String errorMsgPass;
        String errorMissingData = get();
        if (!insertedPass.equals("")) {
            if (!errorMissingData.equals("")) {
                h = new helper(c);
                db = h.getReadableDatabase();
                String password = "";
                String[] col = {COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_PASSWORD};
                Cursor c = db.query(TABLE_NAME, col, null, null, null, null, null);    //fetching data from database

                c.moveToFirst();
                do {
                    if (insertedPass.equals(c.getString(5))) {
                        password = c.getString(5);
                        c.close();
                        return password;
                    } else c.moveToNext();
                }
                while (!c.isAfterLast());

                c.close();
                return password;
            } else {
                errorMsgPass = "Please, register first.";
                return errorMsgPass;
            }
        } else {
            errorMsgPass = "Please, insert your password first.";
            return errorMsgPass;
        }

    }

    public String getLoginEmail(String insertedEmail) {
        String errorMsgEmail;
        if (!insertedEmail.equals("")) {
            h = new helper(c);
            db = h.getReadableDatabase();
            String email = "";
            String[] col = {COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_PASSWORD};
            Cursor c = db.query(TABLE_NAME, col, null, null, null, null, null);    //fetching data from database

            c.moveToFirst();
            do {
                if (insertedEmail.equals(c.getString(3))) {
                    email = c.getString(3);
                    c.close();
                    return email;
                } else c.moveToNext();
            }
            while (!c.isAfterLast());

            c.close();
            return email;
        } else {
            errorMsgEmail = "Please, insert your email first.";
            return errorMsgEmail;
        }
    }


    public class helper extends SQLiteOpenHelper {

        public helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        }
    }


}
