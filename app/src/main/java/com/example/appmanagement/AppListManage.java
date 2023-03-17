package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListManage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AppItems> appItems;
    private  String Email;
    private SharedPreferences sharedPreferences;
    Dbhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list_manage);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView_permission);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appItems =new ArrayList<>();

        adapter =new AppListManageAdaptor(appItems,this);
        recyclerView.setAdapter(adapter);
        apps();
        Email= getIntent().getStringExtra("email");
        TextView DispalyId=findViewById(R.id.user_permission_value);
        DispalyId.setText(Email);
        sharedPreferences =getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("apps","");
        editor.apply();
    }
    public void apps() {
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

                AppItems AppItems = new AppItems(appName, appIcon, packageName);
                appItems.add(AppItems);
            }
        }
    }

    public void AddItemList()
    {
        Toast.makeText(this, "hello leon", Toast.LENGTH_SHORT).show();
    }
    public  void  ToOverViewPermissions(View view)
    {
        String value = sharedPreferences.getString("apps", "");
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        db=new Dbhelper(this);
        boolean dbresponse=db.updatePermission(Email,value);
        if(dbresponse)
        {
            Toast.makeText(this, "Permission set Successfully", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Failed to set permission", Toast.LENGTH_SHORT).show();
        }



//        Intent intent = new Intent(this,PermisionOverView.class);
//        intent.putExtra("email",Email);
//        startActivity(intent);

    }

}