package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UserItems> userItems;
    private String usernamedb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_users);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userItems =new ArrayList<>();


        adapter =new UsersAdaptor(userItems,this);
        recyclerView.setAdapter(adapter);
        allUsers();






    }
    public  void allUsers()
    {


//        getting username from database
            Dbhelper dbHelper = new Dbhelper(this);
            Cursor cursor = dbHelper.getAllUsers();
            if (cursor.moveToNext()) {
                do {
                    usernamedb = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                    String permissions = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
//                    dbpermission = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
                    String UserID = cursor.getString(cursor.getColumnIndexOrThrow("UserID"));
                    UserItems useritems = new UserItems(usernamedb,permissions,UserID);
                    userItems.add(useritems);

                    // do something with the retrieved data
                } while (cursor.moveToNext());
            }
            cursor.close();







    }
}