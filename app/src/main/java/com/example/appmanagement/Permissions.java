package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Permissions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    public void Read_permission(View view) throws PackageManager.NameNotFoundException {
        // Obtain a reference to the PackageManager
        PackageManager packageManager = getPackageManager();

// Get a list of all installed applications
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);

// Loop through each application and print its name and permissions
        for (ApplicationInfo application : applications) {
            // Get the package name and package info for the current application
            String packageName = application.packageName;
            PackageInfo packageInfo = ((PackageManager) packageManager).getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

            // Get the requested permissions for the current application
            String[] permissions = packageInfo.requestedPermissions;

            // Print the application name and its permissions
            if (permissions != null) {
//                Log.d("MyApp", "Application: " + packageName);
                for (String permission : permissions) {
                    System.out.println("MyApp"+ "  Permission: " + permission);
                }
            }
        }



    }


}