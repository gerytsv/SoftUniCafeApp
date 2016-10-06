package com.example.android.softunicafeapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.softunicafeapp.data.DataBaseHelper;
import com.example.android.softunicafeapp.data.User;

public class DBAdapter extends SQLiteOpenHelper {


    // Context of the application using the database.
    private final Context ctx;

    static final String DATABASE_NAME = "login.db";
    static final String TABLE_NAME = "LOGIN";
    static final int DATABASE_VERSION = 1;

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";

    DataBaseHelper dbHelper;
    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            "( counter integer primary key autoincrement, "  /// I added the word "counter"
            + "NAME  text, SURNAME text, EMAIL text, PHONE text, PASSWORD text);";

    // Variable to hold the database instance
    public SQLiteDatabase mySQLiteDatabase;



    public DBAdapter(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = _context;
        dbHelper = new DataBaseHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        this.mySQLiteDatabase = db;
        Log.e("DATABASE OPERATION", "Table create..." + DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }


    public void close() {
        mySQLiteDatabase.close();
    }

    public  DBAdapter open() throws SQLException
    {
        mySQLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return mySQLiteDatabase;
    }

    public void insertEntry(User user)
    {
        ContentValues newValues = new ContentValues();

        String query = "select * from users";
        /*Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();*/

        newValues.put(COLUMN_NAME, user.getName());
        newValues.put(COLUMN_SURNAME, user.getSurName());
        newValues.put(COLUMN_EMAIL, user.getEmail());
        newValues.put(COLUMN_PHONE, user.getPhone());
        newValues.put(COLUMN_PASSWORD, user.getPassword());
            // Inserts the row into the table
        mySQLiteDatabase.insert(DATABASE_NAME, null, newValues);
            //Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
       //  //ERROR DB = null
       close();
    }

    public String getPass(String email) {
        String query = "Select email " + COLUMN_EMAIL + "select password " + COLUMN_PASSWORD + " FROM " + DATABASE_NAME;
        mySQLiteDatabase = this.getReadableDatabase(); //Here DB is null?! App CRASHES
        Cursor cursor = mySQLiteDatabase.rawQuery(query, null);
        if(cursor.getCount() < 1) // email does Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        cursor.close();
        return password;
/*        String a, b;
        b = "Not Found!";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(email)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());

        }
        return b;*/
    }


/**
    public int deleteEntry(String email) {

        String where = "EMAIL=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{email});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+ numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
            return numberOFEntriesDeleted;
    }


    public static String getSinlgeEntry(String email) {
        Cursor cursor = db.query("LOGIN", null, " EMAIL=?",
                new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) { // Email is not registered in the system
                cursor.close();
                return "NOT EXIST";
            }
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            cursor.close();
            return password;
    }

    public void updateEntry(String email, String password) {
            // Define the updated row content.
            ContentValues updatedValues = new ContentValues();
            // Assign values for each row.
 updatedValues.put("EMAIL", email);
            updatedValues.put("PASSWORD", password);

        String where = "EMAIL = ?";
        db.update("LOGIN", updatedValues, where, new String[] { email });
        }
 */

}