package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView DisplayTime;
    private Timer timer;
    private TimerTask timerTask;
    private  final int REFRESH_RATE=1000;
    private Calendar calendar;
    private Boolean CheckUserExistance;
    private SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        DisplayTime=(TextView) findViewById(R.id.DisplayTime);
        Calendar c =Calendar.getInstance();
//        SimpleDateFormat df =new SimpleDateFormat("HH:mm:ss");

        calendar = Calendar.getInstance();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DisplayTime.setText(calendar.getTime().toString());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, REFRESH_RATE);
        CheckUserExistance=false;
        allUsers();

    }
    public void StartApplications(View view) {



//        check if
        if(CheckUserExistance)
        {
            Toast.makeText(this, "Verify", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,AccessGate.class);
            startActivity(intent);

        }else
        {
            Toast.makeText(this, "No User Initially registered. You Take all Privileges", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Camera2_auth.class);
//            notfity the other activity not to ask the user to se permission
            sharedPreferences =getSharedPreferences("alert", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user","admin");
            editor.apply();

            startActivity(intent);

        }








    }

    public  void allUsers()
    {


//        getting username from database
        Dbhelper dbHelper = new Dbhelper(this);
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor.moveToNext()) {
            do {
//                usernamedb = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
//                String permissions = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
////                    dbpermission = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
//                String UserID = cursor.getString(cursor.getColumnIndexOrThrow("UserID"));
//                UserItems useritems = new UserItems(usernamedb,permissions,UserID);
//                userItems.add(useritems);
                CheckUserExistance=true;

                // do something with the retrieved data
            } while (cursor.moveToNext());
        }
        cursor.close();







    }










    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}