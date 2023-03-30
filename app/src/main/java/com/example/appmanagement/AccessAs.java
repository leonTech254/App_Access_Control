package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccessAs extends AppCompatActivity {
    private String userID;
    private String usernamedb;
    private  String UserID;
    private  String allowedApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_as);
        userID=getIntent().getStringExtra("userId");
        allowedApp="None";
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
                allowedApp = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
                String allowedApp = cursor.getString(cursor.getColumnIndexOrThrow("AllowedApp"));
                UserID=cursor.getString(cursor.getColumnIndexOrThrow("UserID"));
                // do something with the retrieved data
            } while (cursor.moveToNext());
        }
        cursor.close();

        TextView username=findViewById(R.id.username);
        TextView usercode=findViewById(R.id.usercode);
        TextView appAllowed=findViewById(R.id.allowedApp);
        username.setText(usernamedb);
        usercode.setText(UserID);
        appAllowed.setText(allowedApp);

        if(allowedApp.equals("all"))
        {
            Intent intent = new Intent(this,AllApps.class);
            startActivity(intent);
        }
        if(allowedApp.equals("none"))
        {
            Toast.makeText(this, "Try logging in again", Toast.LENGTH_SHORT).show();
        }
    }


    public void toAllowedApps(View view)
    {
        Intent intent = new Intent(this,PermisionOverView.class);
        intent.putExtra("userId",UserID);
        intent.putExtra("username",usernamedb);
        startActivity(intent);

    }
}