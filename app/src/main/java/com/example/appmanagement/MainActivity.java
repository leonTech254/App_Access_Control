package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

    }
    public void setAsDefaultLauncher(View view) {
        Toast.makeText(this, "Hello world", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);


    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}