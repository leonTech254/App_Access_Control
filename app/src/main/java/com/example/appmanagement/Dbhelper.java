package com.example.appmanagement;

import android.content.ContentValues;
import android.content.Context;
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
        db.execSQL("CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,Username TEXT,password TEXT DEFAULT '12345',permission TEXT DEFAULT 'user',AllowedApp TEXT DEFAULT 'false')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  boolean AddUsers(String username,String sender, String read,String permission)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("password",sender);
        if(permission.equals("user"))
        {

            contentValues.put("permission","user");
        }else
        {
            contentValues.put("permission",permission);

        }

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
}
