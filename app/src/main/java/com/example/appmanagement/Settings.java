package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {
    private  SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public  void toAllUsers(View view)
    {
        Intent intent = new Intent(this,ViewAllUsers.class);
        startActivity(intent);
    }

    public  void  toAppUsers(View view)
    {
//        clear the admins session if any
        sharedPreferences =getSharedPreferences("alert", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user","");
        editor.apply();


        Intent intent = new Intent(this,Camera2_auth.class);;
        startActivity(intent);
    }
}