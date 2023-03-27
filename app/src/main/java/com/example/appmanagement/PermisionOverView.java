package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermisionOverView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AppItems> appItems;
    private  String usernamedb;
    private  String userID;
    private  String Username;
    private  String dbpermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permision_over_view);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        appItems =new ArrayList<>();

        userID=getIntent().getStringExtra("userId");
        Username=getIntent().getStringExtra("username");

        adapter =new AppAdaptor(appItems,this);
        recyclerView.setAdapter(adapter);
       String permission=getData();
        apps(permission);
    }


    public void apps(String UsersPermission) {
//        process the string converting a all woed application from the string to arraty

        String[] namesArray = UsersPermission.split(",");
        List<String> ApplistTemp = new ArrayList<>(Arrays.asList(namesArray));








        PackageManager packageManager = getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                Drawable appIcon = packageManager.getApplicationIcon(applicationInfo);
                String appName = packageManager.getApplicationLabel(applicationInfo).toString();
                System.out.println(appName);

                String[] processname = appName.split("\\ ");
                System.out.println(processname);

                String packageName = applicationInfo.packageName;

                // Get the requested permissions for the current application
                String[] permissions = packageInfo.requestedPermissions;

                // Print the application name and its permissions
                if (permissions != null) {
                    System.out.println("Requested permissions:");
                    for (String permission : permissions) {
                        System.out.println(permission);
                    }
                }
                if(dbpermission.equals("all"))
                {
                    AppItems AppItems = new AppItems(appName, appIcon, packageName);
                    appItems.add(AppItems);

                }else {
                    if ( ApplistTemp.contains(packageName))
                    {
                        AppItems AppItems = new AppItems(appName, appIcon, packageName);
                        appItems.add(AppItems);

                    }

                }

            }
        }
    }
    public  String getData()
    {
//        getting username from database
        Dbhelper dbHelper = new Dbhelper(this);
        Cursor cursor = dbHelper.getUsersByID(userID);
        if (cursor.moveToFirst()) {
            do {
               usernamedb = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                dbpermission = cursor.getString(cursor.getColumnIndexOrThrow("permission"));
                String allowedApp = cursor.getString(cursor.getColumnIndexOrThrow("AllowedApp"));
                // do something with the retrieved data
            } while (cursor.moveToNext());
        }
        cursor.close();
        TextView username=findViewById(R.id.username);
        username.setText(usernamedb);
        return  dbpermission;
    }

}