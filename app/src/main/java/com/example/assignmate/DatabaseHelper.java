package com.example.assignmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    ArrayList<String> messageParts;
    public static final String databaseName = "AssignMate.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "AssignMate.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(ID INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT UNIQUE NOT NULL, password TEXT NOT NULL,phone TEXT NOT NULL)");
        MyDatabase.execSQL("create Table assignments(ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL, department TEXT NOT NULL,subject TEXT NOT NULL,chapters INTEGER NOT NULL,Date_Time TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists assignments");
    }

    public boolean insertAssignment(String name, String department, String subject, int chapters, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        values.put("name", name);
        values.put("department", department);
        values.put("subject", subject);
        values.put("chapters", chapters);
        values.put("Date_Time", currentDateTime);

        long result = db.insert("assignments", null, values);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertData(String email, String password, String Phone_Num) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("phone", Phone_Num);
        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPhone(String Phone_num) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where phone = ?", new String[]{Phone_num});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean getPassword(String Phone_Num) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where phone = ?", new String[]{Phone_Num});

        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            SmsManager smsManager = SmsManager.getDefault();
            String message = "Welcome to AssignMate! \n\nYour account has been created successfully.\n\nUsername: " + email + "\nPassword: " + password + "\n\nLogin now to generate assignments easily!\n\nKeep your credentials safe.\nHappy Learning!\n\n- AssignMate Team | Vaibhav Jawadwar";

            Phone_Num = "+91" + Phone_Num;
            ArrayList<String> messageParts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(Phone_Num, null, messageParts, null, null);
            return true;
        } else {
            return false;
        }
    }
}