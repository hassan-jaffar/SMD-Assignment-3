package com.example.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
public class DatabaseHelper {
    private final String DATABASE_NAME = "PasswordManagerDB";
    private final int DATABASE_VERSION = 1;

    private final String USER_TABLE_NAME = "User_Table";

    private final String KEY_ID = "_id";
    private final String KEY_NAME = "_name";
    private final String KEY_PASS = "_pass";
    private final String PASSWORDS_TABLE_NAME = "Passwords_Table";

    private final String KEY_PID = "_pid";
    private final String KEY_USERID = "_userid";
    private final String KEY_USERNAME = "_username";
    private final String KEY_PASSWORD = "_password";
    private final String KEY_URL = "_url";
    private final String KEY_VISIBLE = "_visible";

    CreateDataBase helper;
    SQLiteDatabase database;
    Context context;

    public DatabaseHelper(Context context)
    {
        this.context = context;

    }

    public void updatePassword(int pid, String newName, String newPassword)
    {
        ContentValues cv = new ContentValues();

        cv.put(KEY_USERNAME, newName);
        cv.put(KEY_PASSWORD, newPassword);

        int records = database.update(PASSWORDS_TABLE_NAME, cv, KEY_PID+"=?", new String[]{pid+""});
        if(records>0)
        {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void permanentDelete(int pid)
    {
        int rows = database.delete(PASSWORDS_TABLE_NAME, KEY_PID+"=?", new String[]{pid+""});
        if(rows>0)
        {
            Toast.makeText(context, "Password deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePassword(int pid)
    {
        String query = "UPDATE " + PASSWORDS_TABLE_NAME + " SET _visible = 0 WHERE _pid = " + pid;
        database.execSQL(query);
    }

    public void restorePassword(int pid)
    {
        String query = "UPDATE " + PASSWORDS_TABLE_NAME + " SET _visible = 1 WHERE _pid = " + pid;
        database.execSQL(query);
    }

    public int loginUser(String name, String pass)
    {
        int id = -1;
        Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE _name = ? AND _pass = ?", new String[]{name, pass});
        int id_Index = cursor.getColumnIndex(KEY_ID);
        if(cursor.moveToFirst())
        {
                id = cursor.getInt(id_Index);
        }
        cursor.close();

        return id;
    }

    public void signupUser(String name, String pass)
    {


        int id = -1;
        Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE _name = ?", new String[]{name});
        int id_Index = cursor.getColumnIndex(KEY_ID);
        if(cursor.moveToFirst())
        {
            Toast.makeText(context, "This username already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues cv = new ContentValues();

            cv.put(KEY_NAME,name);
            cv.put(KEY_PASS,pass);

            long records = database.insert(USER_TABLE_NAME, null, cv);
            if(records == -1)
            {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "New User added successfully", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();

    }

    public void addPassword(int userid, String url, String username, String password)
    {
        ContentValues cv = new ContentValues();

        cv.put(KEY_USERID, userid);
        cv.put(KEY_URL,url);
        cv.put(KEY_USERNAME,username);
        cv.put(KEY_PASSWORD,password);

        long records = database.insert(PASSWORDS_TABLE_NAME, null, cv);
        if(records == -1)
        {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "New Password added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Password> readAllPasswords(int userid)
    {
        ArrayList<Password> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+PASSWORDS_TABLE_NAME + " WHERE _userid = ? AND _visible = 1", new String[]{String.valueOf(userid)});
        int pid_Index = cursor.getColumnIndex(KEY_PID);
        int username_Index = cursor.getColumnIndex(KEY_USERNAME);
        int password_Index = cursor.getColumnIndex(KEY_PASSWORD);
        int userid_Index = cursor.getColumnIndex(KEY_USERID);
        int url_Index = cursor.getColumnIndex(KEY_URL);

        if(cursor.moveToFirst())
        {
            do{
                Password c = new Password();

                c.setId(cursor.getInt(pid_Index));
                c.setUsername(cursor.getString(username_Index));
                c.setPassword(cursor.getString(password_Index));
                c.setUserid(cursor.getInt(userid_Index));
                c.setUrl(cursor.getString(url_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    public ArrayList<Password> readDeleted(int userid)
    {
        ArrayList<Password> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+PASSWORDS_TABLE_NAME + " WHERE _userid = " + userid + " AND _visible = 0", null);
        int pid_Index = cursor.getColumnIndex(KEY_PID);
        int username_Index = cursor.getColumnIndex(KEY_USERNAME);
        int password_Index = cursor.getColumnIndex(KEY_PASSWORD);
        int userid_Index = cursor.getColumnIndex(KEY_USERID);
        int url_Index = cursor.getColumnIndex(KEY_URL);

        if(cursor.moveToFirst())
        {
            do{
                Password c = new Password();

                c.setId(cursor.getInt(pid_Index));
                c.setUsername(cursor.getString(username_Index));
                c.setPassword(cursor.getString(password_Index));
                c.setUserid(cursor.getInt(userid_Index));
                c.setUrl(cursor.getString(url_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    public void open()
    {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        helper.close();
    }

    private class CreateDataBase extends SQLiteOpenHelper
    {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME + " TEXT NOT NULL," +
                    KEY_PASS + " TEXT NOT NULL" +
                    ");";
            db.execSQL(query);

            String query2 = "CREATE TABLE " + PASSWORDS_TABLE_NAME + "(" +
                    KEY_PID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT NOT NULL," +
                    KEY_PASSWORD + " TEXT NOT NULL," +
                    KEY_USERID + " INTEGER NOT NULL," +
                    KEY_URL + " TEXT NOT NULL," +
                    KEY_VISIBLE + " INTEGER NOT NULL DEFAULT 1" +
                    ");";
            db.execSQL(query2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // backup code here
            db.execSQL("DROP TABLE "+USER_TABLE_NAME+" IF EXISTS");
            onCreate(db);
        }
    }

}
