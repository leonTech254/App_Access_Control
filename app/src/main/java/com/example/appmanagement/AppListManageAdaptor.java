package com.example.appmanagement;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppListManageAdaptor extends RecyclerView.Adapter<AppListManageAdaptor.AppHolder> {
    Dbhelper db;
    private SharedPreferences sharedPreferences;




    public AppListManageAdaptor(List<AppItems> appItems, Context context) {
        this.appItems = appItems;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


    }

    private List<AppItems> appItems;
    private Context context;
    AppListManage methods;

    public AppListManageAdaptor(Context context) {

    }
    public void me()
    {


    }

    @NonNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_permisssion_design,parent,false);

        return new AppHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppHolder holder, @SuppressLint("RecyclerView") int position) {

        AppItems AppItems = appItems.get(position);
        holder.AppName.setText(AppItems.getAppName());
        holder.AppIcon.setImageDrawable(AppItems.getAppIcon());
        holder.Appmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.Appmarker.isChecked())
                {
                    Toast.makeText(context, AppItems.getAppName(), Toast.LENGTH_SHORT).show();
                    ManagePermission(AppItems.getAppName());

                }else
                {
//                    remove item
                    RemoveApp(AppItems.getAppName());
                }




            }
        });




    }

    public  void ManagePermission(String appname)
    {


        String value = sharedPreferences.getString("apps", "");
        if(!value.isEmpty())
        {
            String newApps=value+","+appname;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("apps",newApps);
            editor.apply();

            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();

        }else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("apps",appname);
            editor.apply();
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
        }


    }

    public  void  RemoveApp(String AppName)
    {
        String appListString = sharedPreferences.getString("apps", "");
        String[] namesArray = appListString.split(",");
        List<String> ApplistTemp = new ArrayList<>(Arrays.asList(namesArray));
        ApplistTemp.remove(AppName);
        String NewappListString = TextUtils.join(",", ApplistTemp);
//appdating sessionApp
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("apps",NewappListString);
        editor.apply();

        Toast.makeText(context, NewappListString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return appItems.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder
    {
        public TextView AppName;
        public ImageView AppIcon;
        public CheckBox Appmarker;
        public AppHolder(@NonNull View itemView) {
            super(itemView);
            AppName=(TextView) itemView.findViewById(R.id.appName);
            AppIcon= (ImageView) itemView.findViewById(R.id.appIcon);
            Appmarker=(CheckBox) itemView.findViewById(R.id.AppCheckBox);

        }
    }


}
