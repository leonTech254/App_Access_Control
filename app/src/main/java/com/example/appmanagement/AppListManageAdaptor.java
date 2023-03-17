package com.example.appmanagement;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppListManageAdaptor extends RecyclerView.Adapter<AppListManageAdaptor.AppHolder> {
    Dbhelper db;
    public AppListManageAdaptor(List<AppItems> appItems, Context context) {
        this.appItems = appItems;
        this.context = context;
    }

    private List<AppItems> appItems;
    private Context context;
    AppListManage methods;

    public AppListManageAdaptor(Context context) {

    }
    public void me()
    {
        Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(context, AppItems.getAppName(), Toast.LENGTH_SHORT).show();
                db=new Dbhelper(context);
                db.AddTempApp(AppItems.getAppName());

            }
        });




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
