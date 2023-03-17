package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class AccessAs extends AppCompatActivity {
    private String userID;
    private String usernamedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_as);
        userID=getIntent().getStringExtra("userId");
        getData();

    }



    public  void getData()
    {
//        getting username from database
        Dbhelper dbHelper = new Dbhelper(this);
        Cursor cursor = dbHelper.getUsersByID(userID);
        if (cursor.moveToFirst()) {
            do {
                usernamedb = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String permission = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
                String allowedApp = cursor.getString(cursor.getColumnIndexOrThrow("AllowedApp"));
                // do something with the retrieved data
            } while (cursor.moveToNext());
        }
        cursor.close();

        TextView username=findViewById(R.id.username);
        username.setText(usernamedb);
    }
}