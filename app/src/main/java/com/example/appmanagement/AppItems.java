package com.example.appmanagement;

import android.graphics.drawable.Drawable;

public class AppItems {

    private String AppName;

    public AppItems(String appName, Drawable appIcon, String packageName) {
        AppName = appName;
        AppIcon = appIcon;
        this.packageName = packageName;
    }

    private Drawable AppIcon;


    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        AppIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private String packageName;


}
