package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.List;

public class PermisionOverView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AppItems> appItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permision_over_view);
    }


    public void apps() {
        PackageManager packageManager = getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;

//            filter application according database permission
//
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
}