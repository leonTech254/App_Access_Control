package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_Success_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_success_register);
    }


    public void toAllApp(View view)
    {
        Intent intent = new Intent(this,AllApps.class);
        startActivity(intent);
    }


}