package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnrollSuccess extends AppCompatActivity {
    private String Email;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_success);
        String UserEmail=getIntent().getStringExtra("email");
        userId=getIntent().getStringExtra("usedId");

        Email=UserEmail;


    }


    public  void toPermissions(View view)
    {
        Intent intent =new Intent(this,AppListManage.class);
        intent.putExtra("email",Email);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
}