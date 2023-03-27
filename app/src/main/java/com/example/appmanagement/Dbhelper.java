package com.example.appmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Dbhelper extends SQLiteOpenHelper {
    static  String Database_name="mainDB.db";
    static final int Db_version=1;
    public Dbhelper(Context context) {
        super(context, Database_name, null, Db_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,UserID TEXT, Email TEXT,Username TEXT,password TEXT DEFAULT '12345',permission TEXT DEFAULT 'all',AllowedApp TEXT DEFAULT 'false')");
        db.execSQL("CREATE TABLE TempApplication(_id INTEGER PRIMARY KEY AUTOINCREMENT,AppName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  boolean AddUsers(String username, String UserID, String password, String email)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("password",password);
        contentValues.put("Email",email);
        contentValues.put("UserID",UserID);

        long response=db.insert("AllUsers",null,contentValues);
        if(response==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean updatePermission(String email, String permissions) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("permission", permissions);

        int result = db.update("AllUsers", values, "Email=?", new String[]{email});

        return result > 0;
    }




    public  boolean AddTempApp(String AppName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("AppName",AppName);

        long response=db.insert("AllUsers",null,contentValues);
        if(response==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getUsersByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "UserID",
                "Username",
                "password",
                "permission",
                "AllowedApp"
        };
        String selection = "UserID = ?";
        String[] selectionArgs = { id };
        Cursor cursor = db.query(
                "AllUsers",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }



    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "UserID",
                "Username",
                "password",
                "permission",
                "AllowedApp"
        };
        Cursor cursor = db.query(
                "AllUsers",
                projection,
                null, // no selection
                null, // no selectionArgs
                null,
                null,
                null
        );
        return cursor;
    }




}
