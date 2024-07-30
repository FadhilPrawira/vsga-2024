package com.fadhilprawira.vsga2024;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "student_database_fadhil";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";

    public static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FIRSTNAME + " TEXT );";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_STUDENTS);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

//    Seperti migration di laravel
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_STUDENTS + "'");
        onCreate(db);
    }

    public long addStudentDetail(String student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, student);
        long insert = db.insert(TABLE_STUDENTS, null, values);
        return insert;
    }

//    ArrayList untuk menampung data
    public ArrayList<String> getAllStudentsList() {
        ArrayList<String> studentArrayList = new ArrayList<String>();
        String name = "";
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
//                throws an exception if the column does not exist, ensuring that the value is always ≥ 0
                name = c.getString(c.getColumnIndexOrThrow(KEY_FIRSTNAME));
                studentArrayList.add(name);
            } while (c.moveToNext());
            Log.d("array", studentArrayList.toString());
        }
        return studentArrayList;
    }
}
